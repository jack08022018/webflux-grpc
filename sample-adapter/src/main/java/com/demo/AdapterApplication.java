package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class AdapterApplication {

	public static void main(String[] args) {
//		SpringApplication.run(AdapterApplication.class, args);
		new SpringApplicationBuilder(AdapterApplication.class)
			.web(WebApplicationType.NONE)
			.run(args);
	}

}
