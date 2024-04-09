package com.example.performance_reservation.domain.performance.service;

import com.example.performance_reservation.domain.performance.domain.PerformanceDetail;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PerformanceServiceTest {

    @Test
    void 공연_정보_id_값들을_중복없이_시작일_빠른_순으로_가져온다() {

        // given
        PerformanceService performanceService = new PerformanceService(new FakePerformanceRepository());
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
        LocalDateTime yesterday = today.minusDays(1);
        LocalDateTime oneWeekAgo = today.minusWeeks(1);

        PerformanceDetail detail1 = new PerformanceDetail(1, 1, 50, 20, today);
        PerformanceDetail detail2 = new PerformanceDetail(2, 1, 50, 20, oneWeekAgo);
        PerformanceDetail detail3 = new PerformanceDetail(3, 2, 50, 20, tomorrow);
        PerformanceDetail detail4 = new PerformanceDetail(4, 3, 50, 20, today);
        PerformanceDetail detail5 = new PerformanceDetail(4, 3, 50, 20, yesterday);

        // when
        List<Long> idSet = performanceService.getIdSet(List.of(detail1, detail2, detail3, detail4, detail5));

        // then
        assertThat(idSet).isEqualTo(List.of(1L, 3L, 2L));
    }

    @Test
    void 조회_시작_날짜는_반드시_종료_날짜_이전이어야_한다() {
        // given
        PerformanceService performanceService = new PerformanceService(new FakePerformanceRepository());
        LocalDateTime startDate = LocalDateTime.now().minusDays(2);
        LocalDateTime endDate = LocalDateTime.now().plusDays(3);

        // when
        List<PerformanceDetail> details = performanceService.getDetailsByIntervalDate(startDate, endDate)
                                          .stream()
                                          .sorted(Comparator.comparing(PerformanceDetail::getStartDate))
                                          .toList();

        // then
        assertThat(details.get(0).getStartDate()).isEqualTo(startDate);
        assertThat(details.get(details.size() - 1).getStartDate()).isEqualTo(endDate);
    }

    @Test
    void 당일_일정_조회가_가능하다() {
        // given
        PerformanceService performanceService = new PerformanceService(new FakePerformanceRepository());
        LocalDateTime now = LocalDateTime.now();

        // when
        List<PerformanceDetail> details = performanceService.getDetailsByIntervalDate(now, now)
                                                  .stream()
                                                  .sorted(Comparator.comparing(PerformanceDetail::getStartDate))
                                                  .toList();

        // then
        assertThat(details.get(0).getStartDate()).isEqualTo(now);
        assertThat(details.get(details.size() - 1).getStartDate()).isEqualTo(now);
    }


    @Test
    void 조회_시작_날짜가_종료_날짜_이후면_예외를_던진다() {
        // given
        PerformanceService performanceService = new PerformanceService(new FakePerformanceRepository());
        LocalDateTime startDate = LocalDateTime.now().plusDays(2);
        LocalDateTime endDate = LocalDateTime.now();

        // when & then
        assertThatThrownBy(() -> performanceService.getDetailsByIntervalDate(startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("조회 시작 날짜는 종료 날짜 이전 이어야 입니다.");
    }



}