package com.example.performance_reservation.application.performance;


import com.example.performance_reservation.application.performance.dto.PerformanceOverview;
import com.example.performance_reservation.domain.performance.dto.PerformanceInfo;
import com.example.performance_reservation.domain.performance.Performance;
import com.example.performance_reservation.domain.performance.PerformanceDetail;
import com.example.performance_reservation.domain.performance.service.PerformanceService;
import com.example.performance_reservation.domain.reservation.Reservation;
import com.example.performance_reservation.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
public class PerformanceFacade {
    private final PerformanceService performanceService;
    private final ReservationService reservationService;

    public List<PerformanceInfo> getPerformanceInfo(
        final LocalDate startDate,
        final LocalDate endDate
    ) {
        List<PerformanceDetail> details = performanceService.getDetailsByIntervalDate(startDate, endDate);
        List<Long> performanceIds = performanceService.getIdSet(details);
        List<Performance> performancesMeta = performanceService.getMetaInfo(performanceIds);
        return PerformanceInfo.convert(performancesMeta, details);
    }

    public PerformanceOverview getPerformanceDetail(final long performanceDetailId) {
        PerformanceDetail detail = performanceService.getPerformanceDetail(performanceDetailId);
        List<Integer> availableSeats = new ArrayList<>(IntStream.rangeClosed(1, 50).boxed().toList());
        List<Integer> reservationSeats = reservationService.getReservations(performanceDetailId).stream()
                                     .map(Reservation::getSeatNo).toList();
        availableSeats.removeAll(reservationSeats);

        return PerformanceOverview.builder()
                   .performanceDetailId(detail.getId())
                   .amountSeats(detail.getAmountSeats())
                   .remainSeats(detail.getRemainSeats())
                   .availableSeats(availableSeats)
                   .startDate(detail.getStartDate()).build();

    }


}