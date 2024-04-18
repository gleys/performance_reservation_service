package com.example.performance_reservation.infrastructure.performance;

import com.example.performance_reservation.domain.performance.PerformanceDetail;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface JpaPerformanceDetailRepository extends Repository<PerformanceDetail, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select pd from PerformanceDetail pd where pd.id = ?1")
    Optional<PerformanceDetail> findByIdWithPessimisticLock(final long id);
    Optional<PerformanceDetail> findById(final long id);
    List<PerformanceDetail> findByStartDateBetween(final LocalDate startDate, final LocalDate endDate);
    PerformanceDetail save(PerformanceDetail entity);
}
