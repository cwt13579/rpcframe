package com.tech.rpc.frame;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;

public class RpcInvocationHandler implements InvocationHandler{
	private Class<?> clz;
    private String host;
    private int port;
    
    public RpcInvocationHandler(Class<?> interfaceClass,String host, int port) {
    	 this.clz = interfaceClass;
    	 this.host = host;
    	 this.port = port;
    }

	@Override
	public Object invoke(Object proxy, Method method, Object[] arguments)throws Throwable {
		   //此处可以改进实现连接�?
		   Socket socket = new Socket(host,port);
		   //Socket socket = SocketPool.getSocket();
		   //Socket socket = ConnectionPoolUtil.getConnection();
           try {  
        	   
               ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());  
               try {  
                   output.writeUTF(method.getName());  
                   output.writeObject(clz);
                   output.writeObject(method.getParameterTypes());  
                   output.writeObject(arguments);  
                   
                   ObjectInputStream input = new ObjectInputStream(socket.getInputStream());  
                   try {  
                       Object result = input.readObject();  
                       if (result instanceof Throwable) {  
                           throw (Throwable) result;  
                       }  
                       return result;  
                   } finally {  
                      input.close();  
                   }  
               } finally {  
                   output.close();  
               }  
           } finally {  
               socket.close();
           } 
	}

}
