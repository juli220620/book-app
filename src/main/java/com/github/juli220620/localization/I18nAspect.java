package com.github.juli220620.localization;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Aspect
@Component
@RequiredArgsConstructor
public class I18nAspect {

    private final MessageSource messageSource;

    @Value("${spring.web.locale:en}")
    private String localeValue;

    @Around("execution(String *.*(..)) " +
            "&& @annotation(com.github.juli220620.localization.LocalizedMessage)" +
            "&& @target(org.springframework.web.bind.annotation.RestController)")
    public String getLocalizedMessage(ProceedingJoinPoint joinPoint) {
        try {
            var key = (String) joinPoint.proceed();
            return messageSource.getMessage(key, null, Locale.forLanguageTag(localeValue));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
