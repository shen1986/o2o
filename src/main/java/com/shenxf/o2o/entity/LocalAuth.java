package com.shenxf.o2o.entity;

import java.util.Date;

public class LocalAuth {
	private Long localAuth;
	private String username;
	private String password;
	private Date createTime;
	private Date lastEditTIme;
	private PersonInfo personInfo;
	public Long getLocalAuth() {
		return localAuth;
	}
	public void setLocalAuth(Long localAuth) {
		this.localAuth = localAuth;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastEditTIme() {
		return lastEditTIme;
	}
	public void setLastEditTIme(Date lastEditTIme) {
		this.lastEditTIme = lastEditTIme;
	}
	public PersonInfo getPersonInfo() {
		return personInfo;
	}
	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}
}
