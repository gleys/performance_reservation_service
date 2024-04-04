package com.example.performance_reservation.controller.wallet;


import com.example.performance_reservation.controller.wallet.request.ChargeBalanceRequest;
import com.example.performance_reservation.controller.wallet.response.BalanceInfoResponse;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/wallets")
@RestController
public class WalletController {

    @GetMapping
    public BalanceInfoResponse getBalance(@RequestParam("user_id") final int userId) {
        return new BalanceInfoResponse(userId, 1000);
    }

    @PostMapping
    public void chargeBalance(
            @RequestParam("user_id") final int user_id,
            @RequestParam("amount") final int amount
    ) {
    }
}
