package com.mc.cityconnections.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@org.springframework.stereotype.Service
public class Service {

	private final Log LOG = LogFactory.getLog(getClass());

	private Map<String, List<String>> cityMap = new HashMap<>();

	@Value("${data.file:classpath:city.txt}")
	private String CITIES;

	@Autowired
	private ResourceLoader resourceLoader;

	@PostConstruct
	private void read() throws IOException {

		LOG.info("Reading data");

		Resource resource = resourceLoader.getResource(CITIES);

		InputStream is;

		if (!resource.exists()) {
			// file on the filesystem path
			is = new FileInputStream(new File(CITIES));
		} else {
			// file is a classpath resource
			is = resource.getInputStream();
		}

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
			cityMap = reader.lines().collect(Collectors.toList()).stream().map(str -> str.split(","))
					.collect(Collectors.toMap(p -> p[0], p -> new ArrayList<>(Arrays.asList(p[1].trim())), (o, n) -> {
						o.addAll(n);
						return o;
					}));
			LOG.info("Map: " + cityMap);
		}
	}

	public String isConnected(String origin, String destination) {
		return cityMap.containsKey(origin)
				? cityMap.get(origin).stream().filter(e -> e.equalsIgnoreCase(destination)).findAny().isPresent()
						? "yes"
						: "no"
				: "no";
	}

}
