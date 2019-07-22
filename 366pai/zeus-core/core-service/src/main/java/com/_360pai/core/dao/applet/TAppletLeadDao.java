
package com._360pai.core.dao.applet;

import com._360pai.core.condition.applet.TAppletLeadCondition;
import com._360pai.core.facade.shop.vo.ShopGuideVO;
import com._360pai.core.model.applet.TAppletLead;
import com._360pai.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>TAppletLead的基础操作Dao</p>
 * @ClassName: TAppletLeadDao
 * @Description: TAppletLead基础操作的Dao
 * @author Generator
 * @date 2018年11月22日 14时40分30秒
 */
public interface TAppletLeadDao extends BaseDao<TAppletLead,TAppletLeadCondition>{

    List<ShopGuideVO> getRemainingGuides(String openId);
}
