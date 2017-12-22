package com.nado.douyuConnectorMicroservice.douyuClient.repository;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nado.douyuConnectorMicroservice.enums.MessageIntegrityStatuses;

public interface Daily5MinuteTrafficReportRepository {
	void saveRecord(String date, String time, String total_message_count, String broken_message_count);
	String retieveRecord(String date, String time, MessageIntegrityStatuses field);
	LinkedHashMap<String, String> retieveRecordByDate(String date, MessageIntegrityStatuses field);
	void eraseRecordsByDate(String date);
}
