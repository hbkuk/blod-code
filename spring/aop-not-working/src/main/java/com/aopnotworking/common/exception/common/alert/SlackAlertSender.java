package com.aopnotworking.common.exception.common.alert;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SlackAlertSender {

    public void send(String message) {
        log.error("Slack Alert! => {}", message);

    }
}
