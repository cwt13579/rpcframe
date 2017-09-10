package com.tech.rpc.frame.sdk;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.tech.rpc.frame.annotation.BerServiceImpl;

public class ServiceManager {

	private static Map<String,Object> services=new HashMap<String, Object>();
	private static byte[] lock=new byte[1];
	/**
	 * 注册服务
	 * @param serviceClz 服务类(可以是服务的类，也可以是服务的接口的类)
	 * @param service 服务
	 */
	private static final void reg(Class<?> serviceClz,Object method,String methodName){
		services.put(DistributeUtil.buildServiceName(serviceClz, methodName), method);
	}
	
	/**
	 * 获取分布式服务
	 * @param clz 服务类
	 * @param tag 服务扩展标识
	 * @return 服务，不存在时返回null
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	@SuppressWarnings({ "unchecked", "hiding" })
	public static final <T> T aquire(Class<T> clz,String methodName, Class<?>[] parameterTypes) throws NoSuchMethodException, SecurityException{
		Object obj=services.get(DistributeUtil.buildServiceName(clz, methodName));
		if(obj!=null){
			return (T)obj;
		}
		synchronized (lock) {
			obj=services.get(DistributeUtil.buildServiceName(clz, methodName));
			if(obj==null){
				BerServiceImpl bs = clz.getAnnotation(BerServiceImpl.class);
				if (bs != null) {//&&clz.isInterface()
					//获取Method(service)
					Method method = clz.getMethod(methodName, parameterTypes);
					ServiceManager.reg(clz,method,methodName);
					obj = services.get(DistributeUtil.buildServiceName(clz, methodName));
				}
			}
		}
		return (T)obj;
	}
}
