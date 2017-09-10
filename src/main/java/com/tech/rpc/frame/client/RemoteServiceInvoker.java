package com.tech.rpc.frame.client;

import java.io.IOException;
import java.net.UnknownHostException;

import com.tech.rpc.frame.RpcFramework;
import com.tech.rpc.frame.register.RegistryMeta;
import com.tech.rpc.frame.register.ServiceConsumer;
import com.tech.rpc.frame.sdk.balance.strategy.RandomBalanceStrategy;

public class RemoteServiceInvoker {
	private static ServiceConsumer consumer = new ServiceConsumer();
	public static <T> T getService(final Class<T> interfaceClass) throws UnknownHostException, IOException {
		RegistryMeta registryMeta = consumer.getServer(new RandomBalanceStrategy());
		String host =registryMeta.getIp();
		int port = registryMeta.getPort();
		return RpcFramework.refer(interfaceClass, host, port);
	}
}
