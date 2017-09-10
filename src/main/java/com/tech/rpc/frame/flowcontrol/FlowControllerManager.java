package com.tech.rpc.frame.flowcontrol;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tech.rpc.frame.sdk.util.Pair;

public class FlowControllerManager {
	
	 private static final Logger logger = LoggerFactory.getLogger(FlowControllerManager.class); 
	
	 private static final ConcurrentMap<String, Pair<Long,FlowController>> globalFlowControllerMap = new ConcurrentHashMap<String, Pair<Long,FlowController>>(); 
	 
	 private static final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("provider-timer")); 
		
	 static {
			scheduledExecutorService.scheduleAtFixedRate(new Runnable() {  
	            @Override  
	            public void run() {  
	                try {  
	                    System.out.println("清理下一个时间的槽位");  
	                    clearAllServiceNextMinuteCallCount();
	                } catch (Exception e) {  
	                    logger.warn("schedule publish failed [{}]", e.getMessage());
	                }  
	            }  
	              
	        }, 3, 45, TimeUnit.SECONDS);  
			
			//启动统计
			new Thread(new Runnable(){
				@Override
				public void run() {
		             for (;;) {  
		                 logger.info("统计中");  
		                 try {  
		                     Thread.sleep(5000);
		                     for(String str : globalFlowControllerMap.keySet()){  
		                         FlowController flowController = globalFlowControllerMap.get(str).getValue();  
		                         logger.info("上一秒调用的次数是[{}]",flowController.getLastCallCountAtLastMinute());  
		                         logger.info("当前秒调用的次数是[{}]",flowController.getCurrentCallCount());  
		                         logger.info("下以秒调用的次数是[{}]",flowController.getNextMinuteCallCount());  
		                     } 
		                    //异步发送统计信息 
		                 } catch (InterruptedException e) {  
		                     e.printStackTrace();  
		                 }  
		             } 
				}
				
			}).start();
		}
	 
	  /** 
	     * 设置某个服务的单位时间的最大调用次数 
	     * @param serviceName 
	     * @param maxCallCount 
	 * @return 
	     */  
	  public static void setServiceLimitVal(String serviceName,Long maxCallCount){  
	          
	        Pair<Long,FlowController> pair = new Pair<Long, FlowController>();  
	        pair.setKey(maxCallCount);  
	        pair.setValue(new FlowController());  
	        globalFlowControllerMap.put(serviceName, pair);  
	          
	  } 
	  
	   
	  
	  /** 
	     * 原子增加某个服务的调用次数 
	     * @param serviceName 
	     */  
	    public static void incrementCallCount(String serviceName){  
	          
	        Pair<Long,FlowController> pair = globalFlowControllerMap.get(serviceName);  
	          
	        if(null == pair){  
	            logger.warn("serviceName [{}] matched no flowController",serviceName);  
	            return;  
	        }  
	          
	        FlowController flowController = pair.getValue();  
	        flowController.incrementAtCurrentMinute();  
	          
	    }  
	    
	    /** 
	     * 查看某个服务是否可用 
	     * @param serviceName 
	     * @return 
	     */  
	    public static boolean isAllow(String serviceName){  
	          
	        Pair<Long,FlowController> pair = globalFlowControllerMap.get(serviceName);  
	          
	        if(null == pair){  
	            logger.warn("serviceName [{}] matched no flowController",serviceName);  
	            return true;  
	        }  
	          
	        FlowController flowController = pair.getValue();  
	        Long maxCallCount = pair.getKey();  
	        long hasCallCount = flowController.incrementAtCurrentMinute();  
	        
	        return hasCallCount > maxCallCount ? false :true;
	    } 
	    
	    /** 
	     * 获取到某个服务的上一分钟的调用次数 
	     * @param serviceName 
	     * @return 
	     */  
	    public static Long getLastMinuteCallCount(String serviceName){  
	        Pair<Long,FlowController> pair = globalFlowControllerMap.get(serviceName);  
	          
	        if(null == pair){  
	            logger.warn("serviceName [{}] matched no flowController",serviceName);  
	            return 0l;  
	        }  
	        FlowController flowController = pair.getValue();  
	        return flowController.getLastCallCountAtLastMinute();  
	    }  
	      
	    /** 
	     * 将下一秒的调用次数置为0 
	     */  
	    public static void clearAllServiceNextMinuteCallCount(){  
	          
	        for(String service : globalFlowControllerMap.keySet()){  
	              
	            Pair<Long,FlowController> pair = globalFlowControllerMap.get(service);  
	            if(null == pair){  
	                logger.warn("serviceName [{}] matched no flowController",service);  
	                continue;  
	            }  
	            FlowController flowController = pair.getValue();  
	            flowController.clearNextMinuteCallCount();  
	        }  
	    }  
}
