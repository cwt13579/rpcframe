package com.tech.rpc.example;

import com.tech.rpc.frame.client.RemoteServiceInvoker;
import com.tech.rpc.model.User;
import com.tech.rpc.module.base.facade.BaseUserFacade;
import com.tech.rpc.module.base.facade.BaseUserService;

public class AppClient {
	
	public static void main(String args[]) throws Exception {
//		Page<User> page = new Page<User>();
//		page.setCurrentPage(1);
//		page.setPageSize(10);
//		List<User> userList = RemoteServiceInvoker.getService(BaseUserService.class).findUserPage(page);
//		page.setResultList(userList);
//		
//	    for(User user : userList) {
//	    	System.out.println(user);
//	    }
//	    RemoteServiceInvoker.getService(BaseUserService.class).delete(userList.get(0).getId());
	    User user = new User();
	    user.setName("helloword1");
	    user.setMobile("18709090909");
	    user.setEmail("1121354961@qq.com");
	    user.setPasswd("111112");
	    user.setStatus(1);
	    RemoteServiceInvoker.getService(BaseUserFacade.class).save(user);
//	    user = userList.get(0);
//	    user.setMobile("18888888899");
//	    user.setEmail("1121@sina.com");
//	    RemoteServiceInvoker.getService(BaseUserService.class).update(user);
	}
}
