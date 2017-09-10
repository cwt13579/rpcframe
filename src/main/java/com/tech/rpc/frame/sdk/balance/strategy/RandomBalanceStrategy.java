package com.tech.rpc.frame.sdk.balance.strategy;

import java.util.List;

import com.tech.rpc.frame.register.RegistryMeta;

public class RandomBalanceStrategy implements BalanceStrategy{

	 @Override
	 public RegistryMeta getServer(List<RegistryMeta> list) {
 
	         java.util.Random random = new java.util.Random();
	         int randomPos = random.nextInt(list.size());
	         
	         return list.get(randomPos);
	  }
}
