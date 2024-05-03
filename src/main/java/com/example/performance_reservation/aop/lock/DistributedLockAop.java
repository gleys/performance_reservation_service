package com.example.performance_reservation.application.aop.lock;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class DistributedLockAop {
    private static final String LOCK_PREFIX = "LOCK:";
    private final RedissonClient client;
    private final AopForTransaction transaction;

    @Around("@annotation(com.example")

}
