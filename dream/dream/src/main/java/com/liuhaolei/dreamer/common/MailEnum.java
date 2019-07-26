package com.liuhaolei.dreamer.common;

import java.io.Serializable;

public class MailEnum implements Serializable{
	
	private String msgSubject;
	
	private String message;

	 

	public MailEnum() {
		super();
	}

	public String getMsgSubject() {
		return msgSubject;
	}

	public void setMsgSubject(String msgSubject) {
		this.msgSubject = msgSubject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
