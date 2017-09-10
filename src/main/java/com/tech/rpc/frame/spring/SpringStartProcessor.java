package com.tech.rpc.frame.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.tech.rpc.frame.annotation.BerBean;
import com.tech.rpc.frame.annotation.BerService;
import com.tech.rpc.frame.annotation.BerServiceImpl;
import com.tech.rpc.frame.sdk.ServiceImplManager;
@BerBean
public class SpringStartProcessor implements BeanPostProcessor{

	@Override
	public Object postProcessAfterInitialization(Object obj, String beanId) throws BeansException {
		Class<?> clz=obj.getClass();
		BerServiceImpl bsi=clz.getAnnotation(BerServiceImpl.class);
		if(bsi!=null){
			List<Class<?>> faces=new ArrayList<Class<?>>();
			Class<?> parent=clz;
			while(parent!=null){
				Class<?>[] array = parent.getInterfaces();
				if(array!=null&&array.length!=0){
					for(Class<?> c:array){
						faces.add(c);
					}
				}
				parent=parent.getSuperclass();
			}
			// 如果存在拥有BerService注解的接口，则按接口名注册服务
			if (faces != null && faces.size() != 0) {
				for (Class<?> one : faces) {
					BerService bs = one.getAnnotation(BerService.class);
					if (bs != null) {
						ServiceImplManager.reg(one, obj,bsi.tag());
						return obj;//为了规范管理，一个类最多只能是一个BerService的实现
					}
				}
			}
			ServiceImplManager.reg(clz, obj,bsi.tag());
		}
		return obj;
	}

	@Override
	public Object postProcessBeforeInitialization(Object obj, String beanId) throws BeansException {
 
		return obj;
	}

}
