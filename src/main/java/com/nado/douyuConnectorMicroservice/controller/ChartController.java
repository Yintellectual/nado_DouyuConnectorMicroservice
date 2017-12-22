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
public class ChartController {

	@Autowired
	private DouyuDanmuBrokenMessageFilter clientWrapper;
	@Autowired
	private SampleMessageRepository sampleMessageRepository;
	
	@RequestMapping("/api/sampleMessages/all")
	@ResponseBody
	public  Map<String, List<String>> allSampleMessages(){
		return sampleMessageRepository.printAsFiles();
	}
	@RequestMapping("/api/sampleMessages/types")
	@ResponseBody
	public Set<String> messageTypes(){
		return sampleMessageRepository.retieveTypes();
	}
	
	@RequestMapping("/api/chartjs/trafficDataOfToday")
	@ResponseBody
	public ChartData2D2DataSets trafficDataOfToday(){
		LocalDateTime now = LocalDateTime.now();
		String date = CommonUtil.getvirtualizedDate(now);
		return ChartData2D2DataSets.fromMap(
				clientWrapper.getTotalMessageRecordsByDate(date),
				clientWrapper.getBrokenMessageRecordsByDate(date),
				"rgba(100,100,100,0.5)");
	}
	@RequestMapping("/api/chartjs/trafficDataOfYesterday")
	@ResponseBody
	public ChartData2D2DataSets trafficDataOfYesterday(){
		LocalDateTime now = LocalDateTime.now();
		String date = CommonUtil.getvirtualizedDate(now.minus(1, ChronoUnit.DAYS));
		return ChartData2D2DataSets.fromMap(
				clientWrapper.getTotalMessageRecordsByDate(date),
				clientWrapper.getBrokenMessageRecordsByDate(date),
				"rgba(100,100,100,0.5)");
	}
	@RequestMapping("/api/chartjs/trafficDataByDate")
	@ResponseBody
	public ChartData2D2DataSets trafficDataByDate(String date){
		return ChartData2D2DataSets.fromMap(
				clientWrapper.getTotalMessageRecordsByDate(date),
				clientWrapper.getBrokenMessageRecordsByDate(date),
				"rgba(100,100,100,0.5)");
	}
}
