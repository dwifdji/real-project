package com._360pai.core.vo.enrolling.web;

import java.io.Serializable;

import com._360pai.core.common.constants.EnrollingEnum;

public class EnrollingStatusVO implements Serializable{
	
	private String id;
	
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
		this.name = EnrollingEnum.STATUS.getDesc(id);
	}

	public EnrollingStatusVO(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
