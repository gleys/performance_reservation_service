package com.example.performance_reservation.controller.wallet;

import com.example.performance_reservation.controller.wallet.response.BalanceInfoResponse;
import com.example.performance_reservation.controller.wallet.response.PointHistoryResponse;
import com.example.performance_reservation.domain.wallet.PointActivity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "잔고")
@RequestMapping("/wallets")
@RestController
public class WalletController {

    @Operation(summary = "잔고 조회")
    @GetMapping
    public BalanceInfoResponse getBalance(@RequestParam("user_id") final int userId) {
        return new BalanceInfoResponse(userId, 1000);
    }

    @Operation(summary = "잔액 충전")
    @PostMapping
    public void chargeBalance(
            @RequestParam("user_id") final int userId,
            @RequestParam("amount") final int amount
    ) {
    }

    @Operation(summary = "포인트 사용내역 조회")
    @GetMapping("{user_id}")
    public List<PointHistoryResponse> getPointHistory(@RequestParam("user_id") final long userId) {
        return List.of(
            new PointHistoryResponse(100000, PointActivity.CHARGE, LocalDateTime.now().minusDays(3)),
            new PointHistoryResponse(80000, PointActivity.USE, LocalDateTime.now().minusDays(2))
        );
    }
}
