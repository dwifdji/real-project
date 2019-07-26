package com.mybatisPlus.demo.service;

import com.mybatisPlus.demo.model.LjTransactionData;
import com.mybatisPlus.demo.vo.LjTransactionDataVO;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liuhaolei
 * @since 2019-06-12
 */
public interface LjTransactionDataService extends IService<LjTransactionData> {

	List<LjTransactionData> getLjTransactionData(int beginIndex, int num, List<LjTransactionDataVO> liLjTransactionDatas2);

	List<LjTransactionDataVO> getLjTransactionDataVOS();

	List<LjTransactionDataVO> getProblems();

}
