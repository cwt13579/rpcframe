package com.tech.rpc.simple;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import com.tech.rpc.frame.RpcInvocationHandler;

public class RpcFramework {

	public static void exportService(final List<Object> services , int port) {
		
	}
	public static void export(final Object service , int port) throws IOException {
		if(service == null) {
			throw new IllegalArgumentException("service == null");
		}
		
		if(port < 0 || port > 65535) {
			throw new IllegalArgumentException("invalid port");
		}
		
		System.out.println("export service " + service.getClass().getName() + " on port " + port);
		
		ServerSocket serverSocket = new ServerSocket(port);
		
		while(true) {
			try {
				final Socket socket = serverSocket.accept();
				new Thread(new RpcTask(socket,service)).start();;
			} catch (Exception e) {
				
			} finally {
				
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T refer(final Class<T> interfaceClass,final String host ,final int port) throws UnknownHostException, IOException {
		if (interfaceClass == null)  
            throw new IllegalArgumentException("Interface class == null");  
        if (! interfaceClass.isInterface())  
            throw new IllegalArgumentException("The " + interfaceClass.getName() + " must be interface class!");  
        if (host == null || host.length() == 0)  
            throw new IllegalArgumentException("Host == null!");  
        if (port <= 0 || port > 65535)  
            throw new IllegalArgumentException("Invalid port " + port);  
        System.out.println("Get remote service " + interfaceClass.getName() + " from server " + host + ":" + port); 

        
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[] {interfaceClass}, new  RpcInvocationHandler(interfaceClass,host,port));  
    }  
}
