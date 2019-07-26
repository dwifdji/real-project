package com.mybatisPlus.demo.service;

import com.mybatisPlus.demo.model.JdPmTransactionDataAssets;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liuhaolei
 * @since 2019-06-11
 */
public interface JdPmTransactionDataAssetsService extends IService<JdPmTransactionDataAssets> {

	List<JdPmTransactionDataAssets> getJDTransactionData();

 

}
