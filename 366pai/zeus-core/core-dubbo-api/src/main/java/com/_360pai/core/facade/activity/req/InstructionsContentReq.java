
package com._360pai.core.facade.activity.req;

import com._360pai.arch.common.RequestModel;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月10日 18时47分27秒
 */
@Getter
@Setter
public class InstructionsContentReq extends RequestModel {

	/**
	 * 
	 */
	private Integer id;
	/**
	 *      拍卖活动ID
	 */
	private Integer activityId;
	/**
	 *		处置服务ID
	 */
	private Integer disposalId;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String content;
	/**
	 * 
	 */
	private String status;



	private String assetId;

	private String agencyCode;

	private String agencyId;

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	/**
	 * 
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 * 
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	 * 
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * 
	 */
	public String getContent(){
		return content;
	}
	
	/**
	 * 
	 */
	public void setContent(String content){
		this.content = content;
	}
	
	/**
	 * 
	 */
	public String getStatus(){
		return status;
	}
	
	/**
	 * 
	 */
	public void setStatus(String status){
		this.status = status;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
}
