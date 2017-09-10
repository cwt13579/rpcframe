package com.tech.rpc.frame.sdk;


/**
 * 创建于:2016年7月29日<br>
 * 版权所有(C) 2016 宝润兴业<br>
 * TODO
 * @author cwt
 * @version 1.0
 * 
 */
public class DistributeUtil {

	private DistributeUtil(){
		
	}
	/**
	 * 根据服务类和服务标识生成服务名称
	 * @param clz 服务类
	 * @param method 方法名
	 * @return
	 */
	public static final String buildServiceName(Class<?> clz,String methodName){
		return clz.getName()+"@"+methodName;
	}
	
	public static final String buildServiceName(Class<?> clz){
		return clz.getName();
	}
}
