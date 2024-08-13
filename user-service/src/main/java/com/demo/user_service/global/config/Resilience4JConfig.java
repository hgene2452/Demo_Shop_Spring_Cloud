package com.demo.user_service.global.config;

import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration
public class Resilience4JConfig {

	@Bean
	public Customizer<Resilience4JCircuitBreakerFactory> globalCustomConfiguration() {

		// the circuitBreakerConfig and timeLimiterConfig objects
		CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
			.failureRateThreshold(4) // CircuitBreaker를 열지 결정하는 failure rate (default: 50/100)
			.waitDurationInOpenState(Duration.ofMillis(1000)) // CircuitBreaker를 open한 상태를 유지하는 지속 기간 (default: 60sec)
			.slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED) // CircuitBreaker가 닫힐 때 통화 결과를 기록하는데 사용되는 슬라이딩 창의 유형
			.slidingWindowSize(2) // CircuitBreaker가 닫힐 때 호출 결과를 기록하는데 사용되는 슬라이딩 창의 크기를 구성 (default: 100)
			.build();

		TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
			.timeoutDuration(Duration.ofSeconds(4))
			.build();

		return factory -> factory.configureDefault(id ->
			new Resilience4JConfigBuilder(id)
				.timeLimiterConfig(timeLimiterConfig)
				.circuitBreakerConfig(circuitBreakerConfig)
				.build()
		);
	}
}
