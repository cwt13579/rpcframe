package com.tech.rpc.frame.register;

public class RegistryMeta {

	private String ip;//ip地址
	private int    port;//端口
	private int    weight;//权重
	
	public RegistryMeta(String ip,int port,int weight) {
		this.ip = ip;
		this.port = port;
		this.weight = weight;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	@Override
	public String toString() {
		return "RegistryMeta [ip=" + ip + ", port=" + port + ", weight="
				+ weight + "]";
	}
	
	
}
