package com.nado.douyuConnectorMicroservice.douyuClient.repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nado.douyuConnectorMicroservice.enums.MessageIntegrityStatuses;


public interface SampleMessageRepository {
	int getMaximumSizeForEachType();
	void saveSample(String message, String type, MessageIntegrityStatuses field);
	List<String> retieveSamplesByType(String type, MessageIntegrityStatuses field);
	Map<String, List<String>> printAsFiles();
	Set<String> retieveTypes();
}
