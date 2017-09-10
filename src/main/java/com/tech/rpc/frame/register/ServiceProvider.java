package com.tech.rpc.frame.register;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import com.google.gson.Gson;
import com.tech.rpc.common.Constant;

public class ServiceProvider {
	
	private static final Logger LOGGER = Logger.getLogger(ServiceProvider.class);
	private CountDownLatch latch = new CountDownLatch(1);
	
	public void publish(String host, int port) {
		String url = publishService(host, port); // 发布 RMI 服务并返回 RMI 地址
        if (url != null) {
            ZooKeeper zk = connectServer(); // 连接 ZooKeeper 服务器并获取 ZooKeeper 对象
            if (zk != null) {
                createNode(zk, url); // 创建 ZNode 并将 RMI 地址放入 ZNode 上
            }
        }
	}
	
	// 发布 RMI 服务
    private String publishService(String host, int port) {
        String url = null;
        try {
        	RegistryMeta regitstryMeta = new RegistryMeta(host, port,1);
        	Gson gons = new Gson();
        	url =  gons.toJson(regitstryMeta);
            LOGGER.debug("publish rmi service (url: {"+url+"})");
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return url;
    }
	
	 // 连接 ZooKeeper 服务器
    private ZooKeeper connectServer() {
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(Constant.ZK_CONNECTION_STRING, Constant.ZK_SESSION_TIMEOUT, new Watcher() {
				@Override
		 		public void process(WatchedEvent event) {
					 if (event.getState() == Event.KeeperState.SyncConnected) {
	                        latch.countDown(); // 唤醒当前正在执行的线程
	                 }
				}});
            latch.await(); // 使当前线程处于等待状态
        } catch (IOException | InterruptedException e) {
            LOGGER.error("", e);
        }
        return zk;
    }
    
	
	 // 创建 ZNode
    private void createNode(ZooKeeper zk, String url) {
        try {
            byte[] data = url.getBytes();
            String path = zk.create(Constant.ZK_PROVIDER_PATH, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL); // 创建一个临时性且有序的 ZNode
            LOGGER.debug("create zookeeper node ({"+path+"} => {"+url+"})");
        } catch (KeeperException | InterruptedException e) {
            LOGGER.error("", e);
        }
    }
}
