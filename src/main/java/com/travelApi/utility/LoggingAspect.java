package com.travelApi.utility;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Autowired
    Environment env;

    @AfterThrowing(pointcut = "execution(* com.travelApi.service.*Impl.*(..))", throwing = "exception")
    public void logServiceException(Exception exception) {
        logger.error(env.getProperty(exception.getMessage()));
    }

}
