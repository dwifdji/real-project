
package com._360pai.core.dao.assistant;

import com._360pai.core.condition.assistant.BannerCondition;
import com._360pai.core.model.assistant.Banner;
import com._360pai.arch.core.abs.BaseDao;

/**
 * <p>Banner的基础操作Dao</p>
 * @ClassName: BannerDao
 * @Description: Banner基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分16秒
 */
public interface BannerDao extends BaseDao<Banner,BannerCondition>{

    int deleteBanner(Integer paramsId);
}
