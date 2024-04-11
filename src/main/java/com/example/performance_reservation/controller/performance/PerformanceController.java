package com.example.performance_reservation.controller.performance;

import com.example.performance_reservation.controller.performance.response.PerformanceDetailResponse;
import com.example.performance_reservation.controller.performance.response.PerformanceInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Tag(name = "공연")
@RequestMapping("/performances")
@RestController
public class PerformanceController {

    @Operation(summary = "기간별 공연 목록 조회")
    @GetMapping
    public List<PerformanceInfoResponse> getPerformanceInfo(
            @RequestHeader("token") final UUID token,
            @RequestParam("start_date") final String startDate,
            @RequestParam("end_date") final String endDate
    ) {
        LocalDate date = LocalDate.parse(startDate).plusDays(1);

        return List.of(
            new PerformanceInfoResponse(1, 2, "테스트1", date, 50),
            new PerformanceInfoResponse(1, 3,"테스트1", date.plusDays(1), 35),
            new PerformanceInfoResponse(3, 4, "테스트3", date, 40)
        );
    }

    @Operation(summary = "개별 공연 상세 조회")
    @GetMapping("/{performance_id}")
    public PerformanceDetailResponse getPerformanceDetailInfo(@PathVariable("performance_id") final int performanceId) {

        return new PerformanceDetailResponse(
                performanceId,
                "테스트",
                List.of(1, 3, 5, 6, 7, 8)
        );
    }
}
