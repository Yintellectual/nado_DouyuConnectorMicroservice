package com.nado.nado_population_service.douyuClient.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.nado.nado_population_service.douyuClient.DouyuDanmuBrokenMessageFilter;
import static com.nado.nado_population_service.util.CommonUtil.*;

@Service
public class DouyuDanmuBrokenMessageFilterNaiveImpl implements DouyuDanmuBrokenMessageFilter {

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

}
