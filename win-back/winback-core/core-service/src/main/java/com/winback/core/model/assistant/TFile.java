
package com.winback.core.model.assistant;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年02月15日 17时21分00秒
 */
public class TFile implements java.io.Serializable{

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
