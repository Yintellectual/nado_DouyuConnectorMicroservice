package com.nado.douyuConnectorMicroservice.controller.entity;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import lombok.Data;

@Data
public class ChartData2D{
	public ChartData2D(){}
	public ChartData2D(Object[] labels, Object[] data, Object[]color){
		this.labels = labels;
		this.data = data;
		this.color = color;
	}
	private Object[] labels;
	private Object[] data;
	private Object[] color;
	public static ChartData2D fromMap(Map<String, String> map,String defaultColor){
		int size = map.size();
		Object[] labelsTemp = new Object[size]; 
		Object[] dataTemp = new Object[size];
		Object[] colorTemp = new Object[size];
		int i = 0;
		for(String key: map.keySet()){
			colorTemp[i] = defaultColor;
			dataTemp[i] = map.get(key);
			labelsTemp[i] = key;
			i++;
		}
		return new ChartData2D(labelsTemp, dataTemp, colorTemp); 
	}
}