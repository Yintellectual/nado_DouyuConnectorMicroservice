package com.nado.nado_population_service.douyuClient;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.springframework.test.util.ReflectionTestUtils.*;
import java.util.Arrays;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.nado.nado_population_service.douyuClient.entity.DouyuApiGift;
import com.nado.nado_population_service.douyuClient.entity.DouyuApiRoom;
import com.nado.nado_population_service.douyuClient.impl.DouyuApiClientNaiveImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DouyuApiClientTest {

	private DouyuApiClient client;
	private DouyuApiRoom room;
	private DouyuApiRoom.DouyuApiData data;
	private DouyuApiGift[] gifts;
	
	
	@Before
	public void Before(){
		client = new DouyuApiClientNaiveImpl();
		setField(client, "restTemplate", new RestTemplate());
		room = client.queryRoom(2020877);
		data = room.getData();
		gifts = data.getGift();
	}
	@Test
	public void queryRoom2020877ContainsMoreThanOneGifts() {
		assertTrue("gifts.length="+gifts.length+", but should >0", gifts.length>0);
	}
	@Test
	public void gift1005HasCorrectPropertyValues(){
		Arrays.stream(gifts).filter(gift->"1005".equals(gift.getId())).forEach(gift->{
			assertTrue("超级火箭".equals(gift.getName()));
			assertTrue("2".equals(gift.getType()));
			assertTrue("2000".equals(gift.getPc()));
			assertTrue(20000 == gift.getGx());
		});
	}
	@Test
	public void gift196HasCorrectPropertyValues(){
		Arrays.stream(gifts).filter(gift->"196".equals(gift.getId())).forEach(gift->{
			assertTrue("火箭".equals(gift.getName()));
			assertTrue("2".equals(gift.getType()));
			assertTrue("500".equals(gift.getPc()));
			assertTrue(5000 == gift.getGx());
		});
	}
	@Test
	public void gift195HasCorrectPropertyValues(){
		Arrays.stream(gifts).filter(gift->"195".equals(gift.getId())).forEach(gift->{
			assertTrue("飞机".equals(gift.getName()));
			assertTrue("2".equals(gift.getType()));
			assertTrue("100".equals(gift.getPc()));
			assertTrue(1000 == gift.getGx());
		});
	}
	@Test
	public void gift1226HasCorrectPropertyValues(){
		Arrays.stream(gifts).filter(gift->"1226".equals(gift.getId())).forEach(gift->{
			assertTrue("办卡".equals(gift.getName()));
			assertTrue("2".equals(gift.getType()));
			assertTrue("6".equals(gift.getPc()));
			assertTrue(60 == gift.getGx());
		});
	}
	@Test
	public void gift989HasCorrectPropertyValues(){
		Arrays.stream(gifts).filter(gift->"989".equals(gift.getId())).forEach(gift->{
			assertTrue("猫耳".equals(gift.getName()));
			assertTrue("2".equals(gift.getType()));
			assertTrue("0.2".equals(gift.getPc()));
			assertTrue(2 == gift.getGx());
		});
	}
	@Test
	public void gift956HasCorrectPropertyValues(){
		Arrays.stream(gifts).filter(gift->"956".equals(gift.getId())).forEach(gift->{
			assertTrue("打call".equals(gift.getName()));
			assertTrue("2".equals(gift.getType()));
			assertTrue("0.1".equals(gift.getPc()));
			assertTrue(1 == gift.getGx());
		});
	}
	@Test
	public void gift191HasCorrectPropertyValues(){
		Arrays.stream(gifts).filter(gift->"191".equals(gift.getId())).forEach(gift->{
			assertTrue("gift:"+gift, "100鱼丸".equals(gift.getName()));
			assertTrue("gift:"+gift, "1".equals(gift.getType()));
			assertTrue("gift:"+gift, "100".equals(gift.getPc()));
			assertTrue("gift:"+gift, 1 == gift.getGx());
		});
	}
	@Test
	public void queryRoom2020877GetRoomId2020877(){
		assertTrue("2020877".equals(data.getRoom_id()));
	}
	@Test
	public void queryRoom2020877GetOwnerNado(){
		assertTrue("纳豆nado".equals(data.getOwner_name()));
	}
}
