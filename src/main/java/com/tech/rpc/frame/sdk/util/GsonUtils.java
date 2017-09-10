package com.tech.rpc.frame.sdk.util;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.tech.rpc.module.base.gloable.GlobalVar;

/**
 * 
 * JSON转换器
 * @author LiuSQ
 *
 */
public class GsonUtils {
	
	
	private static Gson gson = null;
	
	/**
	 * 静态代码块(初始化GSON)
	 */
	static{
		
		gson = new Gson();
		
	}
	
	/**
	 * 获取WEB请求JSON串(填充Bean对象)
	 * @param <T>
	 * @param json
	 * @param classOfT
	 * @return <T> T
	 */
	public static <T> T getJson(String json,Class<T> classOfT){
		return  gson.fromJson(json, classOfT);
	}
	
	/**
	 * 将对象转换为JSON字符串
	 * @param obj
	 * @return String
	 */
	public static String toJson(Object obj){
		return gson.toJson(obj);
	}
	
	
	/**
	 * 返回格式 : {"code":"0","data":"",desc:"exec success"}
	 * @return
	 */
	public static String retJson(){
		return GsonUtils.retJson(GlobalVar.CODE_RET_SUCCESS, "","success");
	}
	
	
	/**
	 * 返回格式 : {"code":"0","data":{}/[{},{}...],desc:"exec success"}
	 * @return
	 */
	public static String retJson(Object data){
		return GsonUtils.retJson(GlobalVar.CODE_RET_SUCCESS, data,"success");
	}
	
	/**
	 * 返回格式 : {"code":"11","data":"",desc:"exec success"}
	 * @return
	 */
	public static String retJson(String errcode,String errdesc){
		return GsonUtils.retJson(errcode,"",errdesc);
	}
	
	/**
	 * 用户自定义Json
	 * @param code
	 * @param data
	 * @param others
	 * @param totals
	 * @param errcode
	 * @param desc
	 * @return
	 */
	public  static String retJson(String code,Object data,String desc){
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		String retJson = null;
		
		map.put(GlobalVar.CODE,code);
		
		if(data != null && !"".equals(data.toString().trim())){
			map.put(GlobalVar.DATA,GsonUtils.toJson(data));
		}else{
			map.put(GlobalVar.DATA,"");
		}
		if(desc != null && !"".equals(desc)){
			map.put(GlobalVar.DESC, desc);
		}else{
			map.put(GlobalVar.DESC, "");
		}

		retJson = GsonUtils.toJson(map);
		map.clear();map = null;
		return retJson;
		
	}
	
	
}
