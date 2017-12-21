package com.nado.nado_population_service.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nado.nado_population_service.controller.entity.ChartData2D;
import com.nado.nado_population_service.controller.entity.ChartData2D2DataSets;
import com.nado.nado_population_service.douyuClient.DouyuDanmuBrokenMessageFilter;
import com.nado.nado_population_service.douyuClient.repository.Daily5MinuteTrafficReportRepository;
import com.nado.nado_population_service.enums.TrafficReportField;

@Controller
public class ChartController {

	@Autowired
	private DouyuDanmuBrokenMessageFilter clientWrapper;
	
	@RequestMapping("/test")
	@ResponseBody
	public ChartData2D test(){
		return new ChartData2D(){
			{
				setColor(new String[]{"rgba(100,100,100,0.5)","rgba(100,100,100,0.5)","rgba(100,100,100,0.5)"});
				setData(new String[]{"44","55","66"});
				setLabels(new String[]{"07:00","07:05","07:10"});
			}
		};
	}
	@RequestMapping("/test2")
	@ResponseBody
	public ChartData2D test2(){
		return ChartData2D.fromMap(new LinkedHashMap<String, String>(){
			{
				put("07:00", 44+"");
				put("07:05", 55+"");
				put("07:10", 66+"");
			}
		}, "rgba(100,100,100,0.5)");
	}
	
	@RequestMapping("/api/chartjs/trafficDataOfToday")
	@ResponseBody
	public ChartData2D2DataSets trafficDataOfToday(){
		return ChartData2D2DataSets.fromMap(
				clientWrapper.getTotalMessageRecordsByDate("2017-12-20"),
				clientWrapper.getBrokenMessageRecordsByDate("2017-12-20"),
				"rgba(100,100,100,0.5)");
	}
	@RequestMapping("/api/chartjs/trafficDataOfYesterday")
	@ResponseBody
	public ChartData2D2DataSets trafficDataOfYesterday(){
		return ChartData2D2DataSets.fromMap(
				clientWrapper.getTotalMessageRecordsByDate("2017-12-19"),
				clientWrapper.getBrokenMessageRecordsByDate("2017-12-19"),
				"rgba(100,100,100,0.5)");
	}
}
