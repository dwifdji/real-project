package com.liuhaolei.dreamer.common.exception;

/**
 * 
 * create by liuhaolei on 2018/11/8
 *
 */
public class LoginException extends RuntimeException{
	
	public LoginException() {
		super();
	}
	
	public LoginException(String messagg) {
		super(messagg);
	}
}
