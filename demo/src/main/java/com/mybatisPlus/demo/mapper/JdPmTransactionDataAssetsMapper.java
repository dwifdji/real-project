package com.mybatisPlus.demo.mapper;

import com.mybatisPlus.demo.model.JdPmTransactionDataAssets;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuhaolei
 * @since 2019-06-11
 */
public interface JdPmTransactionDataAssetsMapper extends BaseMapper<JdPmTransactionDataAssets> {

	List<JdPmTransactionDataAssets> getJDTransactionData();

}
