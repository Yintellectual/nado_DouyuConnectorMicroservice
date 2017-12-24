package com.nado.douyuConnectorMicroservice.controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nado.douyuConnectorMicroservice.controller.entity.ChartData2D;
import com.nado.douyuConnectorMicroservice.controller.entity.ChartData2D2DataSets;
import com.nado.douyuConnectorMicroservice.douyuClient.DouyuDanmuBrokenMessageFilter;
import com.nado.douyuConnectorMicroservice.douyuClient.repository.Daily5MinuteTrafficReportRepository;
import com.nado.douyuConnectorMicroservice.douyuClient.repository.SampleMessageRepository;
import com.nado.douyuConnectorMicroservice.util.CommonUtil;


@Controller
public class SampleMessageController {

	@Autowired
	private SampleMessageRepository sampleMessageRepository;
	
	@RequestMapping("/api/sampleMessages/all")
	@ResponseBody
	public  Map<String, List<String>> allSampleMessages(){
		return sampleMessageRepository.printAll();
	}
	@RequestMapping("/api/sampleMessages/allToFile")
	@ResponseBody
	public  Map<String, List<String>> allSampleMessagesAndWriteToFile(){
		return sampleMessageRepository.printAllAsFiles();
	}
	@RequestMapping("/api/sampleMessages/types")
	@ResponseBody
	public Set<String> messageTypes(){
		return sampleMessageRepository.retieveTypes();
	}
	@RequestMapping("/api/sampleMessages/fileNamesOnly")
	@ResponseBody
	public Set<String> fileNamesOnly(){
		return sampleMessageRepository.printFileNames();
	}
	@RequestMapping("/api/sampleMessages/messagesByFileNames")
	@ResponseBody
	public Map<String, List<String>> getByFileNames(String... fileNames){
		return sampleMessageRepository.printByFileNames(fileNames);
	}
}
