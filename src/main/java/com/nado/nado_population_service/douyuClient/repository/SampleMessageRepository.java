package com.nado.nado_population_service.douyuClient.repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nado.nado_population_service.enums.MessageIntegrityStatuses;


public interface SampleMessageRepository {
	int getMaximumSizeForEachType();
	void saveSample(String message, String type, MessageIntegrityStatuses field);
	List<String> retieveSamplesByType(String type, MessageIntegrityStatuses field);
	Map<String, List<String>> printAsFiles();
}
