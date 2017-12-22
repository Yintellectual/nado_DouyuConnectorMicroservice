package com.nado.douyuConnectorMicroservice.douyuClient.impl;

import static com.nado.douyuConnectorMicroservice.util.CommonUtil.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.nado.douyuConnectorMicroservice.douyuClient.DouyuDanmuBrokenMessageFilter;
import com.nado.douyuConnectorMicroservice.douyuClient.DouyuDanmuClient;
import com.nado.douyuConnectorMicroservice.douyuClient.repository.Daily5MinuteTrafficReportRepository;
import com.nado.douyuConnectorMicroservice.douyuClient.repository.SampleMessageRepository;
import com.nado.douyuConnectorMicroservice.enums.MessageIntegrityStatuses;

@Service
public class DouyuDanmuBrokenMessageFilterNaiveImpl implements DouyuDanmuBrokenMessageFilter {

	@Autowired
	Daily5MinuteTrafficReportRepository daily5MinuteTrafficReportRepository;
	@Autowired
	SampleMessageRepository sampleMessageRepository;
	private int olderBrokenMessageCount = 0;
	private int brokenMessageCount = 0;
	private Map<String, String> latestBrokenMessageRecord = null;
	private int olderTotalMessageCount = 0;
	private int totalMessageCount = 0;
	private Map<String, String> latestTotalMessageRecord = null;

	@Override
	public Map<String, String> getLatest5minutesTotalMessageRecord() {
		// TODO Auto-generated method stub
		return latestTotalMessageRecord;
	}

	@Override
	public Map<String, String> getLatest5minutesBrokenMessageRecord() {
		// TODO Auto-generated method stub
		return latestBrokenMessageRecord;
	}

	@Override
	public LinkedHashMap<String, String> getTotalMessageRecordsByDate(String date) {
		// TODO Auto-generated method stub
		return daily5MinuteTrafficReportRepository.retieveRecordByDate(date, MessageIntegrityStatuses.total);
	}

	@Override
	public LinkedHashMap<String, String> getBrokenMessageRecordsByDate(String date) {
		// TODO Auto-generated method stub
		return daily5MinuteTrafficReportRepository.retieveRecordByDate(date, MessageIntegrityStatuses.broken);
	}

	@Scheduled(cron = "0 0/5 * * * *")
	public void heartbeatEvery5Minutes() {
		String total_message_count = renewTrafficRecord() + "";
		String broken_message_count = renewBrokenRecord() + "";
		LocalDateTime now = virtualizeDateTime(LocalDateTime.now());
		String date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String time = now.format(DateTimeFormatter.ofPattern("HH:mm"));
		daily5MinuteTrafficReportRepository.saveRecord(date, time, total_message_count, broken_message_count);
		latestTotalMessageRecord = Collections.singletonMap(date+" "+time, total_message_count);
		latestBrokenMessageRecord = Collections.singletonMap(date+" "+time, broken_message_count);
	}

	public int renewTrafficRecord() {
		int total_message_count = totalMessageCount - olderTotalMessageCount;
		olderTotalMessageCount = totalMessageCount;
		return total_message_count;
	}

	public int renewBrokenRecord() {
		int broken_message_count = brokenMessageCount - olderBrokenMessageCount;
		olderBrokenMessageCount = brokenMessageCount;
		return broken_message_count;
	}

	/*
	 * 1 = not ends with '/' 2 = missing type 3 = multiple type 4 = missing
	 * fields -1 = false(good message)
	 */
	@Override
	public int isBrokenMessage(String message) {
		int result = 0;
		if (!message.endsWith("/")) {
			result = 1;
		}
		if (message.matches(".*/type@=.*/type@=.*")) {
			result = 3;
		}
		String type = matchStringValue(message, "type");
		if (mapOfTypeAndFields.containsKey(type)) {
			for (String field : mapOfTypeAndFields.get(type)) {
				if (!message.contains(field)) {
					result = 4;
				}
			}
			result = -1;
		} else {
			System.out.println("\n\n\n\n!!!!!!" + message);
			System.out.println("\n\n\n\n!!!!!!" + type);
			result = 2;
		}
		if(result>0){
			sampleMessageRepository.saveSample(message, type, MessageIntegrityStatuses.broken);
		}else if(result<0){
			publish(type, message);
			sampleMessageRepository.saveSample(message, type, MessageIntegrityStatuses.good);
		}else{
			throw new RuntimeException();
		}
		return result;
	}

	public void wrapClient(DouyuDanmuClient client) {
		new Thread(() -> {
			while (true) {
				String message = client.take();
				testAndSaveBrokenMessage(message);
			}
		}).start();
	}

	/*
	 * Has the side-effect of updating both the traffic and the broken message
	 * rate
	 */
	@Override
	public void testAndSaveBrokenMessage(String message) {
		// TODO Auto-generated method stub
		totalMessageCount++;
		if (isBrokenMessage(message) > 0) {
			brokenMessageCount++;
			
			System.out.println("\n\nBROKEN: " + message);

		} else {
			System.out.println("\n\nlatestMessageId:" + totalMessageCount + "  brokenMessageCount:" + brokenMessageCount
					+ "Good: " + matchStringValue(message, "type"));
		}
	}

	
	@Override
	public String publish(String type, String message) {
		// TODO Auto-generated method stub
		return message;
	}

	
}
