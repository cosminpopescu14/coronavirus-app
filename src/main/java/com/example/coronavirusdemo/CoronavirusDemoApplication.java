package com.example.coronavirusdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//https://www.youtube.com/watch?v=8hjNG9GZGnQ
@SpringBootApplication
@EnableScheduling
public class CoronavirusDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoronavirusDemoApplication.class, args);
	}

}
