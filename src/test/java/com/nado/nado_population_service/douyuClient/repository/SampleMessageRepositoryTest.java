package com.nado.nado_population_service.douyuClient.repository;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static com.nado.nado_population_service.util.CommonUtil.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.nado.nado_population_service.enums.MessageIntegrityStatuses;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleMessageRepositoryTest {

	@Autowired
	SampleMessageRepository repository;
	
	@Test
	public void alwaysPassTest() {
		assertTrue(true);
	}
	@Test
	public void maximumSizeIs100() {
		assertTrue(repository.getMaximumSizeForEachType()==100);
	}
	@Test
	public void save110RetrieveTheLatest100() {
		IntStream.rangeClosed(1, 110).forEach(i->{
			repository.saveSample(""+i,"chatmsg", MessageIntegrityStatuses.total);
		});
		List<String> result = repository.retieveSamplesByType("chatmsg", MessageIntegrityStatuses.total);
		assertTrue("size is "+result.size()+" but should be 100", result.size()==100);
		IntStream.rangeClosed(11, 110).forEach(i->{
			assertTrue(result.contains(i+""));
		});
	}
}
