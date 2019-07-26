package com.mybatisPlus.demo.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mybatisPlus.demo.mapper.TUserMapper;
import com.mybatisPlus.demo.model.TUser;
import com.mybatisPlus.demo.service.TUserService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liuhaolei
 * @since 2018-09-06
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements TUserService {

}
