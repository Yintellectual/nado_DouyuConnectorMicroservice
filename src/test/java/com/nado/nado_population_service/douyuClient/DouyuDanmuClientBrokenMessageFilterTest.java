package com.nado.nado_population_service.douyuClient;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class DouyuDanmuClientBrokenMessageFilterTest {
	//@Autowired
	private DouyuDanmuClient client;
	//@Before
	public void Before() throws InterruptedException{
		client.register(2020877+"");
	}
	//@After
	public void after() throws InterruptedException {
		client.logout();
		client.clear();
	}
	
	
}
