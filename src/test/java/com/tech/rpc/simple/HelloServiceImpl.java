package com.tech.rpc.simple;

public class HelloServiceImpl implements HelloService {  
	  
    public String hello(String name) {  
    	String result = "helloworld";
    	StringBuilder builder = new StringBuilder();
    	for(int i = 0 ; i < 10000 ; i++) {
    		builder.append(result+i);
    	}
        return "Hello " + builder.toString();  
    }  
  
}  