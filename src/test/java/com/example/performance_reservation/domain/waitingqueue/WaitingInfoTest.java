package com.example.performance_reservation.domain.waitingqueue;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class WaitingInfoTest {
    @Test
    void 대기열_정보_초기화_시_totalNum_값은_0_이다() {
        // given & when
        LocalDateTime expiredAt = LocalDateTime.now().plusMinutes(5);
        WaitingInfo waitingInfo = WaitingInfo.init(1, 100, expiredAt);

        // then
        assertThat(waitingInfo.waitingNum()).isEqualTo(100);
        assertThat(waitingInfo.totalNum()).isEqualTo(0);
        assertThat(waitingInfo.expiredAt()).isEqualTo(expiredAt);

    }

    @Test
    void 유효시간이_만료되면_false를_반환한다() {
        // given
        LocalDateTime expiredAt = LocalDateTime.now().minusMinutes(10);
        WaitingInfo waitingInfo = WaitingInfo.init(1, 10, expiredAt);
        
        // when
        boolean isExpire = waitingInfo.isExpire();

        // then
        assertThat(isExpire).isEqualTo(true);
    }

    @Test
    void 대기정보를_갱신_한다() {
        // given
        WaitingInfo waitingInfo = WaitingInfo.init(1, 100, LocalDateTime.now().plusMinutes(5));

        // when
        waitingInfo = waitingInfo.update(100, 1000);

        // then
        assertThat(waitingInfo.waitingNum()).isEqualTo(100);
        assertThat(waitingInfo.totalNum()).isEqualTo(1000);
    }
        

}