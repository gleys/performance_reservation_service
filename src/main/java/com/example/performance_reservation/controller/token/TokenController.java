package com.example.performance_reservation.controller.token;

import com.example.performance_reservation.controller.token.response.TokenResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@RequestMapping("/tokens")
@RestController
public class TokenController {
    private final ConcurrentHashMap<Integer, TokenResponse> mockTokens = new ConcurrentHashMap<>();
    private final Random random = new Random();

    @GetMapping
    public TokenResponse getToken(@RequestParam("user_id") final int userId) {
        return mockTokens.get(userId);
    }

    @PostMapping
    public void generateToken(@RequestParam("user_id") final int userId) {
        TokenResponse token = new TokenResponse(userId, random.nextInt(1000), random.nextInt(10000));
        mockTokens.put(userId, token);
    }

}
