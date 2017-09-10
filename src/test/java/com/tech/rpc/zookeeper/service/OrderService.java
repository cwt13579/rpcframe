package com.tech.rpc.zookeeper.service;

import java.util.List;

import com.tech.rpc.frame.annotation.BerService;
import com.tech.rpc.model.Order;
@BerService
public interface OrderService {

	List<Order> getOrderList(); 
	void save(Order order);
	void update(Order order);
	
}
