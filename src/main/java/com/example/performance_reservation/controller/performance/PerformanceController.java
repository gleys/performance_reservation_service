package com.example.performance_reservation.controller.performance;

import com.example.performance_reservation.controller.performance.response.PerformanceDetailResponse;
import com.example.performance_reservation.controller.performance.response.PerformanceInfoResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/performances")
@RestController
public class PerformanceController {

    @GetMapping
    public List<PerformanceInfoResponse> getPerformanceInfo(
        @RequestParam("start_date") final String startDate,
        @RequestParam("end_date") final String endDate
    ) {
        LocalDate date = LocalDate.parse(startDate).plusDays(1);

        return List.of(
            new PerformanceInfoResponse(1, "테스트1", date, 50),
            new PerformanceInfoResponse(2, "테스트2", date, 35),
            new PerformanceInfoResponse(3, "테스트3", date, 40)
        );
    }

    @GetMapping("/{performance_id}")
    public PerformanceDetailResponse getPerformanceDetailInfo(@PathVariable("performance_id") final int performanceId) {

        return new PerformanceDetailResponse(
                performanceId,
                "테스트",
                List.of(1, 3, 5, 6, 7, 8)
        );
    }
}
