
package com.tzCloud.arch.common.exception;

/**
 * BaseException
 *
 * @version 1.0.0
 */
public class BaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	protected String exceptionCode;
	protected String message;

	public BaseException() {
		super();
	}

	public BaseException(String code) {
		super();
		if (code != null) {
			this.exceptionCode = code;
		}
	}

	public BaseException(Throwable arg0) {
		super(arg0);
	}
	
	public BaseException(ExceptionEnum exceptionEnum) {
		super();
		if (exceptionEnum!= null) {
			this.exceptionCode = exceptionEnum.getCode();
			this.message = exceptionEnum.getMessage();
		}
	}
	
	public BaseException(String code, String message) {
		super();
		if (code != null) {
			this.exceptionCode = code;
		}
		if (message != null) {
			this.message = message;
		}
	}

	/**
	 * 构造方
	 *
	 * @param message
	 * @param cause
	 */
	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public String getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
