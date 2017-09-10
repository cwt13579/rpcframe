package com.tech.rpc.model;

import com.tech.rpc.model.base.BaseModel;

public class User extends BaseModel{

	private static final long serialVersionUID = 1L;
	
	private Long   id;
	private String name;
	private String mobile;
	private String passwd;
	private int  status;
	private String email;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", mobile=" + mobile
				+ ", passwd=" + passwd + ", status=" + status + ", email="
				+ email + "]";
	}
}
