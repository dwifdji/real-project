
package com.tzCloud.core.condition.view;

import com.tzCloud.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年04月28日 14时39分26秒
 */
public class ViewCaseLawFirmCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private String docId;
	/**
	 * 
	 */
	private String lawFirm;
	/**
	 * 
	 */
	private String courtName;
	
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
	public String getDocId(){
		return docId;
	}
	
	/**
	 * 
	 */
	public void setDocId(String docId){
		this.docId = docId;
	}
	
	/**
	 * 
	 */
	public String getLawFirm(){
		return lawFirm;
	}
	
	/**
	 * 
	 */
	public void setLawFirm(String lawFirm){
		this.lawFirm = lawFirm;
	}
	
	/**
	 * 
	 */
	public String getCourtName(){
		return courtName;
	}
	
	/**
	 * 
	 */
	public void setCourtName(String courtName){
		this.courtName = courtName;
	}

}