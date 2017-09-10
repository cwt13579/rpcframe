package com.tech.rpc.module.base;

import org.apache.commons.lang.StringUtils;

/**
 * 项目名称：manageWeb    
 * 类名称：ServiceException    
 * 类描述：    业务异常
 * 创建人：chengjx  
 * 创建时间：2014年4月16日 上午10:39:11    
 * 修改人：chengjx    
 * 修改时间：2014年4月16日 上午10:39:11    
 * 修改备注：        
 *
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 5696258901927258154L;
	private String resultCode = StringUtils.EMPTY;
	
	public ServiceException(){
		
	}
	
	public ServiceException(String msg){
		super(msg);
	}
	
	public ServiceException(String msg,String resultCode){
		super(msg);
		this.resultCode = resultCode;
	}
	
	public ServiceException(Throwable cause , String resultCode){
		super(cause);
		this.resultCode = resultCode ;
	}
	
	public ServiceException(String msg ,Throwable cause){
		super(msg ,cause);
	}
	
	public ServiceException(Throwable cause){
		super(cause);
	}
	
	public String getResultCode(){
		return resultCode;
	}
	
	public void setResultCode(String resultCode){
		this.resultCode = resultCode ;
	}
}
