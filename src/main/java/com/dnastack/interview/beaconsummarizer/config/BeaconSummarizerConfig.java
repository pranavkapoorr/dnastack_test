package com.dnastack.interview.beaconsummarizer.config;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.*;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;



@Configuration
@EnableAsync
public class BeaconSummarizerConfig extends AsyncConfigurerSupport{
		@Override
		public Executor getAsyncExecutor() {
			ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
			executor.setMaxPoolSize(100);
			executor.setCorePoolSize(75);
			executor.setQueueCapacity(75);
			return executor;
		}
	    
}
