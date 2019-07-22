
package com.winback.core.dao.assistant;

import com.winback.core.condition.assistant.THelpItemCondition;
import com.winback.core.facade.assistant.vo.HelpItem;
import com.winback.core.model.assistant.THelpItem;
import com.winback.arch.core.abs.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * <p>THelpItem的基础操作Dao</p>
 * @ClassName: THelpItemDao
 * @Description: THelpItem基础操作的Dao
 * @author Generator
 * @date 2019年01月18日 09时41分05秒
 */
public interface THelpItemDao extends BaseDao<THelpItem,THelpItemCondition>{
    List<HelpItem> getHelpItemList(Boolean display);
}
