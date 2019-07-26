package com.mybatisPlus.demo.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mybatisPlus.demo.mapper.JdAssetDataMapper;
import com.mybatisPlus.demo.mapper.JdPmTransactionDataAssetsMapper;
import com.mybatisPlus.demo.model.JdAssetData;
import com.mybatisPlus.demo.model.JdPmTransactionDataAssets;
import com.mybatisPlus.demo.service.JdPmTransactionDataAssetsService;
import com.mybatisPlus.demo.util.HttpsUtil;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liuhaolei
 * @since 2019-06-11
 */
@Service
public class JdPmTransactionDataAssetsServiceImpl extends ServiceImpl<JdPmTransactionDataAssetsMapper, JdPmTransactionDataAssets> implements JdPmTransactionDataAssetsService {
	
	@Autowired
	private JdPmTransactionDataAssetsMapper JdPmTransactionDataAssetsMapper;
	@Autowired
	private JdAssetDataMapper jdAssetDataMapper;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<JdPmTransactionDataAssets> getJDTransactionData() {
		
		List<JdPmTransactionDataAssets> jdPmTransactionDataAssets = 
				JdPmTransactionDataAssetsMapper.getJDTransactionData();
		
		return jdPmTransactionDataAssets;
		
	}

 

}
