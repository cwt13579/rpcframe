package com.tech.rpc.example;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.tech.rpc.frame.RpcFramework;
import com.tech.rpc.frame.annotation.BerServiceImpl;
import com.tech.rpc.frame.annotation.RequestMapping;
import com.tech.rpc.frame.flowcontrol.FlowControllerManager;
import com.tech.rpc.frame.sdk.ClassHelper;
import com.tech.rpc.frame.sdk.ServiceImplManager;
import com.tech.rpc.model.User;
import com.tech.rpc.module.base.BeanContext;
import com.tech.rpc.module.base.facade.BaseUserFacade;
import com.tech.rpc.module.base.facade.BaseUserService;
import com.tech.rpc.module.user.UserService;

public class AppServer {

	public static void main(String args[]) throws IOException {
		//初始化
		FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("classpath:application.xml"); 
		BeanContext.setContext(context);
		
		//BaseUserService userService = ServiceImplManager.aquire("com.tech.rpc.module.base.facade.BaseUserService");
		//List<User> userList  = ServiceImplManager.aquire(BaseUserFacade.class).findUser();
		//UserService userService = context.getBean("userService",UserService.class);
		//List<User> userList = userService.findUser();

//		System.out.println(userList.size());
//		for(User user : userList) {
//			System.out.println(user);
//		}
		
//		UserFacade userFacade = new UserFacade();
//		userFacade.findUser();
//		UserService userService = new UserService();
//		userService.findUser();
		
		
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
