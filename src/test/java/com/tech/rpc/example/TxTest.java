package com.tech.rpc.example;

import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.tech.rpc.model.User;
import com.tech.rpc.module.base.BeanContext;
import com.tech.rpc.module.user.UserService;

public class TxTest {

	public static void main(String args[]) throws Exception {
		FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("classpath:application.xml"); 
		BeanContext.setContext(context);
		User user = new User();
		user.setName("hahafgh");
		user.setMobile("18709090909");
		user.setEmail("1121354961@qq.com");
		user.setPasswd("111112");
		user.setStatus(1);
		
		UserService userService = context.getBean("userService", UserService.class);
		userService.save(user);
	}
}
