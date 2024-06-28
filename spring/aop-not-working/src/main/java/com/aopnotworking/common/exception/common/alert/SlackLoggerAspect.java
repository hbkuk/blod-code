package com.aopnotworking.common.exception.common.alert;

import static com.aopnotworking.common.exception.common.alert.ExceptionWrapper.extractExceptionWrapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * {@code @SlackLogger} 어노테이션이 적용된 메서드에 대한 Aspect
 * <p>
 * 예외와 실패한 현재 사용자의 정보와 알림 이벤트를 Slack으로 로그 전송
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class SlackLoggerAspect {

    private final SlackAlertSender alertSender;

    @Before("@annotation(com.aopnotworking.common.exception.common.alert.SlackLogger)")
    public void sendLogForError(final JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        ExceptionWrapper exceptionWrapper = extractExceptionWrapper((Exception) args[0]);
        alertSender.send(MessageGenerator.generate(exceptionWrapper));
    }
}
