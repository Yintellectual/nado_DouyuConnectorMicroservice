package com.nado.nado_population_service.douyuClient.repository;

import java.util.Map;

import com.nado.nado_population_service.enums.TrafficReportField;

public interface Daily5MinuteTrafficReportRepository {
	void saveRecord(String date, String time, String total_message_count, String broken_message_count);
	String retieveRecord(String date, String time, TrafficReportField field);
	Map<String, String> retieveRecordByDate(String date, TrafficReportField field);
	void eraseRecordsByDate(String date);
}
