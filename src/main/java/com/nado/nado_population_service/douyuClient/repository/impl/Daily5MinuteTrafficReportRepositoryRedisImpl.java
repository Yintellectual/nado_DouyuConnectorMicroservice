package com.nado.nado_population_service.douyuClient.repository.impl;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.nado.nado_population_service.douyuClient.repository.Daily5MinuteTrafficReportRepository;
import com.nado.nado_population_service.enums.TrafficReportField;

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
		hashOperations.put("nado:"+TrafficReportField.total_message_count+":date:"+date, time, total_message_count);
		hashOperations.put("nado:"+TrafficReportField.broken_message_count+":date:"+date, time, broken_message_count);
	}

	@Override
	public String retieveRecord(String date, String time, TrafficReportField field) {
		// TODO Auto-generated method stub
		return hashOperations.get("nado:"+field+":date:"+date, time);
	}

	@Override
	public Map<String, String> retieveRecordByDate(String date, TrafficReportField field) {
		// TODO Auto-generated method stub
		return hashOperations.entries("nado:"+field+":date:"+date);
	}

	@Override
	public void eraseRecordsByDate(String date) {
		// TODO Auto-generated method stub
		setOperations.remove("nado:trafficRecords", date);
		for(TrafficReportField field:TrafficReportField.values()){
			stringRedisTemplate.delete("nado:"+field+":date:"+date);	
		}
	}

}
