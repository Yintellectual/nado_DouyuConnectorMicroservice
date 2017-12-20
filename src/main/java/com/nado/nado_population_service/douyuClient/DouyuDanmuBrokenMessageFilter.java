package com.nado.nado_population_service.douyuClient;

import java.util.List;
import java.util.Map;
import java.util.Set;
import com.nado.nado_population_service.douyuClient.DouyuDanmuClient;
public interface DouyuDanmuBrokenMessageFilter {
	Set<String> everEncounteredTypes();
	Map<String, Integer> getTraffic5minutesRecords();
	Map<String, Integer> getBrokenMessage5minutesRecords();
	Map<String, Integer> getTrafficDailyRecords();
	Map<String, Integer> getBrokenMessageDailyRecords();
	int renewTrafficRecord();
	int renewBrokenRecord();
	int isBrokenMessage(String message);
	void testAndSaveBrokenMessage(String message);
	List<String> getBrokenMessages(String type);
	String take();
	public void wrapClient(DouyuDanmuClient client);
}
