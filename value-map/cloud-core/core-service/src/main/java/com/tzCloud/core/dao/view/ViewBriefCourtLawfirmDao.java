
package com.tzCloud.core.dao.view;

import com.tzCloud.core.condition.view.ViewBriefCourtLawfirmCondition;
import com.tzCloud.core.facade.caseMatching.resp.LawyerFirmResp;
import com.tzCloud.core.model.view.ViewBriefCourtLawfirm;
import com.tzCloud.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>ViewBriefCourtLawfirm的基础操作Dao</p>
 * @ClassName: ViewBriefCourtLawfirmDao
 * @Description: ViewBriefCourtLawfirm基础操作的Dao
 * @author Generator
 * @date 2019年03月15日 11时13分23秒
 */
public interface ViewBriefCourtLawfirmDao extends BaseDao<ViewBriefCourtLawfirm,ViewBriefCourtLawfirmCondition>{

    List<LawyerFirmResp> getLawFirmList(ViewBriefCourtLawfirmCondition req);
}
