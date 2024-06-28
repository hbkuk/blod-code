package com.aopnotworking.common.exception.common.exception;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class ErrorResponse {

    private int errorCode;
    private String message;
}
