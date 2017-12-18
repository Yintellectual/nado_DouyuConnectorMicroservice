package com.nado.nado_population_service.douyuClient;

public interface DouyuDanmuClient {
	public byte[] send(String message);
	public void register(String room_id);
	public void logout();
	public String take();
	public int size();
	public void clear();
}
