package com.tech.rpc.frame.flowcontrol;

import java.util.concurrent.atomic.AtomicLong;

import com.tech.rpc.frame.sdk.util.SystemClock;

public class FlowController {
	
	//private static final Logger logger = LoggerFactory.getLogger(FlowController.class);
	private AtomicLong[] metricses = new AtomicLong[]{new AtomicLong(0), new AtomicLong(0), new AtomicLong(0)};  
	
	 public long incrementAtCurrentMinute(){  
         
         long currentTime = SystemClock.millisClock().now();  
         int index = (int) ((currentTime / 60000) % 3);  
           
         AtomicLong atomicLong = metricses[index];  
         return atomicLong.incrementAndGet();  
     }  
	 
	 public long getLastCallCountAtLastMinute(){  
         
         long currentTime = SystemClock.millisClock().now();  
         int index = (int) (((currentTime / 60000) - 1) % 3);  
         AtomicLong atomicLong = metricses[index];  
         return atomicLong.get();  
           
     }  
	 
	 public long getCurrentCallCount(){  
         
         long currentTime = SystemClock.millisClock().now();  
         int index = (int) (((currentTime / 60000)) % 3);  
         AtomicLong atomicLong = metricses[index];  
         return atomicLong.get();  
           
     }  
	 
	 public long getNextMinuteCallCount(){  
         
         long currentTime = SystemClock.millisClock().now();  
         int index = (int) (((currentTime / 60000) + 1) % 3);  
         AtomicLong atomicLong = metricses[index];  
         return atomicLong.get();  
           
     }  
	 
	 public void clearNextMinuteCallCount(){  
         
         System.out.println("清理开始");  
         long currentTime = SystemClock.millisClock().now();  
         int index = (int) (((currentTime / 60000) + 1) % 3);  
         AtomicLong atomicLong = metricses[index];  
         atomicLong.set(0);  
     }
	 
	 public AtomicLong[] getMetricses() {  
         return metricses;  
     }  

     public void setMetricses(AtomicLong[] metricses) {  
         this.metricses = metricses;  
     }  
}
