package com.aopnotworking.core.proxy;

import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

public class CGLIBProxyTest {

    /**
     * CGLIB (Code Generation Library)
     * : 동적으로 프록시 객체를 생성하기 위한 라이브러리
     * <p>
     * Enhancer
     * : CGLIB의 주요 클래스 중 하나, 프록시 객체를 생성하기 위해 사용
     */
    @Test
    void CGLIB_프록시_테스트() {
        RealService 실제_서비스 = new RealService();

        // Enhancer를 사용하여 프록시 객체 생성
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(RealService.class);
        enhancer.setCallback(new LoggerInterceptor(실제_서비스));

        // 프록시 객체 생성 및 메소드 호출
        RealService 프록시_서비스 = (RealService) enhancer.create();
        프록시_서비스.perform();
    }

}
