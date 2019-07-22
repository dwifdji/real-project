package com.winback.arch.common.exception;


public interface ExceptionEnum {
    
	/**
	 * 获取异常代码
	 * 
	 * @return 异常代码
	 */
    String getCode();
	
	/**
	 * 获取异常信息
	 * 
	 * @return 异常信息
	 */
    String getMessage();

}
