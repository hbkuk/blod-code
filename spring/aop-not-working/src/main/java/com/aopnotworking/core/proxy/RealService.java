package com.aopnotworking.core.proxy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RealService {

    public void perform() {
        log.info("Performing real service...");
    }
}
