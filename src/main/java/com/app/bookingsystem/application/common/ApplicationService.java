package com.app.bookingsystem.application.common;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Retention(RUNTIME)
@Target(TYPE)
@Documented
@Service
@Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
public @interface ApplicationService
{
}
