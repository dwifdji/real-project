package com.mybatisPlus.demo.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mybatisPlus.demo.model.LjTransactionData;
import com.mybatisPlus.demo.vo.LjTransactionDataVO;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuhaolei
 * @since 2019-06-12
 */
public interface LjTransactionDataMapper extends BaseMapper<LjTransactionData> {

	List<LjTransactionDataVO> getLjTransactionData();

	List<LjTransactionDataVO> getProblems();

}
