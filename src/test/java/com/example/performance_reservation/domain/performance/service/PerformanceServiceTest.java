package com.example.performance_reservation.domain.performance.service;

import com.example.performance_reservation.domain.performance.PerformanceDetail;
import com.example.performance_reservation.domain.performance.exception.InvalidDateConditionException;
import com.example.performance_reservation.domain.performance.exception.errorcode.PerformanceErrorCode;
import com.example.performance_reservation.infrastructure.performance.FakePerformanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PerformanceServiceTest {
    private PerformanceService performanceService;

    @BeforeEach
    void init() {
        this.performanceService = new PerformanceService(new FakePerformanceRepository());
    }

    @Test
    void 공연_정보_id_값들을_중복없이_시작일_빠른_순으로_가져온다() {

        // given
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDate yesterday = today.minusDays(1);
        LocalDate oneWeekAgo = today.minusWeeks(1);

        PerformanceDetail detail1 = new PerformanceDetail(1, 1, 50, 20, today);
        PerformanceDetail detail2 = new PerformanceDetail(2, 1, 50, 20, oneWeekAgo);
        PerformanceDetail detail3 = new PerformanceDetail(3, 2, 50, 20, tomorrow);
        PerformanceDetail detail4 = new PerformanceDetail(4, 3, 50, 20, today);
        PerformanceDetail detail5 = new PerformanceDetail(4, 3, 50, 20, yesterday);

        // when
        List<Long> idSet = this.performanceService.getIdSet(List.of(detail1, detail2, detail3, detail4, detail5));

        // then
        assertThat(idSet).isEqualTo(List.of(1L, 3L, 2L));
    }

    @Test
    void 조회_시작_날짜는_반드시_종료_날짜_이전이어야_한다() {
        // given
        LocalDate startDate = LocalDate.now().minusDays(2);
        LocalDate endDate = LocalDate.now().plusDays(3);

        // when
        List<PerformanceDetail> details = this.performanceService.getDetailsByIntervalDate(startDate, endDate)
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
        LocalDate now = LocalDate.now();

        // when
        List<PerformanceDetail> details = this.performanceService.getDetailsByIntervalDate(now, now)
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
        LocalDate startDate = LocalDate.now().plusDays(2);
        LocalDate endDate = LocalDate.now();

        // when & then
        assertThatThrownBy(() -> this.performanceService.getDetailsByIntervalDate(startDate, endDate))
                .isInstanceOf(InvalidDateConditionException.class)
                .hasMessage(PerformanceErrorCode.INVALID_DATE_CONDITION.getMessage());
    }



}