package com.nado.nado_population_service.douyuClient.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.nado.nado_population_service.douyuClient.DouyuApiClient;
import com.nado.nado_population_service.douyuClient.entity.DouyuApiRoom;

public class DouyuApiClientNaiveImpl implements DouyuApiClient {

	@Autowired
	RestTemplate restTemplate;
	
	@Override
	public DouyuApiRoom queryRoom(String room) {
		
		DouyuApiRoom douyuApiRoom = restTemplate.getForObject("http://open.douyucdn.cn/api/RoomApi/room/"+room, DouyuApiRoom.class);
		
		return douyuApiRoom;
	}

	@Override
	public DouyuApiRoom queryRoom(int room) {
		return queryRoom(""+room);
	}
}
