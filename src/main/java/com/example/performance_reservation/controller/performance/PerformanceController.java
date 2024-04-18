package com.example.performance_reservation.controller.performance;

import com.example.performance_reservation.application.performance.PerformanceFacade;
import com.example.performance_reservation.application.performance.dto.PerformanceOverview;
import com.example.performance_reservation.controller.performance.response.PerformanceInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Tag(name = "공연")
@RequiredArgsConstructor
@RequestMapping("/performances")
@RestController
public class PerformanceController {
    private final PerformanceFacade performanceFacade;

    @Operation(summary = "기간별 공연 목록 조회")
    @GetMapping
    public List<PerformanceInfoResponse> getPerformanceInfo(
            @RequestHeader("token") final UUID token,
            @RequestParam("start_date") final LocalDate startDate,
            @RequestParam("end_date") final LocalDate endDate
    ) {

        return performanceFacade.getPerformanceInfo(startDate, endDate).stream()
                .map(info -> PerformanceInfoResponse.builder()
                                .performanceId(info.performanceId())
                                     .detailId(info.detailId())
                                     .performanceName(info.title())
                                     .performer(info.performer())
                                     .startDate(LocalDate.from(info.startDate()))
                                     .amountSeats(info.amountSeats())
                                     .remainSeats(info.remainSeats())
                                     .build()).toList();
    }

    @Operation(summary = "개별 공연 상세 조회")
    @GetMapping("/{performance_id}")
    public PerformanceOverview getPerformanceDetailInfo(
            @RequestHeader("token") final UUID token,
            @RequestParam("detail_id") final long performanceDetailId) {
        return performanceFacade.getPerformanceDetail(performanceDetailId);
    }
}
