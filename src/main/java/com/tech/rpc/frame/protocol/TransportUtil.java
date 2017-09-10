package com.tech.rpc.frame.protocol;

import  com.tech.rpc.frame.serialize.SerializerHolder;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TransportUtil {

	public static void sendMsg(ObjectOutputStream out , TransportMsg msg) throws IOException {
		 byte[] body = SerializerHolder.serializerImpl().writeObject(msg.getBody());  
		
		 out.writeShort(TransportProtocol.MAGIC);             //协议头  
		 out.writeByte(msg.getTransporterType());             // 传输类型 sign 是请求还是响应  
		 out.writeByte(msg.getCode());                        // 请求类型requestcode 表明主题信息的类型，也代表请求的类型    
		 out.writeInt(body.length);                           //length  
		 out.write(body); 
		 out.flush();
	}
	
	public static TransportMsg receiveMsg(ObjectInputStream in) throws IOException {
	   short magic = in.readShort();
	   byte type = in.readByte();
	   byte code = in.readByte();
	   int bodyLength = in.readInt();
	   System.out.println(magic);
	   byte[] bytes = new byte[bodyLength]; 
	   in.read(bytes);
	   
	   return TransportMsg.newInstance(type, code, bytes);
	}
}
