package com.example.performance_reservation.controller.wallet;

import com.example.performance_reservation.controller.wallet.response.BalanceInfoResponse;
import com.example.performance_reservation.controller.wallet.response.PointHistoryResponse;
import com.example.performance_reservation.domain.wallet.PointActivity;
import com.example.performance_reservation.domain.wallet.PointHistory;
import com.example.performance_reservation.domain.wallet.Wallet;
import com.example.performance_reservation.domain.wallet.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "잔고")
@RequiredArgsConstructor
@RequestMapping("/wallets")
@RestController
public class WalletController {

    private final WalletService walletService;
    @Operation(summary = "잔고 조회")
    @GetMapping
    public BalanceInfoResponse getBalance(@RequestParam("user_id") final int userId) {
        Wallet wallet = walletService.getWallet(userId);
        return new BalanceInfoResponse(userId, wallet.getBalance());
    }

    @Operation(summary = "잔액 충전")
    @PostMapping("/{user_id}")
    public void chargeBalance(
            @PathVariable("user_id") final int userId,
            @RequestParam("amount") final int amount
    ) {
        walletService.chargePoint(userId, amount, PointActivity.USE);
    }

    @Operation(summary = "포인트 사용내역 조회")
    @GetMapping("/history")
    public List<PointHistoryResponse> getPointHistory(@RequestParam("user_id") final long userId) {
        return walletService.getHistories(userId)
                .stream().map(history -> new PointHistoryResponse(history.getAmount(), history.getActivity(), history.getCreatedAt()))
                .toList();
    }


}
