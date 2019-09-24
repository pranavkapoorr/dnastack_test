package com.dnastack.interview.beaconsummarizer.utils;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class ListInterceptor implements RequestInterceptor {
 
	@Override
	public void apply(RequestTemplate template) {
		template.queries().forEach((key, value) -> {
            if (value.size() > 1) {
                template.query(key, Arrays.toString(value.toArray()));
            }
        });
	}
	

}