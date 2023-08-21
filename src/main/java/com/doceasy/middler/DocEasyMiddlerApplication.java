package com.doceasy.middler;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class DocEasyMiddlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocEasyMiddlerApplication.class, args);
	}

}
