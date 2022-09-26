package com.travelApi.utility;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    @Autowired
    Environment env;

    @AfterThrowing(pointcut = "execution(* com.travelApi.service.*Impl.*(..))", throwing = "exception")
    public void logServiceException(Exception exception) {
        log.error(env.getProperty(exception.getMessage()));
    }

}
