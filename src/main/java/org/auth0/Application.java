package org.auth0;
import org.auth0.take.common.RateLimitConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

@SpringBootApplication
@EnableScheduling
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    @ConfigurationProperties(prefix = "ratelimit")
    public RateLimitConfiguration rateLimitConfiguration() throws IOException {
        RateLimitConfiguration rateLimitConfiguration = objectMapper.readValue(getClass().getResourceAsStream("/config.json"), RateLimitConfiguration.class);
        rateLimitConfiguration.getRateLimitsPerEndpoint().stream().forEach(rateLimitRule -> rateLimitConfiguration.getBucket().put(rateLimitRule.getEndpoint(), rateLimitRule.getBurst()));
        return rateLimitConfiguration;
    }
}

