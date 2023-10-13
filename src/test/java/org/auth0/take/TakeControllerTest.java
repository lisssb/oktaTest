package org.auth0.take;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TakeControllerTest {

    @InjectMocks
    private TakeController takeController;

    @Mock
    private TakeService takeService;


    @Test
    public void testTakeWithTokensAvailable() {

        String endpoint = "GET /user/:id";
        int tokens = 1; // Adjust the expected token count
        when(takeService.checkEnpoint(endpoint)).thenReturn(ResponseEntity.status(HttpStatus.ACCEPTED).body(new TokenResponse(tokens)));

        ResponseEntity<TokenResponse> response = takeController.take(endpoint);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(tokens, response.getBody().getTokens());
    }

    @Test
    public void testTakeWithNoTokensAvailable() {
        String endpoint = "GET /user/:id";
        int tokens = 0; // No tokens available
        when(takeService.checkEnpoint(endpoint)).thenReturn(ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new TokenResponse(tokens)));

        ResponseEntity<TokenResponse> response = takeController.take(endpoint);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals(tokens, response.getBody().getTokens());
    }
}
