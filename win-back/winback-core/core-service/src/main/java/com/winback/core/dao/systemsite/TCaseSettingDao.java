
package com.winback.core.dao.systemsite;

import com.winback.core.condition.systemsite.TCaseSettingCondition;
import com.winback.core.model.systemsite.TCaseSetting;
import com.winback.arch.core.abs.BaseDao;
import com.winback.core.vo.systemsite.CaseSiteVO;

import java.util.List;

/**
 * <p>TCaseSetting的基础操作Dao</p>
 * @ClassName: TCaseSettingDao
 * @Description: TCaseSetting基础操作的Dao
 * @author Generator
 * @date 2019年01月23日 10时13分56秒
 */
public interface TCaseSettingDao extends BaseDao<TCaseSetting,TCaseSettingCondition>{

    List<CaseSiteVO> getCaseSettingList(String type);
}
