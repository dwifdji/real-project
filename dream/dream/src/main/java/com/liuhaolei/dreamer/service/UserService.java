package com.liuhaolei.dreamer.service;

import com.baomidou.mybatisplus.service.IService;
import com.liuhaolei.dreamer.common.req.UserReq.userModel;
import com.liuhaolei.dreamer.common.res.ResponseModel;
import com.liuhaolei.dreamer.model.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liuhaolei
 * @since 2018-09-11
 */
public interface UserService extends IService<User> {

	ResponseModel saveUser(userModel userModel);

	ResponseModel logingUser(userModel userModel);

	ResponseModel getUserList();

	void getHouseTransactionList();

}
