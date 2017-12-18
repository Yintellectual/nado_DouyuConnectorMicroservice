package com.nado.nado_population_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.nado.nado_population_service.douyuClient.DouyuDanmuClient;
import com.nado.nado_population_service.douyuClient.impl.DouyuDanmuClientQueueImpl;

@EnableScheduling
@SpringBootApplication
public class NadoPopulationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NadoPopulationServiceApplication.class, args);
	}


	@Autowired
	DouyuDanmuClient client;
	@Component
	public class MyRunner implements CommandLineRunner {
		@Override
		public void run(String... args) throws Exception {
			client.register(2020877+"");
			while(true){
				System.out.println(client.take());
			}
		}
	}
}
