package com.tech.rpc.frame.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 创建于:2016年7月17日<br>
 * 版权所有(C) 2016 宝润兴业<br>
 * 服务实现注解
 * @author cwt
 * @version 1.0
 * 
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BerServiceImpl {
	
	
	/**
	 * 服务标识，可以是版本，可以是实现类型
	 * @return
	 */
	String tag() default "";
	/**
	 * 接口描述
	 * @return
	 */
	String desc() default "";

}