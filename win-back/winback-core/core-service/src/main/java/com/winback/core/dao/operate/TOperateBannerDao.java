
package com.winback.core.dao.operate;

import com.winback.arch.core.abs.BaseDao;
import com.winback.core.condition.operate.TOperateBannerCondition;
import com.winback.core.dto.operate.AdvertisingSpaceDto;
import com.winback.core.model.operate.TOperateBanner;
import com.winback.core.vo.operate.AdvertisingSpaceListVO;
import com.winback.core.vo.operate.HomePageBannerVO;

import java.util.List;

/**
 * <p>TOperateBanner的基础操作Dao</p>
 * @ClassName: TOperateBannerDao
 * @Description: TOperateBanner基础操作的Dao
 * @author Generator
 * @date 2019年01月22日 10时27分34秒
 */
public interface TOperateBannerDao extends BaseDao<TOperateBanner,TOperateBannerCondition> {

    List<AdvertisingSpaceListVO> getAdvertisingSpaceList(AdvertisingSpaceDto params);

    List<TOperateBanner> getAdvertisingSpaceListTest(AdvertisingSpaceDto params);

    List<HomePageBannerVO> getBannerList(AdvertisingSpaceDto params);
}
