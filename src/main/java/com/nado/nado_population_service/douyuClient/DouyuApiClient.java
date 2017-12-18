package com.nado.nado_population_service.douyuClient;

import com.nado.nado_population_service.douyuClient.entity.DouyuApiRoom;

public interface DouyuApiClient{
	DouyuApiRoom queryRoom(String room);
	DouyuApiRoom queryRoom(int room);
}
