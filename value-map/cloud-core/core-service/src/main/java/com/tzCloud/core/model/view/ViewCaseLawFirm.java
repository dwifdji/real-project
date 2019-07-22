
package com.tzCloud.core.model.view;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年04月28日 14时39分26秒
 */
public class ViewCaseLawFirm implements java.io.Serializable{

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
