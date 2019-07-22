
package com.winback.core.dao.operate.mapper;

import com.winback.arch.core.abs.BaseMapper;
import com.winback.core.dto.operate.AdvertisingSpaceDto;
import com.winback.core.vo.operate.AdvertisingSpaceListVO;
import com.winback.core.vo.operate.HomePageBannerVO;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.operate.TOperateBannerCondition;
import com.winback.core.model.operate.TOperateBanner;

import java.util.List;


/**
 * <p>TOperateBanner数据层的基础操作</p>
 * @ClassName: TOperateBannerMapper
 * @Description: TOperateBanner数据层的基础操作
 * @author Generator
 * @date 2019年01月22日 10时27分34秒
 */
@Mapper
public interface TOperateBannerMapper extends BaseMapper<TOperateBanner, TOperateBannerCondition> {

    List<AdvertisingSpaceListVO> getAdvertisingSpaceList(AdvertisingSpaceDto params);

    List<TOperateBanner> getAdvertisingSpaceListTest(AdvertisingSpaceDto params);

    List<HomePageBannerVO> getBannerList(AdvertisingSpaceDto params);
}
