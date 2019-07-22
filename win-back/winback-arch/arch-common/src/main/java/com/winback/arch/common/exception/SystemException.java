
package com.winback.arch.common.exception;


public class SystemException extends BaseException {

	private static final long serialVersionUID = -1344852932375992652L;
	
	public SystemException() {
		super();
	}

	public SystemException(String code) {
		super();
		if (code != null) {
			super.exceptionCode = code;
		}
	}
	
	public SystemException(ExceptionEnum exceptionEnum) {
		super();
		if (exceptionEnum!= null) {
			super.exceptionCode = exceptionEnum.getCode();
			super.message = exceptionEnum.getMessage();
		}
	}

}
