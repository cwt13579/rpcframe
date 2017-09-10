package com.tech.rpc.zookeeper;

import java.util.List;

import com.tech.rpc.frame.RpcFramework;
import com.tech.rpc.frame.register.RegistryMeta;
import com.tech.rpc.frame.register.ServiceConsumer;
import com.tech.rpc.frame.sdk.balance.strategy.RandomBalanceStrategy;
import com.tech.rpc.model.Goods;
import com.tech.rpc.zookeeper.service.base.BaseGoodsService;
import com.tech.rpc.zookeeper.service.base.BaseHelloService;

public class Client {

	public static void main(String args[]) throws Exception {
		    // 连接注册中心  获取服务地址
		    ServiceConsumer consumer = new ServiceConsumer();

		    RegistryMeta registryMeta = consumer.getServer(new RandomBalanceStrategy());
		    
		    String host =registryMeta.getIp();
		    int port = registryMeta.getPort();
		    
		    System.out.println("-host:-"+registryMeta.getIp());
		    System.out.println("-port:-"+registryMeta.getPort());
		    System.out.println("-weight:-"+registryMeta.getWeight());
		    
		    BaseHelloService service = RpcFramework.refer(BaseHelloService.class, host, port);  
		    BaseGoodsService goodsService = RpcFramework.refer(BaseGoodsService.class, host, port);  
		    
		    String hello = service.hello("World");
            System.out.println(hello);
            
            List<Goods> list = goodsService.getGoodsList();
            
            for(Goods goods : list) {
            	System.out.println(goods.getGoodsId()+","+goods.getGoodsName() + "," + goods.getGoodsPrice());
            }
            
            BaseHelloService service2 = consumer.lookup(BaseHelloService.class);
            for(int i = 0 ; i < 50; i++) {
            	String hello2 = service2.hello("World");
            	
            	   System.out.println("-------------------");
                   System.out.println(i+"-------------------"+hello2);
                   System.out.println("-------------------");
            }
         
	    }  
}
