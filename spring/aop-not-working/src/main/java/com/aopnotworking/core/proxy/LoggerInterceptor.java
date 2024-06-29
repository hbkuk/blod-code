package com.aopnotworking.core.proxy;

import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

@Slf4j
public class LoggerInterceptor implements MethodInterceptor {

    private final Object target;

    public LoggerInterceptor(Object target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy)
        throws Throwable {
        log.info("실제 Method 호출 전 logging");
        Object result = methodProxy.invoke(target, objects);
        log.info("실제 Method 호출 후 logging");

        return result;
    }
}
