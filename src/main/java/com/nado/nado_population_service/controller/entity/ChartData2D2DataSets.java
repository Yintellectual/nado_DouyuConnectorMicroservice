package com.nado.nado_population_service.controller.entity;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import lombok.Data;

@Data
public class ChartData2D2DataSets{
	public ChartData2D2DataSets(){}
	public ChartData2D2DataSets(Object[] labels, Object[] data, Object[] data2, Object[]color){
		this.labels = labels;
		this.data = data;
		this.data2 = data2;
		this.color = color;
	}
	private Object[] labels;
	private Object[] data;
	private Object[] data2;
	private Object[] color;
	public static ChartData2D2DataSets fromMap(Map<String, String> map,Map<String, String> map2,String defaultColor){
		int size = map.size();
		Object[] labelsTemp = new Object[size]; 
		Object[] dataTemp = new Object[size];
		Object[] data2Temp = new Object[size];
		Object[] colorTemp = new Object[size];
		int i = 0;
		for(String key: map.keySet()){
			colorTemp[i] = defaultColor;
			dataTemp[i] = map.get(key);
			data2Temp[i] = map2.get(key);
			labelsTemp[i] = key;
			i++;
		}
		return new ChartData2D2DataSets(labelsTemp, dataTemp, data2Temp, colorTemp); 
	}
}