package com.aopnotworking.common.exception.common.alert;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MessageGenerator {

    private static final String EXCEPTION_TITLE = "[ EXCEPTION ]\n";
    private static final String EXCEPTION_TEMPLATE = "%s %s %s (line : %d)";

    public static String generate(ExceptionWrapper exceptionWrapper) {
        StringBuilder stringBuilder = new StringBuilder();
        appendExceptionDetails(exceptionWrapper, stringBuilder);
        return stringBuilder.toString();
    }

    /**
     * 기존 문자열 빌더에 예외 정보를 추가 후 반환
     *
     * @param exceptionWrapper 예외 정보 객체
     * @param stringBuilder    기존 문자열 빌더에 추가할 문자열 생성
     * @return 기존 문자열 빌더에 예외 정보를 추가 후 반환
     */
    private static StringBuilder appendExceptionDetails(final ExceptionWrapper exceptionWrapper,
                                                        final StringBuilder stringBuilder) {
        return stringBuilder.append(EXCEPTION_TITLE)
                .append(String.format(EXCEPTION_TEMPLATE, exceptionWrapper.getExceptionClassName(),
                        exceptionWrapper.getExceptionMethodName(),
                        exceptionWrapper.getMessage(),
                        exceptionWrapper.getExceptionLineNumber()));
    }
}
