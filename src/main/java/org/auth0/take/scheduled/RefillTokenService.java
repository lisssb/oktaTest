package org.auth0.take.scheduled;

import org.auth0.take.common.RateLimitConfiguration;
import org.auth0.take.common.RateLimitRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class RefillTokenService {
    @Autowired
    private RateLimitConfiguration rateLimitConfiguration;

    private static final Logger logger = LoggerFactory.getLogger(RateLimitConfiguration.class);


    @Scheduled(fixedDelayString = "#{@rateLimitConfiguration.rateLimitsPerEndpoint[0].refillRate}")
    public void getUserRefill() {
        RateLimitRule rateLimitRule = rateLimitConfiguration.getRateLimitsPerEndpoint().get(0);
        refillToken(rateLimitRule);
        logger.debug("getUserRefill");
    }

    @Scheduled(fixedDelayString = "#{@rateLimitConfiguration.rateLimitsPerEndpoint[1].refillRate}")
    public void patchUserRefill() {
        RateLimitRule rateLimitRule = rateLimitConfiguration.getRateLimitsPerEndpoint().get(1);
        refillToken(rateLimitRule);
        logger.debug("patchUserRefill");
    }

    @Scheduled(fixedDelayString = "#{@rateLimitConfiguration.rateLimitsPerEndpoint[2].refillRate}")
    public void postUserInfoRefill() {
        RateLimitRule rateLimitRule = rateLimitConfiguration.getRateLimitsPerEndpoint().get(2);
        refillToken(rateLimitRule);
        logger.debug("postUserInfoRefill");
    }

    private void refillToken(RateLimitRule rateLimitRule){
        Integer token = rateLimitConfiguration.getBucket().get(rateLimitRule.getEndpoint()) + 1;
        Integer burst = rateLimitRule.getBurst();
        rateLimitConfiguration.getBucket().put(rateLimitRule.getEndpoint(), token > burst? burst : token);
    }
}
