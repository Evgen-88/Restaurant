package com.itrex.kaliaha.advise;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class SuccessfulExecutionSelectMethodRepositoryAdvice {
    private static final String METHOD_RESULT = "\n\nREPOSITORY METHOD: %s%s was successfully completed\nresult:%s\n";

    @AfterReturning(pointcut = "execution(* com.itrex.kaliaha.repository.*.find*(..)) " +
                    "|| execution(* com.itrex.kaliaha.repository.*.get*(..))", returning = "entity"
    )
    public void methodLoggingFindResult(JoinPoint joinPoint, Object entity) {
        String methodName = joinPoint.getSignature().getName();
        String arg = Arrays.toString(joinPoint.getArgs());
        log.info(String.format(METHOD_RESULT, methodName, arg, entity));
    }
}