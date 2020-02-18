package com.cg.po.exception;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class PizzaException{
	Logger logger=LoggerFactory.getLogger(PizzaException.class);
	
	@AfterThrowing(pointcut = "execution(* com.cg.po.service.PizzaService.*(..))", throwing = "ex")
	public void afterThrowingException(Exception ex) {
		logger.error(ex.getMessage());
	}
}
