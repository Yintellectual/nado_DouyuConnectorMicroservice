package com.nado.nado_population_service.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {
	public static String matchDigitalValue(String message, String fieldName){
		Pattern pattern = Pattern.compile(fieldName+"@=(\\d*)"); 
		Matcher matcher = pattern.matcher(message);
		if(matcher.find()){
			return matcher.group(1);
		}else{
			return null;
		}
	}
}
