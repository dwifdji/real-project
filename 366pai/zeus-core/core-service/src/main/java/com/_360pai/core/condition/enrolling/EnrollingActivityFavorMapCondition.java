
package com._360pai.core.condition.enrolling;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月10日 17时37分21秒
 */
public class EnrollingActivityFavorMapCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer partyId;
	/**
	 * 
	 */
	private Integer enrollingActivityId;
	
	/**
	 * 
	 */
	public Integer getPartyId(){
		return partyId;
	}
	
	/**
	 * 
	 */
	public void setPartyId(Integer partyId){
		this.partyId = partyId;
	}
	
	/**
	 * 
	 */
	public Integer getEnrollingActivityId(){
		return enrollingActivityId;
	}
	
	/**
	 * 
	 */
	public void setEnrollingActivityId(Integer enrollingActivityId){
		this.enrollingActivityId = enrollingActivityId;
	}

}