
package com.winback.core.dao._case;

import com.winback.core.condition._case.TCaseStepNoticeTemplateCondition;
import com.winback.core.model._case.TCaseStepNoticeTemplate;
import com.winback.arch.core.abs.BaseDao;
import com.winback.core.vo._case.TCaseStepNoticeTemplateVO;

import java.util.List;

/**
 * <p>TCaseStepNoticeTemplate的基础操作Dao</p>
 * @ClassName: TCaseStepNoticeTemplateDao
 * @Description: TCaseStepNoticeTemplate基础操作的Dao
 * @author Generator
 * @date 2019年01月16日 16时13分05秒
 */
public interface TCaseStepNoticeTemplateDao extends BaseDao<TCaseStepNoticeTemplate,TCaseStepNoticeTemplateCondition>{

    List<TCaseStepNoticeTemplateVO> getTemplateByStepId(String id);

    List<TCaseStepNoticeTemplateVO> getTemplateByBranchId(String id);

}
