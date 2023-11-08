package com.fis.tool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ToolApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToolApplication.class, args);
	}
	
	@Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

	@Bean 
	BCryptPasswordEncoder passencode() {;
		return new BCryptPasswordEncoder();
	}

}
