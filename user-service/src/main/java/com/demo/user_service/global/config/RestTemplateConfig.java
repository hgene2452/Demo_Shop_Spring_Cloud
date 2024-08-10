package com.demo.user_service.global.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

	@Bean
	@LoadBalanced // 다른 서비스의 ip를 직접 명시하지 않고 이름을 사용하기 위함
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
