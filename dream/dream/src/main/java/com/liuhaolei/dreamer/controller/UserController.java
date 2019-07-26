package com.liuhaolei.dreamer.controller;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liuhaolei.dreamer.common.req.UserReq;
import com.liuhaolei.dreamer.common.res.ResponseModel;
import com.liuhaolei.dreamer.common.res.ResultStatus;
import com.liuhaolei.dreamer.mq.MQSender;
import com.liuhaolei.dreamer.service.UserService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liuhaolei
 * @since 2018-09-11
 */
@RestController
@RequestMapping("user")
public class UserController extends AbertController{
	
	@Autowired
	private UserService userService;
	@Autowired
	private MQSender mqSender;
	
	/**
	 * 新用户注册
	 * @return
	 */
	@PostMapping("/regist.api")
	public ResponseModel registUser(@RequestBody UserReq.userModel userModel) {
		
		if( StringUtils.isBlank(userModel.getUserName()) 
				|| StringUtils.isBlank(userModel.getPassWord())) {
			return ResponseModel.failApi(ResultStatus.PARAMS_EMPTY);
		}
		return userService.saveUser(userModel);
	}
	
	/**
	 * 新用户进行登陆
	 * @return
	 */
	@PostMapping("/login.api")
	public ResponseModel loginUser(@RequestBody UserReq.userModel userModel) {
		
		if(StringUtils.isBlank(userModel.getUserName()) || StringUtils.isBlank(userModel.getPassWord())) {
			return ResponseModel.failApi(ResultStatus.PARAMS_EMPTY);
		}
		
		return userService.logingUser(userModel);
	}
	
	
	@GetMapping("/open/valuationMap/house/getHouseTransactionList")
    public String getHouseTransactionList() {
		
		userService.getHouseTransactionList();
        return "success";
    }
	
	
}

