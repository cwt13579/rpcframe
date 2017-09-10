package com.tech.rpc.frame.protocol;

public class TransportProtocol {
	/** Magic */
    public static final short MAGIC = (short) 0xbabe;
	/** 发送的是请求信息*/
    public static final byte REQUEST_REMOTING = 1;
    
    /** 发送的是响应信息*/
    public static final byte RESPONSE_REMOTING = 2;
    
    private byte type;  
    private byte sign;  
    private long id;  
    private int bodyLength;
    
	public byte getType() {
		return type;
	}
	public void setType(byte type) {
		this.type = type;
	}
	public byte getSign() {
		return sign;
	}
	public void setSign(byte sign) {
		this.sign = sign;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getBodyLength() {
		return bodyLength;
	}
	public void setBodyLength(int bodyLength) {
		this.bodyLength = bodyLength;
	}   
}
