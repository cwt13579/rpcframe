package com.tech.rpc.frame.protocol;



public class TransportMsg extends ByteHolder{

	private byte code;  
	private byte transporterType;  
	private transient CommonMsgBody body;
    
	public byte getCode() {
		return code;
	}

	public void setCode(byte code) {
		this.code = code;
	}

	public byte getTransporterType() {
		return transporterType;
	}

	public void setTransporterType(byte transporterType) {
		this.transporterType = transporterType;
	}

	public CommonMsgBody getBody() {
		return body;
	}

	public void setBody(CommonMsgBody body) {
		this.body = body;
	}

	public static TransportMsg createRequestTransporter(byte code,CommonMsgBody body){  
		TransportMsg remotingTransporter = new TransportMsg();  
	    remotingTransporter.transporterType = TransportProtocol.REQUEST_REMOTING;  
	    remotingTransporter.setCode(code);  
	    remotingTransporter.body = body;  
	    return remotingTransporter;  
	} 
	
	  /** 
     * 创建一个响应对象 
     * @param code 响应对象的类型  
     * @param commonCustomHeader 响应对象的正文 
     * @param opaque 此响应对象对应的请求对象的id 
     * @return 
     */  
    public static TransportMsg createResponseTransporter(byte code,CommonMsgBody body,long opaque){  
    	TransportMsg remotingTransporter = new TransportMsg();  
        remotingTransporter.transporterType = TransportProtocol.RESPONSE_REMOTING;  
        remotingTransporter.setCode(code);  
        remotingTransporter.body = body;  
        return remotingTransporter;  
    }  
    
    public static TransportMsg newInstance(byte type, byte code, byte[] bytes) {
    	TransportMsg remotingTransporter = new TransportMsg();
		remotingTransporter.setTransporterType(type);
		remotingTransporter.setCode(code);
		remotingTransporter.bytes(bytes);
		return remotingTransporter;
	}

	@Override
	public String toString() {
		return "TransportMsg [code=" + code + ", transporterType="
				+ transporterType + ", body=" + body + "]";
	}
}
