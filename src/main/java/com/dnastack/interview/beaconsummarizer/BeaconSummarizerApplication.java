package com.dnastack.interview.beaconsummarizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BeaconSummarizerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeaconSummarizerApplication.class, args);
	}
	
}
