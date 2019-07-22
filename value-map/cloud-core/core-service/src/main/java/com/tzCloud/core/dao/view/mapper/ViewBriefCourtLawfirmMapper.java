
package com.tzCloud.core.dao.view.mapper;

import com.tzCloud.core.facade.caseMatching.resp.LawyerFirmResp;
import org.apache.ibatis.annotations.Mapper;

import com.tzCloud.core.condition.view.ViewBriefCourtLawfirmCondition;
import com.tzCloud.core.model.view.ViewBriefCourtLawfirm;
import com.tzCloud.arch.core.abs.BaseMapper;

import java.util.List;

/**
 * <p>ViewBriefCourtLawfirm数据层的基础操作</p>
 * @ClassName: ViewBriefCourtLawfirmMapper
 * @Description: ViewBriefCourtLawfirm数据层的基础操作
 * @author Generator
 * @date 2019年03月15日 11时13分23秒
 */
@Mapper
public interface ViewBriefCourtLawfirmMapper extends BaseMapper<ViewBriefCourtLawfirm, ViewBriefCourtLawfirmCondition>{

    List<LawyerFirmResp> getLawFirmList(ViewBriefCourtLawfirmCondition req);
}
