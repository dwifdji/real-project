package com.liuhaolei.dreamer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liuhaolei.dreamer.common.res.ResponseModel;
import com.liuhaolei.dreamer.model.User;
import com.liuhaolei.dreamer.mq.MQSender;
import com.liuhaolei.dreamer.service.UserService;

@RestController
@RequestMapping("/confind")
public class AdminController extends AbertController{
	
	@Autowired
	private MQSender mqSender;
	@Autowired
	private UserService userService;
	
	/**
	 * 获取所有用户信息
	 * @return
	 */
	@GetMapping("/getUserList.api")
	public ResponseModel getUserList() {
		User userInfo = getUserInfo();
		System.out.println(userInfo.getUserName());
		
		for(int i = 0 ; i < 50; i++) {
			mqSender.sendAll("这是我发送的第" + i + "个信息");
		}
		
		return userService.getUserList();
	}
	

}
