package com.tech.rpc.module.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech.rpc.common.Page;
import com.tech.rpc.model.User;
//@Service
//@BerServiceImpl
@Service
public class UserService{
	
	@Autowired
	private UserDao userDao;
	

	public List<User> findUserPage(Page<User> page) {
		List<User> userList = userDao.findUser(page);
		return userList;
	}
	

	public List<User> findUser() {
		List<User> userList = userDao.findUser();
		return userList;
	}


	public void save(User user) throws Exception {
		for(int i = 0; i < 5;i++) {
			user.setName(user.getName()+i);
			userDao.save(user);
			if(i == 3) {
				throw new Exception("测试事务");
			}
		}
		
		
	}


	public void update(User user) {
		userDao.update(user);
	}


	public void delete(Long id) {
		userDao.delete(id);
	}
}
