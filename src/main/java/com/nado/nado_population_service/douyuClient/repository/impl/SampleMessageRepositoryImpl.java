package com.nado.nado_population_service.douyuClient.repository.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.nado.nado_population_service.douyuClient.repository.SampleMessageRepository;
import com.nado.nado_population_service.enums.MessageIntegrityStatuses;
@Service
public class SampleMessageRepositoryImpl implements SampleMessageRepository {

	private static final int MAXMUM_INDEX_FOR_EACH_TYPE = 99;
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	SetOperations<String, String> setOperations;
	HashOperations<String, String, String> hashOperations;
	ListOperations<String, String> listOperations;
	@PostConstruct
	public void init(){
		setOperations = stringRedisTemplate.opsForSet();
		hashOperations = stringRedisTemplate.opsForHash();
		listOperations = stringRedisTemplate.opsForList();
	}
	
	@Override
	public int getMaximumSizeForEachType() {
		// TODO Auto-generated method stub
		return MAXMUM_INDEX_FOR_EACH_TYPE+1;
	}

	@Override
	public void saveSample(String message, String type, MessageIntegrityStatuses field) {
		// TODO Auto-generated method stub
		String key = "nado:sample_"+field+"_messages:type:"+type;
		listOperations.leftPush(key, message);
		listOperations.trim(key, 0, MAXMUM_INDEX_FOR_EACH_TYPE);
		setOperations.add("nado:sample_"+field+"_messages:types", type);
	}

	@Override
	public List<String> retieveSamplesByType(String type, MessageIntegrityStatuses field) {
		// TODO Auto-generated method stub
		String key = "nado:sample_"+field+"_messages:type:"+type;
		return listOperations.range(key, 0, -1);
	}

	@Override
	public Map<String, List<String>> printAsFiles() {
		Map<String, List<String>> result = new HashMap<>();
		// TODO Auto-generated method stub
		for(MessageIntegrityStatuses statuse: MessageIntegrityStatuses.values()){
			Set<String> types = setOperations.members("nado:sample_"+statuse+"_messages:types");
			if(types!=null && !types.isEmpty()){
				for(String type:types){
					List<String> messages = retieveSamplesByType(type, statuse);
					String fileName = statuse+"_"+type+"_sample_messages.txt";
					result.put(fileName, messages);
					messages.forEach(message->{
						try {
							Files.write(Paths.get(fileName), (message + "\n").getBytes(),
									StandardOpenOption.APPEND, StandardOpenOption.CREATE);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
				}
			}
		} 
		return result;
	}
}
