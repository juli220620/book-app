package com.github.juli220620.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Slf4j
@Component
public class CashingAspect {

    private final Map<Integer, Object> cash = new HashMap<>();

    @Around("execution(* com.github.juli220620.*.*.*(..)) && args(*,..) && @annotation(DoCashing)")
    public Object cashResult(ProceedingJoinPoint joinPoint) {
        var key = getKey(joinPoint.getArgs());

        var value = cash.get(key);

        if (value == null) {
            try {
                value = joinPoint.proceed(joinPoint.getArgs());
            } catch (Throwable e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }


        cash.put(key, value);

        return value;
    }

    private Integer getKey(Object[] args) {
        return Arrays.stream(args)
                .map(Object::hashCode)
                .map(Object::toString)
                .reduce("", (s, s2) -> s+s2)
                .hashCode();
    }
}
