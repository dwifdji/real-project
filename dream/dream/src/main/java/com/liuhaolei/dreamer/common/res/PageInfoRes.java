package com.liuhaolei.dreamer.common.res;

import java.util.List;

import com.github.pagehelper.PageInfo;

public class PageInfoRes{
	
	private Long total;
	
	private Boolean haseNextPage;
	
	private List searchList;

	public PageInfoRes(PageInfo pageInfo) {
		super();
		this.total = pageInfo.getTotal();
		this.haseNextPage = pageInfo.isHasNextPage();
		this.searchList = pageInfo.getList();
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Boolean getHaseNextPage() {
		return haseNextPage;
	}

	public void setHaseNextPage(Boolean haseNextPage) {
		this.haseNextPage = haseNextPage;
	}

	public List  getSearchList() {
		return searchList;
	}

	public void setSearchList(List searchList) {
		this.searchList = searchList;
	}
	
	
	
}
