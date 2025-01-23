package com.sys.spring_webflux_playground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication(scanBasePackages = "com.sys.spring_webflux_playground.${sec}")
@EnableR2dbcRepositories(basePackages = "com.sys.spring_webflux_playground.${sec}")
public class SpringWebfluxPlaygroundApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWebfluxPlaygroundApplication.class, args);
	}

}
