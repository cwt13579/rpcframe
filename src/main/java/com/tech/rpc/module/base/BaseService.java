package com.tech.rpc.module.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tech.rpc.frame.sdk.util.GsonUtils;
import com.tech.rpc.module.base.gloable.GlobalVar;



public class BaseService {

	
	/**
	 * 返回格式 : {"code":"0","data":"",desc:"exec success"}
	 * @return
	 */
	public String retJson(){
		return this.retJson(GlobalVar.CODE_RET_SUCCESS, "","success");
	}
	
	
	/**
	 * 返回格式 : {"code":"0","data":{}/[{},{}...],desc:"exec success"}
	 * @return
	 */
	public String retJson(Object data){
		return this.retJson(GlobalVar.CODE_RET_SUCCESS, data,"success");
	}
	
	/**
	 * 返回格式 : {"code":"11","data":"",desc:"exec success"}
	 * @return
	 */
	public String retJson(String errcode,String errdesc){
		return this.retJson(errcode,"",errdesc);
	}
	
	/**
	 * 返回格式 : {"code":"0","data":{"list":{}/[{},{}...],page:{nowpage:1,totals:100}},desc:"exec success"}
	 * @return
	 */
	public String retJson(List<?> list,Object page){
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put(GlobalVar.LIST, list);
	    map.put(GlobalVar.PAGE,page);
	    return this.retJson(map);
	}

	/**
	 * 返回格式 : {"code":"0","data":{}/[{},{}...],desc:"exec success"}
	 * @return
	 */
	public String retPageJson(Object data){
		
		  Gson  gson = new GsonBuilder()  
		  	 //不导出实体中没有用@Expose注解的属性
		     .excludeFieldsWithoutExposeAnnotation()
		      //支持Map的key为复杂对象的形式
		     .enableComplexMapKeySerialization()  
		     //时间转化为特定格式
		     .serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss:SSS")
		     //对json结果格式化.
		     .setPrettyPrinting().create();   
		  
		return this.retJson(GlobalVar.CODE_RET_SUCCESS, gson.toJson(data),"success");
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
	public String retJson(String code,Object data,String desc){
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		String retJson = null;
		
		map.put(GlobalVar.CODE,code);
		
		if(data != null && !"".equals(data.toString().trim())){
			//modify by mahd 2014-04-17 start (处理gson特殊字符)
			Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
			map.put(GlobalVar.DATA,gson.toJson(data));
			//map.put(GlobalVar.DATA,GsonUtils.toJson(data));
			//modify by mahd 2014-04-17 end
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
