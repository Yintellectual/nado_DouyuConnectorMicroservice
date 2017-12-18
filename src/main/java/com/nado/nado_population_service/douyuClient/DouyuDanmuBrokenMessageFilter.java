package com.nado.nado_population_service.douyuClient;

import java.util.List;
import java.util.Map;

public interface DouyuDanmuBrokenMessageFilter {
	Map<String, Integer> getTraffic5minutesRecords();
	Map<String, Integer> getBrokenMessage5minutesRecords();
	Map<String, Integer> getTrafficDailyRecords();
	Map<String, Integer> getBrokenMessageDailyRecords();
	boolean isBrokenMessage(String message);
	void testAndSaveBrokenMessage();
	List<String> getBrokenMessages(String type);
	String take();
}
