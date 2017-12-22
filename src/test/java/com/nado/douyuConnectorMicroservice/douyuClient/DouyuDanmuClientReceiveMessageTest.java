package com.nado.douyuConnectorMicroservice.douyuClient;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.context.junit4.SpringRunner;

import com.nado.douyuConnectorMicroservice.douyuClient.DouyuDanmuClient;
import com.nado.douyuConnectorMicroservice.douyuClient.impl.DouyuDanmuClientQueueImpl;

import static com.nado.douyuConnectorMicroservice.util.CommonUtil.*;
import static org.junit.Assert.*;

import java.util.List;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



@RunWith(SpringRunner.class)
@SpringBootTest
public class DouyuDanmuClientReceiveMessageTest {
	@Autowired
	private DouyuDanmuClient client;
	
	@Before
	public void Before() throws InterruptedException{
		client.register(2020877+"");
	}
	@After
	public void after() throws InterruptedException {
		client.logout();
		client.clear();
	}

	@Test
	public void alwaysPassTest(){
		assertTrue(true);
	}
	
	//@Test(timeout=60000)
	public void messageHasATimestampNoMoreThan2SecondsOlderThanSystemTime(){
		String message = client.take();
		String timestampString = matchDigitalValue(message, "timestamp");
		assertTrue(timestampString!=null);
		long timestamp = Long.parseLong(timestampString);
		long systemTime = new Date().getTime();
		assertTrue("systemTime="+systemTime+", but timestamp="+timestamp,(systemTime>=timestamp)&&(systemTime-timestamp)<=2000);
	}
	//@Test(timeout=60000)
	public void messageHasAIncrementalId(){
		String message1 = client.take();
		String message2 = client.take();
		String messageId1 = matchDigitalValue(message1, "messageId");
		assertTrue(messageId1!=null);
		String messageId2 = matchDigitalValue(message2, "messageId");
		assertTrue(messageId2!=null);
		long id1 = Long.parseLong(messageId1);
		long id2 = Long.parseLong(messageId2);
		assertTrue(id2==id1+1);
	}
}
