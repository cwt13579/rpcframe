package com.tech.rpc.zookeeper;

public class FlowControlTest {
	
	public static void main(String args[]) {
		long number1 = ((System.currentTimeMillis() / 60000)-1) % 3;
		long number0 = (System.currentTimeMillis() / 60000) % 3;
		long number2 = ((System.currentTimeMillis() / 60000)+1) % 3;
 
		System.out.println(number0);
		System.out.println(number1);
		System.out.println(number2);
		
		Class studentClassRef = String.class;
		Class<?> strClz = String.class;
	}
}
