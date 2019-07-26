package com.liuhaolei.dreamer.vo;

import java.util.List;

import com.liuhaolei.dreamer.common.ReponseEnum;

public class CategoryVO {
	
	private String title;
	
	private String type;
	
	private List<OrderVO> typeList;
	
	
	public List<OrderVO> getTypeList() {
		return typeList;
	}


	public void setTypeList(List<OrderVO> typeList) {
		this.typeList = typeList;
	}


	public String getTitle() {
		return title;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
		this.title = ReponseEnum.OrderCategoryEnum.getTitleByType(type);
	}
	
}
