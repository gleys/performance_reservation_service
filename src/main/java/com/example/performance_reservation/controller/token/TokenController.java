package com.example.performance_reservation.controller.token;

import com.example.performance_reservation.controller.token.response.QueueingInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Tag(name = "대기열")
@RequestMapping("/tokens")
@RestController
public class TokenController {
    private final ConcurrentHashMap<UUID, QueueingInfoResponse> mockTokens = new ConcurrentHashMap<>();
    private final Random random = new Random();

    @Operation(summary = "대기열 갱신")
    @GetMapping
    public QueueingInfoResponse getToken(@RequestHeader("token") final UUID token) {
        return new QueueingInfoResponse(1, 10000);
    }

    @Operation(summary = "대기열 토큰 발급")
    @PostMapping
    public UUID generateToken(@RequestParam("user_id") final int userId) {
        QueueingInfoResponse token = new QueueingInfoResponse(userId, random.nextInt(1000));
        UUID uuid = UUID.fromString(String.valueOf(userId));
        mockTokens.put(uuid, token);
        return uuid;
    }

}
