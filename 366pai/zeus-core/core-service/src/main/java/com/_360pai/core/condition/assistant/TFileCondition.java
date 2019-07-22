
package com._360pai.core.condition.assistant;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年11月21日 10时50分24秒
 */
public class TFileCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private String fileUrl;
	/**
	 * 
	 */
	private String fileUrlWatermark;
	/**
	 * 
	 */
	private String etag;
	/**
	 * 
	 */
	private String etagWatermark;
	
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
	public String getFileUrl(){
		return fileUrl;
	}
	
	/**
	 * 
	 */
	public void setFileUrl(String fileUrl){
		this.fileUrl = fileUrl;
	}
	
	/**
	 * 
	 */
	public String getFileUrlWatermark(){
		return fileUrlWatermark;
	}
	
	/**
	 * 
	 */
	public void setFileUrlWatermark(String fileUrlWatermark){
		this.fileUrlWatermark = fileUrlWatermark;
	}
	
	/**
	 * 
	 */
	public String getEtag(){
		return etag;
	}
	
	/**
	 * 
	 */
	public void setEtag(String etag){
		this.etag = etag;
	}
	
	/**
	 * 
	 */
	public String getEtagWatermark(){
		return etagWatermark;
	}
	
	/**
	 * 
	 */
	public void setEtagWatermark(String etagWatermark){
		this.etagWatermark = etagWatermark;
	}

}