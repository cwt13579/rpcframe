package com.tech.rpc.frame.sdk.balance.strategy;

import java.util.List;

import com.tech.rpc.frame.register.RegistryMeta;

public class RoundRobinStrategy implements BalanceStrategy{

	private static Integer pos = 0;
	@Override
	public RegistryMeta getServer(List<RegistryMeta> list) {
		RegistryMeta server = null;
        synchronized (pos){
		   if (pos > list.size()) {
		                pos = 0;
		            server = list.get(pos);
		            pos ++;
		   }   
	    }
	    return server;
	}
}
