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

	/*
	 * 1 = not ends with '/'
	 * 2 = missing type
	 * 3 = multiple type
	 * 4 = missing fields
	 * -1 = false(good message) 
	 * */
	@Override
	public int isBrokenMessage(String message) {
		if (!message.endsWith("/")) {
			return 1;
		}
		if (message.matches(".*/type@=.*/type@=.*")) {
			return 3;
		}
		String type = matchStringValue(message, "type");
		if (mapOfTypeAndFields.containsKey(type)) {
			for (String field : mapOfTypeAndFields.get(type)) {
				if (!message.contains(field)) {
					return 4;
				}
			}
			return -1;
		} else {
			System.out.println("\n\n\n\n!!!!!!"+message);
			System.out.println("\n\n\n\n!!!!!!"+type);
			return 2;
		}
	}

	public void wrapClient(DouyuDanmuClient client){
		while(true){
			String message = client.take();
			testAndSaveBrokenMessage(message);
		}
	}
	
	/*
	 * Has the side-effect of updating both the traffic and the broken message rate
	 * */
	@Override
	public void testAndSaveBrokenMessage(String message) {
		// TODO Auto-generated method stub
		if (isBrokenMessage(message)==0) {
			try {
				Files.write(Paths.get("c:/nado/brokenMessage.txt"), (message + "\n").getBytes(), StandardOpenOption.APPEND);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("\n\nBROKEN: " + message);

		} else {
			System.out.println("\n\nGood: " + matchStringValue(message, "type"));
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
