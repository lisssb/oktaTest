package org.auth0.take;

import org.auth0.take.common.RateLimitConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest
public class TakeServiceTest {
    @Mock
    private RateLimitConfiguration rateLimitConfiguration;

    @InjectMocks
    private TakeService takeService;

    @Test
    public void testCheckEndpointWithTokensAvailable() {
        String endpoint = "GET /user/:id";
        int initialTokens = 5;

        when(rateLimitConfiguration.getBucket()).thenReturn(new HashMap<>());
        rateLimitConfiguration.getBucket().put(endpoint, initialTokens);

        ResponseEntity<TokenResponse> response = takeService.checkEnpoint(endpoint);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(initialTokens - 1, response.getBody().getTokens());
    }

    @Test
    public void testCheckEndpointWithNoTokensAvailable() {
        String endpoint = "GET /user/:id";
        int initialTokens = 0; // No tokens available

        // Mock behavior of RateLimitConfiguration
        when(rateLimitConfiguration.getBucket()).thenReturn(new HashMap<>());
        rateLimitConfiguration.getBucket().put(endpoint, initialTokens);

        ResponseEntity<TokenResponse> response = takeService.checkEnpoint(endpoint);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals(0, response.getBody().getTokens());
    }
}
