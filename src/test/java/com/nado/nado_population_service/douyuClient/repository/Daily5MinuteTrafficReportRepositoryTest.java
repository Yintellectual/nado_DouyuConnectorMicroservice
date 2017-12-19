package com.nado.nado_population_service.douyuClient.repository;

import static org.junit.Assert.*;

import java.util.Map;

import static com.nado.nado_population_service.util.CommonUtil.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.nado.nado_population_service.enums.TrafficReportField;
@RunWith(SpringRunner.class)
@SpringBootTest
public class Daily5MinuteTrafficReportRepositoryTest {

	@Autowired
	Daily5MinuteTrafficReportRepository repository;
	
	@Test
	public void alwaysPassTest(){
		assertTrue(true);
	}
	private Map<String, String> todaysRecordsOfTotal; 
	private Map<String, String> todaysRecordsOfBroken;
	private Map<String, String> yesterdaysRecordsOfTotal; 
	private Map<String, String> yesterdaysRecordsOfBroken;
	private final static String TODAY = "2017-12-18";
	private final static String YESTERDAY = "2017-12-17";
	@Before
	public void saveSampleRecords(){
		Map<String, String> sampleRecords = generateSampleRecords();
		for(String key:sampleRecords.keySet()){
			repository.saveRecord(TODAY, key, sampleRecords.get(key), ""+Integer.parseInt(sampleRecords.get(key))/100);	
			repository.saveRecord(YESTERDAY, key, "1"+sampleRecords.get(key), "1"+Integer.parseInt(sampleRecords.get(key))/100);
		}
		todaysRecordsOfTotal = repository.retieveRecordByDate(TODAY, TrafficReportField.total_message_count);
		todaysRecordsOfBroken= repository.retieveRecordByDate(TODAY, TrafficReportField.broken_message_count);
		yesterdaysRecordsOfTotal = repository.retieveRecordByDate(YESTERDAY, TrafficReportField.total_message_count);
		yesterdaysRecordsOfBroken= repository.retieveRecordByDate(YESTERDAY, TrafficReportField.broken_message_count);
	}
	/*
	 * {07:00=0, 07:05=1, 07:10=2, 07:15=3, 07:20=4, 07:25=5, 07:30=6, 07:35=7, 07:40=8, 07:45=9, 07:50=10, 07:55=11, 08:00=12, 08:05=13, 08:10=14, 08:15=15, 08:20=16, 08:25=17, 08:30=18, 08:35=19, 08:40=20, 08:45=21, 08:50=22, 08:55=23, 09:00=24, 09:05=25, 09:10=26, 09:15=27, 09:20=28, 09:25=29, 09:30=30, 09:35=31, 09:40=32, 09:45=33, 09:50=34, 09:55=35, 10:00=36, 10:05=37, 10:10=38, 10:15=39, 10:20=40, 10:25=41, 10:30=42, 10:35=43, 10:40=44, 10:45=45, 10:50=46, 10:55=47, 11:00=48, 11:05=49, 11:10=50, 11:15=51, 11:20=52, 11:25=53, 11:30=54, 11:35=55, 11:40=56, 11:45=57, 11:50=58, 11:55=59, 12:00=60, 12:05=61, 12:10=62, 12:15=63, 12:20=64, 12:25=65, 12:30=66, 12:35=67, 12:40=68, 12:45=69, 12:50=70, 12:55=71, 13:00=72, 13:05=73, 13:10=74, 13:15=75, 13:20=76, 13:25=77, 13:30=78, 13:35=79, 13:40=80, 13:45=81, 13:50=82, 13:55=83, 14:00=84, 14:05=85, 14:10=86, 14:15=87, 14:20=88, 14:25=89, 14:30=90, 14:35=91, 14:40=92, 14:45=93, 14:50=94, 14:55=95, 15:00=96, 15:05=97, 15:10=98, 15:15=99, 15:20=100, 15:25=101, 15:30=102, 15:35=103, 15:40=104, 15:45=105, 15:50=106, 15:55=107, 16:00=108, 16:05=109, 16:10=110, 16:15=111, 16:20=112, 16:25=113, 16:30=114, 16:35=115, 16:40=116, 16:45=117, 16:50=118, 16:55=119, 17:00=120, 17:05=121, 17:10=122, 17:15=123, 17:20=124, 17:25=125, 17:30=126, 17:35=127, 17:40=128, 17:45=129, 17:50=130, 17:55=131, 18:00=132, 18:05=133, 18:10=134, 18:15=135, 18:20=136, 18:25=137, 18:30=138, 18:35=139, 18:40=140, 18:45=141, 18:50=142, 18:55=143, 19:00=144, 19:05=145, 19:10=146, 19:15=147, 19:20=148, 19:25=149, 19:30=150, 19:35=151, 19:40=152, 19:45=153, 19:50=154, 19:55=155, 20:00=156, 20:05=157, 20:10=158, 20:15=159, 20:20=160, 20:25=161, 20:30=162, 20:35=163, 20:40=164, 20:45=165, 20:50=166, 20:55=167, 21:00=168, 21:05=169, 21:10=170, 21:15=171, 21:20=172, 21:25=173, 21:30=174, 21:35=175, 21:40=176, 21:45=177, 21:50=178, 21:55=179, 22:00=180, 22:05=181, 22:10=182, 22:15=183, 22:20=184, 22:25=185, 22:30=186, 22:35=187, 22:40=188, 22:45=189, 22:50=190, 22:55=191, 23:00=192, 23:05=193, 23:10=194, 23:15=195, 23:20=196, 23:25=197, 23:30=198, 23:35=199, 23:40=200, 23:45=201, 23:50=202, 23:55=203, 00:00=204, 00:05=205, 00:10=206, 00:15=207, 00:20=208, 00:25=209, 00:30=210, 00:35=211, 00:40=212, 00:45=213, 00:50=214, 00:55=215, 01:00=216, 01:05=217, 01:10=218, 01:15=219, 01:20=220, 01:25=221, 01:30=222, 01:35=223, 01:40=224, 01:45=225, 01:50=226, 01:55=227, 02:00=228, 02:05=229, 02:10=230, 02:15=231, 02:20=232, 02:25=233, 02:30=234, 02:35=235, 02:40=236, 02:45=237, 02:50=238, 02:55=239, 03:00=240, 03:05=241, 03:10=242, 03:15=243, 03:20=244, 03:25=245, 03:30=246, 03:35=247, 03:40=248, 03:45=249, 03:50=250, 03:55=251, 04:00=252, 04:05=253, 04:10=254, 04:15=255, 04:20=256, 04:25=257, 04:30=258, 04:35=259, 04:40=260, 04:45=261, 04:50=262, 04:55=263, 05:00=264, 05:05=265, 05:10=266, 05:15=267, 05:20=268, 05:25=269, 05:30=270, 05:35=271, 05:40=272, 05:45=273, 05:50=274, 05:55=275, 06:00=276, 06:05=277, 06:10=278, 06:15=279, 06:20=280, 06:25=281, 06:30=282, 06:35=283, 06:40=284, 06:45=285, 06:50=286, 06:55=287}
	 * */
	@After
	public void eraseSampleRecords(){
		repository.eraseRecordsByDate(TODAY);
		repository.eraseRecordsByDate(YESTERDAY);
	}

	@Test
	public void recordsAreRetrievedAsMap(){
		assertNotNull(todaysRecordsOfBroken);
		assertNotNull(todaysRecordsOfTotal);
		assertNotNull(yesterdaysRecordsOfBroken);
		assertNotNull(yesterdaysRecordsOfTotal);
	}
	@Test
	public void recordsHasCompleteKeySet(){
		assertTrue(todaysRecordsOfTotal.keySet().size()==288);
		assertTrue("todaysRecordsOfBroken.keySet().size()=="+todaysRecordsOfBroken.keySet().size()+"!=288",todaysRecordsOfBroken.keySet().size()==288);
		assertTrue(yesterdaysRecordsOfTotal.keySet().size()==288);
		assertTrue(yesterdaysRecordsOfBroken.keySet().size()==288);
	}
	@Test
	public void recordsHasCorrectValue(){
		for(String key: todaysRecordsOfTotal.keySet()){
			String[] splitedKey = key.split(":");
			int hour = Integer.parseInt(splitedKey[0]);
			int minute = Integer.parseInt(splitedKey[1]);
			if(hour<7){
				hour+=24;
			}
			hour-=7;
			int correntValue = minute/5+hour*12;
			assertTrue((correntValue+"").equals(todaysRecordsOfTotal.get(key)));
			assertTrue((correntValue/100+"").equals(todaysRecordsOfBroken.get(key)));
			assertTrue(("1"+correntValue).equals(yesterdaysRecordsOfTotal.get(key)));
			assertTrue("1"+correntValue/100+"!="+yesterdaysRecordsOfBroken.get(key) ,("1"+correntValue/100).equals(yesterdaysRecordsOfBroken.get(key)));
			assertTrue((correntValue+"").equals(repository.retieveRecord(TODAY, key, TrafficReportField.total_message_count)));
			assertTrue((correntValue/100+"").equals(repository.retieveRecord(TODAY, key, TrafficReportField.broken_message_count)));
			assertTrue(("1"+correntValue).equals(repository.retieveRecord(YESTERDAY, key, TrafficReportField.total_message_count)));
			assertTrue(("1"+correntValue/100).equals(repository.retieveRecord(YESTERDAY, key, TrafficReportField.broken_message_count)));
		}
	}
}
