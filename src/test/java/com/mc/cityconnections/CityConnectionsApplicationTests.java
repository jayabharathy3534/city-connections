package com.mc.cityconnections;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class CityConnectionsApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void contextLoads() {
	}

	@Test
	public void restNotConnectedIT() {

		Map<String, String> params = new HashMap<>();
		params.put("origin", "Boston");
		params.put("destination", "Newark");

		String body = restTemplate.getForObject("/connected?origin={origin}&destination={destination}", String.class,
				params);
		Assert.assertEquals("yes", body);
	}

}
