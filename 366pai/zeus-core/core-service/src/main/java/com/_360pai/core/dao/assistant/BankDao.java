
package com._360pai.core.dao.assistant;

import com._360pai.core.condition.assistant.BankCondition;
import com._360pai.core.model.assistant.Bank;
import com._360pai.arch.core.abs.BaseDao;

/**
 * <p>Bank的基础操作Dao</p>
 * @ClassName: BankDao
 * @Description: Bank基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分16秒
 */
public interface BankDao extends BaseDao<Bank,BankCondition>{
    Bank getById(Integer id);

    String getName(Integer id);
}
