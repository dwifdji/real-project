package com._360pai.core.vo.enrolling.web;

import java.io.Serializable;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/20 15:00
 */
public class EnrollingApplyActivityListVo implements Serializable{
	
	private String sequence;//序号
	
	private String createAt;	//报名时间
	
	private String name;//报名方
	
	private String address;//报名地址
	
	private String userMobil;
	
	private String comMobile;//报名人名称

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUserMobil() {
		return userMobil;
	}

	public void setUserMobil(String userMobil) {
		this.userMobil = userMobil;
	}

	public String getComMobile() {
		return comMobile;
	}

	public void setComMobile(String comMobile) {
		this.comMobile = comMobile;
	}
}
