package com.aopnotworking.common.exception.common.alert;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageRequest {

    private String text;

    public MessageRequest(final String text) {
        this.text = text;
    }
}
