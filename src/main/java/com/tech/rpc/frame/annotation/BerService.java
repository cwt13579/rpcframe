package com.tech.rpc.frame.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 创建于:2016年7月17日<br>
 * 版权所有(C) 2016 宝润兴业<br>
 * 服务接口注解
 * @author cwt
 * @version 1.0
 * 
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BerService {
	/**
	 * 接口描述
	 * @return
	 */
	String desc() default "";
	
	/**
	 * 系统标识
	 * @return
	 */
	String systemTag() default "";
	
	boolean isFlowControl() default true;		    //是否单位时间限流
	
	long maxCallCountInMinute() default 100000;		//单位时间的最大调用量
}