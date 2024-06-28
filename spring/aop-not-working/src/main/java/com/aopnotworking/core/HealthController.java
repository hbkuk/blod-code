package com.aopnotworking.core;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/api/health")
    String health() {
        throw new RuntimeException("서버 오류가 발생했습니다.");
    }

    @PostMapping("/api/health/{id}")
    String health(@PathVariable String id) {
        return id;
    }

}
