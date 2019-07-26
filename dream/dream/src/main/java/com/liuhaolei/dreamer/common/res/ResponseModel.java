package com.liuhaolei.dreamer.common.res;

import java.util.Date;

/**
 * 
 *  create by liuhaolei on 2018-09-11
 *  响应返回模板
 *
 */
public class ResponseModel {
	
	private String code;
	
	private String msg;
	
	private Object data;
	
	private Long timestamp;
	
	

	 

	public String getCode() {
		return code;
	}


	public String getMsg() {
		return msg;
	}


	public Object getData() {
		return data;
	}


	public Long getTimestamp() {
		return timestamp;
	}


	public ResponseModel(String code, String msg, Object data, Long timestamp) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
		this.timestamp = timestamp;
	}
	
	
	public ResponseModel(String msg, Long timestamp) {
		super();
		this.msg = msg;
		this.timestamp = timestamp;
	}

	public ResponseModel(String msg) {
		super();
		this.msg = msg;
		this.timestamp = System.currentTimeMillis();;
	}
	
	public ResponseModel(ResultStatus resultStatus, Object data, Long timestamp) {
		super();
		this.code = resultStatus.getCode();
		this.msg = resultStatus.getMsg();
		this.data = data;
		this.timestamp = timestamp;
	}
	
	
	public static ResponseModel successApi(ResultStatus resultStatus, Object data) {
		
		return new ResponseModel(resultStatus, data, new Date().getTime());
	}
	
	public static ResponseModel failApi(ResultStatus resultStatus) {
		
		return new ResponseModel(resultStatus.getCode(), resultStatus.getMsg(), null, System.currentTimeMillis());
	}
	
}
