package com.example.performance_reservation.application.performance;


import com.example.performance_reservation.domain.performance.dto.PerformanceInfo;
import com.example.performance_reservation.domain.performance.Performance;
import com.example.performance_reservation.domain.performance.PerformanceDetail;
import com.example.performance_reservation.domain.performance.service.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PerformanceFacade {
    /*
     * facade 라는 네이밍이 어울리는지 의문.
     * 얘는 단순히 performance controller에 필요한 값들만 performanceService 의 메소드들을 조합해서 뿌려주는데
     * facade 의 정의에 맞지 않는것 같음.(여러 도메인 비즈니스를 가져와서 쓰는게 아니기 때문)
     * 그럼 어떠한 네이밍을 가져야하나?
     */
    private final PerformanceService performanceService;

    public List<PerformanceInfo> getPerformanceInfo(
        final LocalDateTime startDate,
        final LocalDateTime endDate
    ) {
        List<PerformanceDetail> details = performanceService.getDetailsByIntervalDate(startDate, endDate);
        List<Long> performanceIds = performanceService.getIdSet(details);
        List<Performance> performancesMeta = performanceService.getMeta(performanceIds);
        return PerformanceInfo.convert(performancesMeta, details);
    }

    public PerformanceDetail getPerformanceDetail(final int performanceId) {
        return performanceService.getPerformanceDetail(performanceId);
    }



}