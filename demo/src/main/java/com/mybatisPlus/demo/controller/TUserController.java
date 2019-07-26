package com.mybatisPlus.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mybatisPlus.demo.model.TUser;
import com.mybatisPlus.demo.service.TUserService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liuhaolei
 * @since 2018-09-06
 */
@RestController
@RequestMapping("/tUser")
public class TUserController {
	
	@Autowired
	private TUserService tUserService;
	
	@GetMapping("/saveUser.api")
	public String saveUser() {
		TUser user = new TUser();
		user.setName("好好学习，天天向上");
		tUserService.insert(user);
		return "终于成功啦";
	}
}

