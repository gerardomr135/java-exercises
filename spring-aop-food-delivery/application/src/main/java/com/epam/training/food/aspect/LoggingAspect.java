package com.epam.training.food.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    // Advice
    @Before("@annotation(EnableArgumentLogging)")
    public void logArguments(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        LOGGER.info("Method name: [{}], parameter(s): {}", methodName, Arrays.toString(joinPoint.getArgs()));
    }

    // Advice
    @AfterReturning(value = "@annotation(EnableReturnValueLogging)", returning = "returnValue")
    public void logReturnedValue(JoinPoint joinPoint, Object returnValue) {
        String methodName = joinPoint.getSignature().getName();
        LOGGER.info("Method name: [{}], return value: {}", methodName, returnValue.toString());
    }

    // Advice
    @Around("@annotation(EnableExecutionTimeLogging)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        long startTime = System.nanoTime();
        Object result = joinPoint.proceed();
        long executionTime = System.nanoTime() - startTime;
        double executionTimeMicros = executionTime / 1000.0;
        LOGGER.info("Method name: [{}], execution time = {}µs", methodName, executionTimeMicros);
        return result;
    }
}
