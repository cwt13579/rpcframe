package com.tech.rpc.module.base.facade;

import java.util.List;

import com.tech.rpc.common.Page;
import com.tech.rpc.frame.annotation.BerService;
import com.tech.rpc.model.User;
@BerService
public interface BaseUserFacade {
    public List<User> findUserPage(Page<User> page);
	
	public List<User> findUser() ;
	
	public void save(User user) throws Exception;
	
	public void update(User user);
	
	public void delete(Long id);
}
