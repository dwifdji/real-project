
package com.winback.core.dao._case;

import com.winback.core.condition._case.TCaseStepRecordCondition;
import com.winback.core.dto._case.CaseStepRecordDto;
import com.winback.core.model._case.TCaseStepRecord;
import com.winback.arch.core.abs.BaseDao;
import com.winback.core.vo._case.TCaseStepRecordVO;

import java.util.List;

/**
 * <p>TCaseStepRecord的基础操作Dao</p>
 * @ClassName: TCaseStepRecordDao
 * @Description: TCaseStepRecord基础操作的Dao
 * @author Generator
 * @date 2019年01月28日 18时47分10秒
 */
public interface TCaseStepRecordDao extends BaseDao<TCaseStepRecord,TCaseStepRecordCondition>{

    List<TCaseStepRecordVO> getCaseStepRecordList(CaseStepRecordDto params);
}
