package com.nado.douyuConnectorMicroservice.douyuClient.repository.impl;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.nado.douyuConnectorMicroservice.douyuClient.repository.Daily5MinuteTrafficReportRepository;
import com.nado.douyuConnectorMicroservice.enums.MessageIntegrityStatuses;
import com.nado.douyuConnectorMicroservice.util.CommonUtil;

@Service
public class Daily5MinuteTrafficReportRepositoryRedisImpl implements Daily5MinuteTrafficReportRepository {

	@Autowired
	StringRedisTemplate stringRedisTemplate;
	SetOperations<String, String> setOperations;
	HashOperations<String, String, String> hashOperations;
	@PostConstruct
	public void init(){
		setOperations = stringRedisTemplate.opsForSet();
		hashOperations = stringRedisTemplate.opsForHash();
	}
	
	@Override
	public void saveRecord(String date, String time, String total_message_count, String broken_message_count) {
		// TODO Auto-generated method stub
		setOperations.add("nado:trafficRecords", date);
		hashOperations.put("nado:"+MessageIntegrityStatuses.total+"_message_count:date:"+date, time, total_message_count);
		hashOperations.put("nado:"+MessageIntegrityStatuses.broken+"_message_count:date:"+date, time, broken_message_count);
	}

	@Override
	public String retieveRecord(String date, String time, MessageIntegrityStatuses field) {
		// TODO Auto-generated method stub
		return hashOperations.get("nado:"+field+"_message_count:date:"+date, time);
	}

	@Override
	public LinkedHashMap <String, String> retieveRecordByDate(String date, MessageIntegrityStatuses field) {
		// TODO Auto-generated method stub
		Map<String, String> data = hashOperations.entries("nado:"+field+"_message_count:date:"+date);
		return CommonUtil.sortTrafficRecordByTimeOrder(data);
	}

	@Override
	public void eraseRecordsByDate(String date) {
		// TODO Auto-generated method stub
		setOperations.remove("nado:trafficRecords", date);
		for(MessageIntegrityStatuses field:MessageIntegrityStatuses.values()){
			stringRedisTemplate.delete("nado:"+field+"_message_count:date:"+date);	
		}
	}
}
