package com.tech.rpc.module.base;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;


/**
 * spring容器的上下文
 * @author cwt
 * @date 2014-4-9
 */
public class BeanContext implements ApplicationContextAware{
	private static final Logger log=Logger.getLogger(BeanContext.class);
	private static ApplicationContext applicationContext;
	private static String config = "ApplicationContext.xml";

	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public  void  setApplicationContext(ApplicationContext context)
	{
		applicationContext = context;
	}
	
	public static void setContext(ApplicationContext context){
		applicationContext = context;
	}
	
	public static Object getBean(String beanId){
		if(applicationContext == null){
			log.info("getBean from ClassPathXmlApplicationContext");
			applicationContext = new ClassPathXmlApplicationContext(config);
		}
		
		return applicationContext.getBean(beanId);
		
	}
	
	public static  <T> T getBean(String beanId,Class<T> clazz){
		if(applicationContext == null){
			log.info("getBean from ClassPathXmlApplicationContext");
			applicationContext = new ClassPathXmlApplicationContext(config);
		}
		
		return applicationContext.getBean(beanId, clazz);
	}
	
	public static Resource getResource(String name){
		if(applicationContext == null){
			log.info("getBean from ClassPathXmlApplicationContext");
			applicationContext = new ClassPathXmlApplicationContext(config);
		}
		
		return applicationContext.getResource(name);
	}
}
