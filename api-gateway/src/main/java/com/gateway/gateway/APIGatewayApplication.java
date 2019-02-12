package com.gateway.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages={ APIGatewayApplication.COM_GATEWAY })
public class APIGatewayApplication {

	static final String COM_GATEWAY = "com.gateway.*";

	public static void main(String[] args) {
		SpringApplication.run(APIGatewayApplication.class, args);
	}

}

