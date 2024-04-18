# 공연 예약 서비스

## ERD
![image](https://github.com/gleys/performance_reservation_service/assets/26499296/c2a21bd2-8eff-4c19-bc69-0139dfcce20c)

## 시퀀스 다이어그램

### 결제
1. 유저가 임시 예약 목록중 하나를 결제 요청한다.
2. 시스템은 해당 예약 id 값으로 예약 정보를 조회한다.
3. 유저의 현재 지갑 잔액이 공연비 보다 크면 예약 정보를 확정으로 수정하고 저장한다.
4. 결제 성공 결과를 유저에게 반환한다.

![image](https://github.com/gleys/performance_reservation_service/assets/26499296/9c329cc3-4caf-49f2-9b27-3dc276105910)

### 좌석 조회
1. 유저가 특정 공연에 대한 좌석 정보 조회를 요청한다.
2. 대기열이 있으면 실패 반환.
3. 시스템은 해당 공연 id 값으로 잔여 좌석을 조회한다.
4. 잔여 좌석 개수에 따라 예매 불가 여부를 지정한다.
5. 잔여 좌석 정보를 유저에게 반환한다.

![image](https://github.com/gleys/performance_reservation_service/assets/26499296/dd64a856-c1af-40f0-aaf1-556916e0ca24)

### 좌석 예약
1. 유저가 좌석 예약 요청을 한다.
2. 대기열이 있으면 실패 반환 한다.
3. 해당 공연 정보 조회 한다.
4. 요청한 좌석이 예약 가능한지 확인.
5. 예약 상태를 임시로 설정해서 저장.
6. 대기열 토큰 만료
7. 예약 결과 반환.

![image](https://github.com/gleys/performance_reservation_service/assets/26499296/fbf7e400-0b37-466c-9909-d221ea695bf7)

### 잔액 조회
1. 유저가 잔액 조회 요청을 한다.
2. 잔액 조회 결과 반환 받는다.
3. 유저에게 결과를 반환한다.

![image](https://github.com/gleys/performance_reservation_service/assets/26499296/044fa309-4d7e-40ac-bdf1-0874093f956a)

### 잔액 충전
1. 유저가 잔액 충전 요청을 한다.
2. 충전 금액이 0 보다 크면 갱신한다.
3. 유저에게 충전 결과를 반환한다.

![image](https://github.com/gleys/performance_reservation_service/assets/26499296/18bf618f-7808-4a50-b106-381c712caba1)

### 토큰 발급
(인메모리로 구현. waitingqueue 패키지를 분라히야 따로 인스턴스에서 구동하는 형태로 실행하면 대기열 상태를 글로벌하게 가져갈 수 있습니다.)
1. 유저가 토큰 생성 요청을 한다.
2. 기존 토큰이 존재하면
      2-1. 기존 토큰삭제한다.
      2-2. 대기열 추가한다.
      2-3. 새 토큰 생성 후 저장한다.
3. 기존 토큰이 없으면
      3-1. 대기열 추가 한다.
      3-2. 새 토큰 생성 후 저장한다.
4. 유저에게 토큰을 반환한다.

![image](https://github.com/gleys/performance_reservation_service/assets/26499296/09487e22-ea9d-4570-ac51-ed93a0e75608)

### 토큰 조회
1. 유저가 토큰 조회 요청을 한다.
2. 토큰이 존재하면
   2-1. 기존 토큰 조회 한다.
   2-2. 대기열을 갱신한다.
   2-3. 토큰을 저장한다.
   2-4. 토큰을 유저에게 반환한다.
3. 토큰이 없으면 실패 결과를 반환한다.

![image](https://github.com/gleys/performance_reservation_service/assets/26499296/25a5d6b6-999a-41e5-a3fe-b7d850f5208f)

### 특정 공연 목록 조회
1. 유저가 특정 공연 전체 목록을 조회한다.
2. 토큰이 유효한지 확인한다.
3. 토큰이 유효하면
   3-1. 특정 공연 전체 목록을 조회한다.
   3-2. 유저에게 응답값을 반환한다.
4. 유저에게 실패 결과를 반환한다.

![image](https://github.com/gleys/performance_reservation_service/assets/26499296/c40208bf-f26e-4f82-9cae-fef9120cd0bf)

### api 명세
https://gleys.gitbook.io/reservation-service/v/api

![img_1.png](img_1.png)
