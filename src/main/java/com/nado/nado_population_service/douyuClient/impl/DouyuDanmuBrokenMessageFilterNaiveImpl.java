package com.nado.nado_population_service.douyuClient.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nado.nado_population_service.douyuClient.DouyuDanmuBrokenMessageFilter;
import com.nado.nado_population_service.douyuClient.DouyuDanmuClient;

import static com.nado.nado_population_service.util.CommonUtil.*;

@Service
public class DouyuDanmuBrokenMessageFilterNaiveImpl implements DouyuDanmuBrokenMessageFilter {

	@Autowired
	DouyuDanmuClient client;
	@Override
	public Map<String, Integer> getTraffic5minutesRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Integer> getBrokenMessage5minutesRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Integer> getTrafficDailyRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Integer> getBrokenMessageDailyRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isBrokenMessage(String message) {
		if (!message.endsWith("/")) {
			return true;
		}
		if (!message.contains("type")) {
			return true;
		}
		if (message.matches(".*type.*type.*")) {
			return true;
		}
		String type = matchStringValue(message, "type");
		if(mapOfTypeAndFields.containsKey(type)){
			for (String field : mapOfTypeAndFields.get(type)) {
				if (!message.contains(field)) {
					return true;
				}
			}
			return false;	
		}else{
			return true;
		}
	}

	@Override
	public void testAndSaveBrokenMessage() {
		// TODO Auto-generated method stub
		while(true){
			String message = client.take();
			if(isBrokenMessage(message)){
				try {
					Files.write(Paths.get("c:/nado/output.txt"), (message+"\n").getBytes(), StandardOpenOption.APPEND);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println("\n\nBROKEN: "+message);
				
			}else{
				System.out.println("\n\nGood: "+matchStringValue(message, "type"));
			}
		}
	}

	@Override
	public List<String> getBrokenMessages(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String take() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> everEncounteredTypes() {
		// TODO Auto-generated method stub
		return null;
	}
}
