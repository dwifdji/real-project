package com.liuhaolei.dreamer.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.liuhaolei.dreamer.common.CommonString;
import com.liuhaolei.dreamer.common.MailEnum;
import com.liuhaolei.dreamer.common.req.UserReq.userModel;
import com.liuhaolei.dreamer.common.res.ResponseModel;
import com.liuhaolei.dreamer.common.res.ResultStatus;
import com.liuhaolei.dreamer.conf.Audience;
import com.liuhaolei.dreamer.mapper.UserMapper;
import com.liuhaolei.dreamer.model.User;
import com.liuhaolei.dreamer.mq.MQSender;
import com.liuhaolei.dreamer.service.UserService;
import com.liuhaolei.dreamer.util.EncryptionUtil;
import com.liuhaolei.dreamer.util.JwtTokenUtil;
import com.liuhaolei.dreamer.util.RedisUtil;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liuhaolei
 * @since 2018-09-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private Audience audience;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private MQSender mqSender;

	@Override
	public ResponseModel saveUser(userModel userModel) {
	
		String newPassword = userModel.getPassWord().trim();
		String encPassword = EncryptionUtil.md5(newPassword + CommonString.SALT);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user_name", userModel.getUserName());
		params.put("pass_word", encPassword);
		List<User> userList = userMapper.selectByMap(params);
		if(userList != null && userList.size() > 0) {
			return ResponseModel.failApi(ResultStatus.ALREADY_REGISTERED);
		}
		
		User user = new User();
		user.setUserName(userModel.getUserName().trim());
		user.setMale(userModel.getMale());
		user.setPassWord(encPassword);
		user.setCreateAt(new Date());
		user.setUpdateAt(new Date());
		userMapper.insert(user);
		MailEnum mailEnum = new MailEnum();
		mailEnum.setMessage("注册信息");
		mailEnum.setMsgSubject("您已经注册成功啦");
		mqSender.sendMsgJson(mailEnum);
		
		return ResponseModel.successApi(ResultStatus.SUCCESS, null);
	}

	
	@Override
	public ResponseModel logingUser(userModel userModel) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user_name", userModel.getUserName());
		String newPassword = userModel.getPassWord().trim();
		String encPassword = EncryptionUtil.md5(newPassword + CommonString.SALT);
		params.put("pass_word", encPassword);
		
		List<User> userList = userMapper.selectByMap(params);
		
		if(userList == null || userList.size() <= 0) {
			return ResponseModel.failApi(ResultStatus.EMPTY_DATA);
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		Integer id = userList.get(0).getId();
		String token = JwtTokenUtil.createJWT(userModel.getUserName(), 
				String.valueOf(id), 
				null, 
				audience.getClientId(), 
				audience.getName(),
                audience.getExpiresSecond()*1000,
                audience.getBase64Secret());
		
		//redis设置缓存
		redisUtil.setStr(token, CommonString.USERID + String.valueOf(id), 1200l);
		
		result.put("token", token);
		return ResponseModel.successApi(ResultStatus.SUCCESS, result);
	}


	@Override
	public ResponseModel getUserList() {
		List<User> selectList = userMapper.selectList(null);
		return ResponseModel.successApi(ResultStatus.SUCCESS, selectList);
	}


	@Override
	public void getHouseTransactionList() {
		
 
        
	}
	
	
	public static void main(String[] args) {
		double lat = Double.parseDouble("31.200113") ; //纬度
		double lng = Double.parseDouble("121.535317");//经度
		double dis = Double.parseDouble("1");//半径
		
		 //先计算查询点的经纬度范围  
        double r = 6371;//地球半径千米  
        double dlng =  2 * Math.asin(Math.sin(dis / (2 * r)) / Math.cos(lat * Math.PI / 180));  
        dlng = dlng * 180 / Math.PI;//角度转为弧度  
        double dlat = dis / r;  
        dlat = dlat * 180 / Math.PI;          
        double minlat = getSixFormatNum(lat - dlat) ;  
        double maxlat = getSixFormatNum(lat + dlat);  
        double minlng = getSixFormatNum(lng - dlng);  
        double maxlng = getSixFormatNum(lng + dlng);  
        
        System.out.println(minlat + "-" + maxlat + "-" + minlng + "-" + maxlng);
	}
	
	
	private static double getSixFormatNum(double d) {
		  BigDecimal b = new BigDecimal(d);
	      d = b.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue(); 
	      return d;  
	}
}
