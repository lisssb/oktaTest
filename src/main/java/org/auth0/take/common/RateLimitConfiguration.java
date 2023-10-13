package org.auth0.take.common;


import java.util.HashMap;
import java.util.List;

public class RateLimitConfiguration {
    private List<RateLimitRule> rateLimitsPerEndpoint;

    private HashMap<String, Integer> bucket = new HashMap<String, Integer>();

    public HashMap<String, Integer> getBucket(){
        return bucket;
    }
    public List<RateLimitRule> getRateLimitsPerEndpoint() {
        return rateLimitsPerEndpoint;
    }

    public void setRateLimitsPerEndpoint(List<RateLimitRule> rateLimitsPerEndpoint) {
        this.rateLimitsPerEndpoint = rateLimitsPerEndpoint;
    }
}
