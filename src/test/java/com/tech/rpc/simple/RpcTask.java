package com.tech.rpc.simple;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

public class RpcTask implements Runnable{
    private Socket socket;
    private Object service;
    
	public RpcTask(Socket socket, Object service) {
		this.socket = socket;
		this.service = service;
	}
	
	
	@Override
	public void run() {
		 try {
			 ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			 try {
				 String methodName = input.readUTF();
				 Class<?> serviceClz =  (Class<?>) input.readObject();
				 Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
				 Object[] arguments = (Object[]) input.readObject();
				 ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
				 
				 try {
					 Method method = service.getClass().getMethod(methodName, parameterTypes);  
					 Object result = method.invoke(service, arguments); 
					 
					 output.writeObject(result);   
				 }  catch (Throwable t) {  
                     output.writeObject(t);  
                 } finally {  
                     output.close();  
                 }  
				
			 }finally {  
                 input.close();  
             }  
		 } catch (Exception e) {
			 e.printStackTrace();  
		 } finally {
			 try {
				socket.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			} 
		 }
	}

}
