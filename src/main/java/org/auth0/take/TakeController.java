package org.auth0.take;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TakeController {

    @Autowired
    TakeService takeService;

    @GetMapping("/take")
    public ResponseEntity<TokenResponse> take(@RequestParam String endpoint) {
        return takeService.checkEnpoint(endpoint);
    }
}
