package com.tech.rpc.module.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tech.rpc.common.Page;
import com.tech.rpc.frame.annotation.BerServiceImpl;
import com.tech.rpc.model.User;
import com.tech.rpc.module.base.facade.BaseUserFacade;

@BerServiceImpl
public class UserFacade implements BaseUserFacade{

	@Autowired
	private UserService userService;
	
	public List<User> findUser() {
		List<User> userList = userService.findUser();
		return userList;
	}
	
	public void save(User user) throws Exception {
		userService.save(user);
	}

	@Override
	public List<User> findUserPage(Page<User> page) {
		List<User> userList = userService.findUserPage(page);
		return userList;
	}

	@Override
	public void update(User user) {
		userService.update(user);
	}

	@Override
	public void delete(Long id) {
		userService.delete(id);
	}
}
