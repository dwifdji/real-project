package com.liuhaolei.dreamer.common.res;

public enum ResultStatus {
	
	SUCCESS("200", "操作成功"),
	EMPTY_DATA("400", "暂时没有数据"),
	ALREADY_REGISTERED("1001", "您已注册"),
	AUTH_ERROR("401", "token失效认证失败"),
	UPLOAD_ERROR("401", "token失效认证失败"),
	INIT_WX_ERROR("1000", "初始化微信参数异常"),
	PARAMS_EMPTY("401", "参数错误");
	
	
	private String code;
	
	private String msg;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	private ResultStatus(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	
}
