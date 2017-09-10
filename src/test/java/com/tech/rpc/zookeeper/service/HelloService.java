package com.tech.rpc.zookeeper.service;

import com.tech.rpc.frame.annotation.BerServiceImpl;
import com.tech.rpc.frame.annotation.RequestMapping;
import com.tech.rpc.zookeeper.service.base.BaseHelloService;

@BerServiceImpl
public class HelloService implements BaseHelloService {  
	
	  @RequestMapping(isFlowControl=true,maxCallCountInMinute=40)
	  public String hello(String name) {  
	        return "Hello " + name;  
	  } 

}  
