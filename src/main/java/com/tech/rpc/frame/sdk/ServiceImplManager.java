package com.tech.rpc.frame.sdk;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class ServiceImplManager {
	private static Map<String,Object> services=new HashMap<String, Object>();
	private static Map<String,List<Method>> methods=new HashMap<String, List<Method>>();
	private static Map<String,String> special=new HashMap<String, String>();
	private static Logger logger=Logger.getLogger(ServiceImplManager.class);
	
	public ServiceImplManager(){
		init();
	}
	
	/**
	 * 初始化基础类型参数的兼容关系
	 */
	private void init(){
		String[] base=new String[]{short.class.getName(),int.class.getName(),long.class.getName(),float.class.getName(),double.class.getName()};
		String[] obj=new String[]{Short.class.getName(),Integer.class.getName(),Long.class.getName(),Float.class.getName(),Double.class.getName()};
		for(int i=0;i<base.length;i++){
			special.put(base[i]+"_"+obj[i], "y");
			special.put(obj[i]+"_"+base[i], "y");
		}
		for(int i=0;i<base.length;i++){
			for(int j=i+1;j<base.length;j++){
				special.put(base[j]+"_"+base[i], "y");
			}
		}
	}
	
	/**
	 * 注册服务实现
	 * @param serviceClz 服务类(可以是服务的类，也可以是服务的接口的类)
	 * @param service 服务
	 * @param serviceTag 服务标签，可以是版本号，可以是不同的实现的标识
	 */
	public static final void reg(Class<?> serviceClz,Object service,Object serviceTag){
		String serviceName=DistributeUtil.buildServiceName(serviceClz);
		String sName=serviceName+".";
		Method[] ms=serviceClz.getDeclaredMethods();
		for (Method m : ms) {
			if(methods.containsKey(sName+m.getName())){
				methods.get(sName+m.getName()).add(m);
			}else{
				List<Method> list=new ArrayList<Method>();
				list.add(m);
				methods.put(sName+m.getName(), list);
			}
		}
		services.put(serviceName, service);
		logger.info("服务实现注册成功:"+serviceName);
	}
	
	/**
	 * 获取分布式服务实现
	 * @param clz 服务类
	 * @return 服务，不存在时返回null
	 */
	public static final List<Method> aquire(String serviceName,String methodName){
		return methods.get(serviceName+"."+methodName);
	}
	
	/**
	 * 获取分布式服务实现
	 * @param clz 服务类
	 * @return 服务，不存在时返回null
	 */
	public static final Method aquire(String serviceName,String methodName,Object[] params){
		List<Method> mts=methods.get(serviceName+"."+methodName);
		if(mts==null||mts.size()==0){
			return null;
		}
		if(mts.size()==1){
			return mts.get(0);
		}
		Class<?>[] pClz=null;
		if(params!=null&&params.length!=0){
			pClz=new Class<?>[params.length];
			for(int i=0;i<params.length;i++){
				pClz[i]=params[i]!=null?params[i].getClass():null;
			}
		}
		try {
			for (Method m : mts) {
				Class<?>[] mClz=m.getParameterTypes();
				if(mClz.length==pClz.length){
					int i=0;
					for(i=0;i<mClz.length;i++){
						if(pClz[i]==null||pClz[i]==mClz[i]||mClz[i].isAssignableFrom(pClz.getClass())||judgeSpecial(mClz[i],pClz[i])){
							continue;
						}else{
							break;
						}
					}
					if(i==mClz.length){
						return m;
					}
				}
			}
		} catch (Exception e){
			return null;
		}
		return null;
	}
	
	/**
	 * 获取分布式服务实现
	 * @param clz 服务类
	 * @return 服务，不存在时返回null
	 */
	@SuppressWarnings({ "unchecked"})
	public static final <T> T aquire(String serviceName){
		return (T)services.get(serviceName);
	}
	
	@SuppressWarnings("unchecked")
	public static final <T> T aquire(Class<T> clz) {
		return (T)services.get(clz.getName());
	}
	private static boolean judgeSpecial(Class<?> mClz,Class<?> pClz){
		return special.containsKey(mClz.getName()+"_"+pClz.getName());
	}
	
}
