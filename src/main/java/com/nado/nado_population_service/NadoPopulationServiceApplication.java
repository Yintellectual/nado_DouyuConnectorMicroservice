package com.nado.nado_population_service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.nado.nado_population_service.douyuClient.DouyuDanmuBrokenMessageFilter;
import com.nado.nado_population_service.douyuClient.DouyuDanmuClient;
import com.nado.nado_population_service.douyuClient.impl.DouyuDanmuClientQueueImpl;
import com.nado.nado_population_service.util.CommonUtil;
import static com.nado.nado_population_service.util.CommonUtil.*;
@EnableScheduling
@SpringBootApplication
public class NadoPopulationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NadoPopulationServiceApplication.class, args);
	}


	@Autowired
	DouyuDanmuClient client;
	@Autowired
	private DouyuDanmuBrokenMessageFilter clientWrapper;
	@Component
	public class MyRunner implements CommandLineRunner {
		@Override
		public void run(String... args) throws Exception {
			client.register(2020877+"");
			clientWrapper.testAndSaveBrokenMessage();
			
//			while(true){
//				String msg= client.take();
//				if(msg.contains("respond")){
//					System.out.println(msg);
//				}else{
//					//System.out.println(CommonUtil.matchStringValue(msg, "type"));
//					System.out.println(msg);
//				}
//			}
		//	String string = "type@=rri/rid@=1/rn@=cate_rank/cate_id@=5/uid@=10005/sc@=10000/idx@=10/bcr@=1/ibc@=1/an@=test/rktype@=1/tag_id@=1200/gift_id@=100/";
		//	System.out.println(CommonUtil.extractFields(string));
		//	System.out.println(mapOfTypeAndFields);
		}
	}
}
