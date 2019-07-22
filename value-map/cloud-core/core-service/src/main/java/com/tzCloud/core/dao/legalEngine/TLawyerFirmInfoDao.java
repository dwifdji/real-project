
package com.tzCloud.core.dao.legalEngine;

import com.tzCloud.core.condition.legalEngine.TLawyerFirmInfoCondition;
import com.tzCloud.core.model.legalEngine.TLawyerFirmInfo;
import com.tzCloud.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>TLawyerFirmInfo的基础操作Dao</p>
 * @ClassName: TLawyerFirmInfoDao
 * @Description: TLawyerFirmInfo基础操作的Dao
 * @author Generator
 * @date 2019年04月28日 16时43分49秒
 */
public interface TLawyerFirmInfoDao extends BaseDao<TLawyerFirmInfo,TLawyerFirmInfoCondition>{
    List<TLawyerFirmInfo> selectByFirmNameLike(String name);
}
