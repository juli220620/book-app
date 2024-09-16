package com.github.juli220620.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Around("execution(* com.github.juli220620.service.BookCrudService.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) {
        var args = Optional.ofNullable(joinPoint.getArgs()).orElse(new Object[0]);
        var methodName = joinPoint.getSignature().getName();
        log.info("Entered {} method with {} args", methodName, Arrays.toString(args));


        Object result;
        try {
           result = joinPoint.proceed();
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

        var resString = result instanceof Iterable
                ? String.format("%s [content omitted]", result.getClass())
                : result == null
                    ? "null"
                    : result.toString();

        log.info("Method {} finished execution with return value {}", methodName, resString);

        return result;
    }
}
