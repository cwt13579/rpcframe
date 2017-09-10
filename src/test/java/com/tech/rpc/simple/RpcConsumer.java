package com.tech.rpc.simple;

import com.tech.rpc.frame.RpcFramework;

public class RpcConsumer {
	 public static void main(String[] args) throws Exception { 
		    //SocketPoolFactory.getSocketPoolFactory( "127.0.0.1", 1234,3).init();
	        HelloService service = RpcFramework.refer(HelloService.class, "127.0.0.1", 1234);  
	        String hello = service.hello("World");  
	        System.out.println(hello);  
	        for (int i = 0; i < 1; i ++) {  
	          
	          
	            //Thread.sleep(200);
	        }  
	    }  
}
