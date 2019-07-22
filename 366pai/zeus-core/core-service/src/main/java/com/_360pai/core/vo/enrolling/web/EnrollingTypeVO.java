package com._360pai.core.vo.enrolling.web;

import java.io.Serializable;
import java.util.List;

public class EnrollingTypeVO implements Serializable{
	
	private String typeId; //预招商类型id
	
	private String typeName;//预招商名称
	
	private List<HomeEnrollingActivityVO> eaList;//类型下的列表

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public List<HomeEnrollingActivityVO> getEaList() {
		return eaList;
	}

	public void setEaList(List<HomeEnrollingActivityVO> eaList) {
		this.eaList = eaList;
	}
	
}
