package com.example.preorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableFeignClients
@SpringBootApplication
public class PreOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(PreOrderApplication.class, args);
	}

}
