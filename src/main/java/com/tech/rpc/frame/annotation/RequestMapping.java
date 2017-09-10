package com.tech.rpc.frame.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequestMapping {
	boolean isFlowControl() default true;		    //是否单位时间限流
	long maxCallCountInMinute() default 100000;		//单位时间的最大调用量
}
