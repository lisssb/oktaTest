package org.auth0.take.scheduled;

import org.auth0.Application;
import org.auth0.take.common.RateLimitConfiguration;
import org.auth0.take.common.RateLimitRule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext // This annotation ensures that the Spring context is reloaded after each test method
public class RefillTokenServiceIntegrationTest {

    @Autowired
    private RefillTokenService refillTokenService;

    @Autowired
    private RateLimitConfiguration rateLimitConfiguration;

    @Test
    public void testGetUserRefill() throws InterruptedException {
        RateLimitRule rateLimitRule = new RateLimitRule();
        rateLimitRule.setEndpoint("GET /user/:id");
        rateLimitRule.setSustained(100);
        rateLimitRule.setBurst(100);

        rateLimitConfiguration.setRateLimitsPerEndpoint(new ArrayList<>(Arrays.asList(rateLimitRule)));
        rateLimitConfiguration.getBucket().put("GET /user/:id", 90);
        refillTokenService.getUserRefill();
        Thread.sleep(rateLimitRule.getRefillRate());

        assertEquals(rateLimitConfiguration.getBucket().get("GET /user/:id"), 91);
    }

    @Test
    public void testGetUserRefillMaxBurst() throws InterruptedException {
        RateLimitRule rateLimitRule = new RateLimitRule();
        rateLimitRule.setEndpoint("GET /user/:id");
        rateLimitRule.setSustained(100);
        rateLimitRule.setBurst(100);

        rateLimitConfiguration.setRateLimitsPerEndpoint(new ArrayList<>(Arrays.asList(rateLimitRule)));
        rateLimitConfiguration.getBucket().put("GET /user/:id", rateLimitRule.getBurst());
        refillTokenService.getUserRefill();
        Thread.sleep(rateLimitRule.getRefillRate());

        assertEquals(rateLimitConfiguration.getBucket().get("GET /user/:id"), rateLimitRule.getBurst());
    }


}
