package com.example.performance_reservation.infrastructure.wallet;

import com.example.performance_reservation.domain.wallet.PointHistory;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface JpaPointHistoryRepository extends Repository<PointHistory, Long> {
    List<PointHistory> findByUserId(final long userId);
    PointHistory save(PointHistory entity);
}
