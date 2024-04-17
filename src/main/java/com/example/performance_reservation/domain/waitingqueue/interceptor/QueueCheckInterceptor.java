package com.example.performance_reservation.infrastructure.waitingqueue;

import com.example.performance_reservation.domain.waitingqueue.WaitingInfo;
import com.example.performance_reservation.domain.waitingqueue.WaitingQueue;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class QueueCheckInterceptor implements HandlerInterceptor {
    private final WaitingQueue waitingQueue;
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        String path = request.getServletPath();
        if (isVisitor(path)) return checkToken(request);
        return true;
    }

    public boolean checkToken(final HttpServletRequest request) {
        UUID token = UUID.fromString(request.getHeader("token"));
        WaitingInfo waitingInfo = waitingQueue.getInfo(token);
        return !waitingInfo.isAvailable();
    }

    private boolean isVisitor(String path) {
        return path.equals("reserve");
    }

}
