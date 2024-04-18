package com.example.performance_reservation.application.reservation;

import com.example.performance_reservation.domain.performance.Performance;
import com.example.performance_reservation.domain.performance.PerformanceDetail;
import com.example.performance_reservation.domain.performance.service.PerformanceService;
import com.example.performance_reservation.domain.reservation.dto.Bill;
import com.example.performance_reservation.domain.reservation.service.ReservationService;
import com.example.performance_reservation.domain.waitingqueue.WaitingQueue;
import com.example.performance_reservation.domain.waitingqueue.domain.WaitingInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ReservationFacade {

    private final PerformanceService performanceService;
    private final ReservationService reservationService;
    private final WaitingQueue waitingQueue;

    @Transactional
    public void makeTemporaryReservation(final UUID token, final long performanceDetailId, final int seatNo) {
        // 좌석, 공연 및 상세 정보를 가져옴
        WaitingInfo waitingInfo = waitingQueue.getInfo(token);
        Performance metaInfo = performanceService.getMetaInfo(performanceDetailId);
        PerformanceDetail detail = performanceService.getReservablePerformanceDetail(performanceDetailId);
        detail.isValidSeatNo(seatNo);

        // 주문서 생성
        Bill bill = Bill.builder()
                     .title(metaInfo.getTitle())
                     .performanceDetailId(performanceDetailId)
                     .performer(metaInfo.getPerformer())
                     .price(metaInfo.getPrice())
                     .startDate(detail.getStartDate())
                     .seatNo(seatNo)
                     .userId(waitingInfo.userId())
                     .now(LocalDateTime.now())
                     .build();

        // 예약 후 잔여 좌석 수량 감소
        reservationService.reserve(bill);
        performanceService.decreaseRemainSeats(detail);

        // 인증 토큰 삭제
        waitingQueue.remove(token);
    }
}
