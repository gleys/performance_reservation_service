package com.example.performance_reservation.domain.waitingqueue.interceptor;

import com.example.performance_reservation.domain.waitingqueue.domain.WaitingInfo;
import com.example.performance_reservation.domain.waitingqueue.WaitingQueue;
import com.example.performance_reservation.domain.waitingqueue.exception.InvalidTokenException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.WebRequestHandlerInterceptorAdapter;

import java.util.UUID;
import java.util.regex.Pattern;


@RequiredArgsConstructor
public class QueueCheckInterceptor implements HandlerInterceptor {

    private final WaitingQueue waitingQueue;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        String path = request.getServletPath();
        if (isAuthorizationPath(path)) checkToken(request);
        return true;
    }

    public void checkToken(final HttpServletRequest request) {
        UUID token = UUID.fromString(request.getHeader("token"));
        WaitingInfo waitingInfo = waitingQueue.getInfo(token);
        if (!waitingInfo.isAvailable()) {
            throw InvalidTokenException.Exception;
        }
    }

    private boolean isAuthorizationPath(String path) {
        return path.equals("/reservations/{seat_id}")
               || Pattern.matches("/performances.*", path);
    }

}
