package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ReceiveApplication {

	public static void main(String[] args) {
//		SpringApplication.run(ReceiveApplication.class, args);
		new SpringApplicationBuilder(ReceiveApplication.class)
			.web(WebApplicationType.NONE)
			.run(args);
	}

}
