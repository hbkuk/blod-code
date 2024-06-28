package com.aopnotworking.common.exception.common.alert;

import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExceptionWrapper {

    private static final int DEFAULT_LINE_NUMBER = -1;
    private static final String UNKNOWN_CLASS = "UnknownClass";
    private static final String UNKNOWN_METHOD = "UnknownMethod";

    private final String exceptionClassName;
    private final String exceptionMethodName;
    private final int exceptionLineNumber;
    private final String message;

    /**
     * 예외 객체에서 예외 정보를 추출 후 래핑된 예외 객체를 생성 후 반환
     * @param calledException 추출할 예외 객체
     * @return 예외 객체에서 예외 정보를 추출 후 래핑된 예외 객체를 생성 후 반환
     */
    public static ExceptionWrapper extractExceptionWrapper(final Exception calledException) {
        StackTraceElement[] exceptionStackTrace = calledException.getStackTrace();

        Optional<StackTraceElement> firstStackTraceElement = Optional.ofNullable(
                exceptionStackTrace)
            .filter(stackTrace -> stackTrace.length > 0)
            .map(stackTrace -> stackTrace[0]);

        String exceptionClassName = firstStackTraceElement.map(StackTraceElement::getClassName)
            .orElse(UNKNOWN_CLASS);
        String exceptionMethodName = firstStackTraceElement.map(StackTraceElement::getMethodName)
            .orElse(UNKNOWN_METHOD);
        int exceptionLineNumber = firstStackTraceElement.map(StackTraceElement::getLineNumber)
            .orElse(DEFAULT_LINE_NUMBER);
        String message = calledException.getMessage();

        return new ExceptionWrapper(exceptionClassName, exceptionMethodName, exceptionLineNumber,
            message);
    }
}
