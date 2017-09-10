package com.tech.rpc.frame.sdk.balance.strategy;

import java.util.List;

import com.tech.rpc.frame.register.RegistryMeta;

public interface BalanceStrategy {

	RegistryMeta getServer(List<RegistryMeta> list);
}
