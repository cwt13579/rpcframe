package com.tech.rpc.zookeeper;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import com.tech.rpc.frame.RpcFramework;
import com.tech.rpc.frame.annotation.BerServiceImpl;
import com.tech.rpc.frame.annotation.RequestMapping;
import com.tech.rpc.frame.flowcontrol.FlowControllerManager;
import com.tech.rpc.frame.sdk.ClassHelper;

public class Server {
	
	public static void main(String args[]) throws IOException {
		  //启动服务端 并注册到  注册中心
		
		  //编织服务
		  List<Class<?>> ServiceImplList = ClassHelper.getClassListByAnnotation(BerServiceImpl.class);
		 
		  for(Class<?> clz : ServiceImplList) {
			  //获取方法
			  Method[] methods = clz.getDeclaredMethods();
			  for(Method method : methods) {
				  RequestMapping request = method.getAnnotation(RequestMapping.class);
				  if(request != null) {
					  FlowControllerManager.setServiceLimitVal(clz.getName()+"@"+method.getName(), request.maxCallCountInMinute());
				  }
			  }
		  }
		  //暴露服务
		  RpcFramework.export(12345);
	}
}
