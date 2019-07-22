
package com.winback.core.dao.systemsite;

import com.winback.core.condition.systemsite.TCaseStatusMsgTemplateCondition;
import com.winback.core.model.systemsite.TCaseStatusMsgTemplate;
import com.winback.arch.core.abs.BaseDao;
import com.winback.core.vo.systemsite.CaseStatusMsgVO;

import java.util.List;

/**
 * <p>TCaseStatusMsgTemplate的基础操作Dao</p>
 * @ClassName: TCaseStatusMsgTemplateDao
 * @Description: TCaseStatusMsgTemplate基础操作的Dao
 * @author Generator
 * @date 2019年01月23日 10时13分56秒
 */
public interface TCaseStatusMsgTemplateDao extends BaseDao<TCaseStatusMsgTemplate,TCaseStatusMsgTemplateCondition>{

    List<CaseStatusMsgVO> getCaseStatusMsgList(String type);
}
