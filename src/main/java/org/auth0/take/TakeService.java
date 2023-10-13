package org.auth0.take;

import org.auth0.take.common.RateLimitConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TakeService {
    @Autowired
    private RateLimitConfiguration rateLimitConfiguration;
    public ResponseEntity<TokenResponse> checkEnpoint(String endpoint) {
        int tokens = rateLimitConfiguration.getBucket().get(endpoint);
        if (tokens > 0) {
            tokens --;
            rateLimitConfiguration.getBucket().put(endpoint, tokens);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new TokenResponse(tokens));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new TokenResponse(0));
        }
    }
}
