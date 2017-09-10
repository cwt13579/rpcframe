package com.tech.rpc.module.user;

import java.util.List;

import com.tech.rpc.common.Page;
import com.tech.rpc.model.User;
import com.tech.rpc.module.base.ServiceException;


public interface UserDao {
	public List<User> findUser(Page<User> page) throws ServiceException;

	public List<User> findUser() throws ServiceException;
	
	public void save(User user) throws ServiceException;
	
	public void update(User user) throws ServiceException;
	
	public void delete(Long id) throws ServiceException;
	
}
