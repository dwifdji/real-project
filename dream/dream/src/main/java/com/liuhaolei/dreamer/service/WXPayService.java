package com.liuhaolei.dreamer.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liuhaolei.dreamer.common.res.ResponseModel;

public interface WXPayService {

	ResponseModel appPayOrder(String fee);

	void getPayStatus(HttpServletRequest req, HttpServletResponse res);

}
