package com.tech.rpc.frame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.List;

import com.tech.rpc.frame.flowcontrol.FlowControllerManager;
import com.tech.rpc.frame.sdk.ClassHelper;
import com.tech.rpc.frame.sdk.ServiceImplManager;

public class RpcTask implements Runnable{
    private Socket socket;
    
	public RpcTask(Socket socket) {
		this.socket = socket;
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
					 // 获取 Method(Service)
					 //Method method = service.getClass().getMethod(methodName, parameterTypes);
					 //获取子类
					 List<Class<?>> childClz = ClassHelper.getClassListBySuper(serviceClz);
					 if(!childClz.isEmpty()) {
						 System.out.println(childClz.get(0));
					 }
					 //Method method = (Method) ServiceManager.aquire(childClz.get(0), methodName, parameterTypes);
					 Method method=ServiceImplManager.aquire(serviceClz.getName(), methodName,parameterTypes);
					 // 调用之前判断限流（分布式限流）
					 if(FlowControllerManager.isAllow(childClz.get(0).getName()+"@"+method.getName())) {
						 //统计限流信息
						 // FlowControllerManager.incrementCallCount(childClz.get(0).getName()+"@"+method.getName());
                         //Object result = method.invoke(childClz.get(0).newInstance(), arguments);
						 Object obj=ServiceImplManager.aquire(serviceClz.getName());
						 Object result = method.invoke(obj, arguments);
						 output.writeObject(result); 
					 } else {
						 output.writeObject(null);
					 }
					 
					
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
