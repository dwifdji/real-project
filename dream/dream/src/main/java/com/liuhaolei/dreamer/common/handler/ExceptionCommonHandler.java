package com.liuhaolei.dreamer.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liuhaolei.dreamer.common.exception.BusinessException;
import com.liuhaolei.dreamer.common.exception.RefalshException;
import com.liuhaolei.dreamer.common.res.ResponseModel;

/**
 * 
 * create by liuhaolei on 2018/09/15
 * 实现全局controller所有异常处理
 *
 */
@ControllerAdvice
public class ExceptionCommonHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionCommonHandler.class);
	
	/**
	 * 处理全局controller的异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseModel handlerControllerException(Exception e) {
		LOGGER.error(e.getMessage(), e);
		
		return new ResponseModel(e.getMessage());
	}
	
	/**
	 * 处理所有的业务层Service数据
	 * @param e
	 * @return
	 */
	@ExceptionHandler(BusinessException.class)
	@ResponseBody
	public ResponseModel handlerBusinessException(Exception e) {
		LOGGER.error(e.getMessage(), e);
		
		return new ResponseModel(e.getMessage());
	}
	
	/**
	 * 处理所有的业务层Service数据
	 * @param e
	 * @return
	 */
	@ExceptionHandler(RefalshException.class)
	@ResponseBody
	public ResponseModel handlerLoginException(Exception e) {
		LOGGER.error(e.getMessage(), e);
		
		return new ResponseModel("402", "刷新token", e.getMessage(), 
				System.currentTimeMillis());
	}

}
