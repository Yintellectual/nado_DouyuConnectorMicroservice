package com.nado.nado_population_service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

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

    @Bean(destroyMethod = "shutdown")
    public Executor taskScheduler() {
        return Executors.newScheduledThreadPool(5);
    }
	
	@Autowired
	DouyuDanmuClient client;
	@Autowired
	private DouyuDanmuBrokenMessageFilter clientWrapper;

	@Component
	public class MyRunner implements CommandLineRunner {
		@Override
		public void run(String... args) throws Exception {
			//System.out.println(generateSampleRecords());
			 client.register(2020877+"");
			 clientWrapper.wrapClient(client);

			// while(true){
			// String msg= client.take();
			// if(msg.contains("respond")){
			// System.out.println(msg);
			// }else{
			// //System.out.println(CommonUtil.matchStringValue(msg, "type"));
			// System.out.println(msg);
			// }
			// }
			// String string =
			// "type@=rri/rid@=1/rn@=cate_rank/cate_id@=5/uid@=10005/sc@=10000/idx@=10/bcr@=1/ibc@=1/an@=test/rktype@=1/tag_id@=1200/gift_id@=100/";
			// System.out.println(CommonUtil.extractFields(string));
			// System.out.println(mapOfTypeAndFields);
			// String fileName = "c:/nado/refinedSample.txt";
			//
			// try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			//
			// stream.forEach(message->{
			// if(clientWrapper.isBrokenMessage(message)>0){
			// try {
			// Files.write(Paths.get("c:/nado/refinedSample2.txt"), (message +
			// "\n").getBytes(), StandardOpenOption.APPEND,
			// StandardOpenOption.CREATE);
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
			// });
			//
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
			//
			// System.out.println("\n\n\n!!!!!!!!!!!!!!!!!!!!!!!!!!!done!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			// System.out.println(mapOfTypeAndFields);
			// String fileName = "c:/nado/refinedSample2.txt";
			//
			// try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			//
			// stream.forEach(message -> {
			// if(!(message.contains("type")&&message.contains("timestamp")&&message.contains("messageId"))){
			// try {
			// Files.write(Paths.get("c:/nado/refinedSample4.txt"), (message +
			// "\n").getBytes(),
			// StandardOpenOption.APPEND, StandardOpenOption.CREATE);
			// } catch (IOException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
			// }else{
			// String[] splitedMessage = message.split("timestamp");
			// String newMessage = "";
			// try{
			// newMessage =
			// "timestamp"+splitedMessage[1]+message.split("timestamp")[0];
			// }catch(Exception e){
			// e.printStackTrace();
			// }
			// try {
			// Files.write(Paths.get("c:/nado/refinedSample3.txt"), (newMessage
			// + "\n").getBytes(),
			// StandardOpenOption.APPEND, StandardOpenOption.CREATE);
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
			// });
			// } catch (IOException e) {
			// e.printStackTrace();
			// }

			
			System.out.println("\n\n\n!!!!!!!!!!!!!!!!!!!!!!!!!!!done!!!!!!!!!!!!!!!!!!!!!!!!!!!");

		}
	}
}
