package com.github.juli220620.aspect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class CachingAspect {
    private final Map<String, Map<Integer, CacheDataHolder>> cache = new HashMap<>();

    @Value("${app.cache.max-cache-size}")
    private Integer cacheLimit = 100;

    @Around("execution(* com.github.juli220620..*.*(..)) && args(*,..) && @annotation(DoCashing)")
    public Object cacheResult(ProceedingJoinPoint joinPoint) {
        var methodCache = getCacheForMethod(joinPoint);
        var cacheKey = getKey(joinPoint.getArgs());

        var argsCache = methodCache.get(cacheKey);

        return argsCache == null
                ? processCacheMiss(methodCache, cacheKey, joinPoint)
                : processCacheHit(argsCache);
    }

    private Object processCacheHit(CacheDataHolder value) {
        value.setLatestInvocationTime(System.currentTimeMillis());
        return value.getMethodReturnValue();
    }

    private Object processCacheMiss(Map<Integer, CacheDataHolder> methodCache,
                                    Integer key,
                                    ProceedingJoinPoint joinPoint) {
        Object result;
        try {
            result = joinPoint.proceed(joinPoint.getArgs());
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        if (methodCache.size() >= cacheLimit) evictOldData(methodCache);
        methodCache.put(key, new CacheDataHolder(result, System.currentTimeMillis()));

        return result;
    }

    //Evicting old cache data to prevent memory leaks
    private void evictOldData(Map<Integer, CacheDataHolder> methodCache) {
        var key = methodCache.entrySet().stream()
                .min(Comparator.comparingLong(e -> e.getValue().getLatestInvocationTime()))
                .orElseThrow()
                .getKey();
        methodCache.remove(key);
    }

    private Map<Integer, CacheDataHolder> getCacheForMethod(ProceedingJoinPoint joinPoint) {
        var classPart = joinPoint.getSignature().getDeclaringType().getCanonicalName();
        var methodPart = joinPoint.getSignature().getName();
        var key = String.format("%s|%s", classPart, methodPart);
        return cache.computeIfAbsent(key, k -> new HashMap<>());
    }

    private Integer getKey(Object[] args) {
        return Arrays.stream(args)
                .map(Object::hashCode)
                .map(Object::toString)
                .reduce("", (s, s2) -> s + s2)
                .hashCode();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private static class CacheDataHolder {

        private Object methodReturnValue;
        private Long latestInvocationTime;
    }
}
