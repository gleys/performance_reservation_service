package com.example.performance_reservation.controller.token;

import com.example.performance_reservation.controller.token.response.GenerateTokenResponse;
import com.example.performance_reservation.controller.token.response.WaitingInfoResponse;
import com.example.performance_reservation.domain.waitingqueue.WaitingQueue;
import com.example.performance_reservation.domain.waitingqueue.domain.WaitingInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "대기열")
@RequiredArgsConstructor
@RequestMapping("/tokens")
@RestController
public class TokenController {
    private final WaitingQueue waitingQueue;
    @Operation(summary = "대기열 갱신")
    @GetMapping
    public WaitingInfoResponse getToken(@RequestHeader("token") final UUID token) {
        WaitingInfo waitingInfo = waitingQueue.getInfo(token);
        return new WaitingInfoResponse(waitingInfo.waitingNum(), waitingInfo.totalNum());
    }

    @Operation(summary = "대기열 토큰 발급")
    @PostMapping
    public GenerateTokenResponse generateToken(@RequestParam("user_id") final int userId) {
        UUID token = waitingQueue.waiting(userId);
        return new GenerateTokenResponse(token);
    }

}
