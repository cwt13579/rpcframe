package com.tech.rpc.zookeeper.service.base;

import com.tech.rpc.frame.annotation.BerService;

@BerService
public interface BaseHelloService {

	String hello(String name);
}
