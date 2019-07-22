
package com._360pai.core.dao.assistant;

import com._360pai.core.condition.assistant.InstructionsContentCondition;
import com._360pai.core.model.assistant.InstructionsContent;
import com._360pai.arch.core.abs.BaseDao;

/**
 * <p>InstructionsContent的基础操作Dao</p>
 * @ClassName: InstructionsContentDao
 * @Description: InstructionsContent基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分17秒
 */
public interface InstructionsContentDao extends BaseDao<InstructionsContent,InstructionsContentCondition>{

    int deleteInstructionsContent(Integer paramsId);

    InstructionsContent getAppletAgreement(String name);
}
