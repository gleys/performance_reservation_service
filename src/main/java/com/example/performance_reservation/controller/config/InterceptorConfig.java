package com.example.performance_reservation.controller.config;

import com.example.performance_reservation.domain.waitingqueue.WaitingQueue;
import com.example.performance_reservation.domain.waitingqueue.interceptor.QueueCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    WaitingQueue waitingQueue;
    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new QueueCheckInterceptor(waitingQueue));
    }
}
