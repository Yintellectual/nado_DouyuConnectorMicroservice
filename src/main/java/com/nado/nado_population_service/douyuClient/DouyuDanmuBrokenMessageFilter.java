package com.nado.nado_population_service.douyuClient;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.nado.nado_population_service.douyuClient.DouyuDanmuClient;
public interface DouyuDanmuBrokenMessageFilter {
	Set<String> everEncounteredTypes();
	Map<String, String> getLatest5minutesTotalMessageRecord();
	Map<String, String> getLatest5minutesBrokenMessageRecord();
	LinkedHashMap<String, String> getTotalMessageRecordsByDate(String date);
	LinkedHashMap<String, String> getBrokenMessageRecordsByDate(String date);
	int renewTrafficRecord();
	int renewBrokenRecord();
	int isBrokenMessage(String message);
	void testAndSaveBrokenMessage(String message);
	List<String> getBrokenMessages(String type);
	String take();
	public void wrapClient(DouyuDanmuClient client);
}
