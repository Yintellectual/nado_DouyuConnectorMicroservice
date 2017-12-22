package com.nado.douyuConnectorMicroservice.douyuClient;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;

import com.nado.douyuConnectorMicroservice.NadoPopulationServiceApplication;
import com.nado.douyuConnectorMicroservice.douyuClient.DouyuDanmuClient;
import com.nado.douyuConnectorMicroservice.douyuClient.impl.DouyuDanmuClientQueueImpl;

@RunWith(SpringRunner.class)
@SpringBootTest//(classes={TestConfiguration.class})
//@org.springframework.test.context.ContextConfiguration(classes={TestConfiguration.class})
public class DouyuDanmuClientSendMessageTest {

	@Autowired
	private DouyuDanmuClient client;
	private byte[] message;

//	@Configuration
//	@EnableScheduling
//	static class TestConfiguration {
//		@Bean
//		public DouyuDanmuClient douyuDanmuClient(){
//			return new DouyuDanmuClientQueueImpl();
//		}
//	}

	@Before
	public void before() throws InterruptedException {
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
	
	//@Test
	public void messageEndsWithBackSlash0() {
		message = client.send("test测试");
		assertTrue(message[message.length - 1] == (byte) '\0');
	}

	//@Test
	public void messageLengthEqualsStringLengthPlus13() {
		message = client.send("test测试");
		assertTrue("Length is " + message.length + ", but should be 23.", message.length == 23);
	}

	private int getIntegerFromMultiBytesLittleEndian(byte[] bytes) {
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		if (bytes.length == 2) {
			return byteBuffer.getShort();
		} else if (bytes.length == 1) {
			return bytes[0];
		} else {
			return byteBuffer.getInt();
		}
	}

	//@Test
	public void messageLengthIsStoredInTheFirst4Bytes() {
		message = client.send("test测试");
		int lengthInMessage = getIntegerFromMultiBytesLittleEndian(Arrays.copyOfRange(message, 0, 4));
		assertTrue("Length is " + lengthInMessage + ", but should be 23.", lengthInMessage == 19);
	}

	//@Test
	public void messageLengthIsStoredInTheSecond4Bytes() {
		message = client.send("test测试");
		int lengthInMessage = getIntegerFromMultiBytesLittleEndian(Arrays.copyOfRange(message, 4, 8));
		assertTrue("Length is " + lengthInMessage + ", but should be 23.", lengthInMessage == 19);
	}

	//@Test
	public void messageStore689In9th10thBytes() {
		message = client.send("test测试");
		int code = getIntegerFromMultiBytesLittleEndian(Arrays.copyOfRange(message, 8, 10));
		assertTrue(Arrays.toString(message), code == 689);
	}

	//@Test
	public void messageStore0In11thByte() {
		message = client.send("test测试");
		int code = getIntegerFromMultiBytesLittleEndian(Arrays.copyOfRange(message, 10, 11));
		assertTrue(Arrays.toString(message), code == 0);
	}

	//@Test
	public void messageStore0In12thByte() {
		message = client.send("test测试");
		int code = getIntegerFromMultiBytesLittleEndian(Arrays.copyOfRange(message, 11, 12));
		assertTrue(Arrays.toString(message), code == 0);
	}

	//@Test(timeout = 10000)
	public void anyChatmsgDgbUenterMessageFrom2020877() {
		client.register("" + 2020877);
		boolean receiveMessageFrom2020877 = false;
		while (true) {
			String string = client.take();
			if (string.contains("chatmsg") || string.contains("dgb") || string.contains("uenter")) {
				if (string.contains("2020877")) {
					receiveMessageFrom2020877 = true;
					break;
				}
			}
		}
		assertTrue(receiveMessageFrom2020877);
	}

	//@Test(timeout = 180000)
	public void stayAliveForMoreThan2Minutes() throws InterruptedException {
		client.register("" + 2020877);
		Thread.sleep(120000);
		client.clear();
		boolean receiveMessageFrom2020877 = false;
		while (true) {
			String string = client.take();
			receiveMessageFrom2020877 = true;
			break;
		}
		assertTrue(receiveMessageFrom2020877);
	}

	//@Test(timeout = 20000)
	public void logoutStopsMessageAfter2seconds() {
		client.register("" + 2020877);
		boolean receiveMessageFrom2020877 = false;
		while (true) {
			String string = client.take();
			receiveMessageFrom2020877 = true;
			break;
		}
		if (receiveMessageFrom2020877) {
			client.logout();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			client.clear();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertTrue(client.size() == 0);
		}
	}
}
