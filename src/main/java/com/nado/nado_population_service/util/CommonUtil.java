package com.nado.nado_population_service.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {
	
	
	public static String matchDigitalValue(String message, String fieldName) {
		Pattern pattern = Pattern.compile(fieldName + "@=(\\d*)");
		Matcher matcher = pattern.matcher(message);
		if (matcher.find()) {
			return matcher.group(1);
		} else {
			return null;
		}
	}

	public static String matchStringValue(String message, String fieldName) {
		Pattern pattern = Pattern.compile(fieldName + "@=(\\w*)");
		Matcher matcher = pattern.matcher(message);
		if (matcher.find()) {
			return matcher.group(1);
		} else {
			return null;
		}
	}

	public static List<String> extractFields(String template) {
		List<String> result = new ArrayList<>();
		Pattern pattern = Pattern.compile("(\\w*)@=.*?/");
		Matcher matcher = pattern.matcher(template);
		while (matcher.find()) {
			result.add(matcher.group(1));
		}
		return result;
	}
	
	public static Map<String, List<String>> mapOfTypeAndFields = new HashMap<>();
	static {
		String[] templates = new String[]{
				"type@=chatmsg/rid@=58839/ct@=8/uid@=123456/nn@=test/txt@=666/cid@=1111/ic@=icon/sahf@=0/level@=1/dc@=0/bnn@=test/bl@=0/brid@=58839/hc@=0/el@=eid@AA=1@ASetp@AA=1@ASsc@AA=1@AS/",
				"type@=onlinegift/rid@=1/uid@=1/gid@=-9999/sil@=1/if@=1/ct@=1/nn@=tester/ur@=1/level@=6/btype@=1/",
				"type@=dgb/gfid@=1/gs@=59872/uid@=1/rid@=1/nn@=someone/level@=1/dw@=1/",
				"type@=uenter/rid@=1/uid@=1/nn@=someone/level@=1/el@=eid@AA=1@ASetp@AA=1@ASsc@AA=1@AS@S/",
				"type@=ranklist/rid@=1/gid@=-9999/list_all@=榜单结构体/list@=榜单结构体/list_day@=榜单结构体/",
				"type@=ssd/rid@=1/gid@=-9999/sdid@=1/trid@=1/content@=test/clitp@=1/url@=test_url/jmptp@=1/",
				"type@=spbc/rid@=1/gid@=1/gfid@=1/sn@=name/dn@=name/gn@=1/gc@=1/gb@=1/es@=1/gfid@=1/eid@=1/",
				"type@=online_noble_list/rid@=1/num@=3/nl@=贵族列表结构/",
				"type@=blab/rid@=1/uid@=10002/nn@=test/lbl@=2/bl@=3/ba@=1/bnn@=ttt/",
				"type@=frank/rid@=10111/fc@=200/bnn@=test/ver@=1/list@=榜单结构/",
				"type@=synexp/o_exp@=14053053060/o_lev@=91/o_minexp@=13954500000/o_upexp@=801446940/rid@=2020877/timestamp@=1513593136037/messageId@=20/"
		};
		for(String template:templates){
			mapOfTypeAndFields.put(matchStringValue(template, "type"), extractFields(template));
		}
	}
}
