
package com._360pai.core.condition.fdd;

import com._360pai.arch.core.abs.DaoCondition;

import java.util.Date;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月15日 16时37分03秒
 */
public class GatewayFddGenerateContractCondition implements DaoCondition{


	private java.lang.Integer id;
	/**
	 * 平台用户id
	 */
	private java.lang.Integer partyId;
	/**
	 * 合同标题
	 */
	private java.lang.String docTitle;
	/**
	 * 合同的模板id
	 */
	private java.lang.String templateId;
	/**
	 * 签章的合同id
	 */
	private java.lang.String contractId;
	/**
	 * 合同下载地址
	 */
	private java.lang.String downloadUrl;
	/**
	 * 合同的下载地址
	 */
	private java.lang.String viewpdfUrl;
	/**
	 * 法大大的开户id
	 */
	private java.lang.String fddId;
	/**
	 * 开户状态
	 */
	private java.lang.String status;
	/**
	 * 处理结果
	 */
	private java.lang.String msg;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;


	private java.lang.String type;


	private java.lang.String activityId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPartyId() {
		return partyId;
	}

	public void setPartyId(Integer partyId) {
		this.partyId = partyId;
	}

	public String getDocTitle() {
		return docTitle;
	}

	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getViewpdfUrl() {
		return viewpdfUrl;
	}

	public void setViewpdfUrl(String viewpdfUrl) {
		this.viewpdfUrl = viewpdfUrl;
	}

	public String getFddId() {
		return fddId;
	}

	public void setFddId(String fddId) {
		this.fddId = fddId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
}