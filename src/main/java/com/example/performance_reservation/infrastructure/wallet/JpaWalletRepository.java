package com.example.performance_reservation.infrastructure.wallet;


import com.example.performance_reservation.domain.wallet.Wallet;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface JpaWalletRepository extends Repository<Wallet, Long> {

    Wallet save(final Wallet entity);
    Optional<Wallet> findByUserId(final long userId);
}
