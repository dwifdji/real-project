
package com.tzCloud.core.dao.view.mapper;

import com.tzCloud.core.facade.caseMatching.resp.LawyerForSearchingListResp;
import org.apache.ibatis.annotations.Mapper;

import com.tzCloud.core.condition.view.ViewBriefLawfirmLawyerCondition;
import com.tzCloud.core.model.view.ViewBriefLawfirmLawyer;
import com.tzCloud.arch.core.abs.BaseMapper;

import java.util.List;

/**
 * <p>ViewBriefLawfirmLawyer数据层的基础操作</p>
 * @ClassName: ViewBriefLawfirmLawyerMapper
 * @Description: ViewBriefLawfirmLawyer数据层的基础操作
 * @author Generator
 * @date 2019年03月15日 11时13分23秒
 */
@Mapper
public interface ViewBriefLawfirmLawyerMapper extends BaseMapper<ViewBriefLawfirmLawyer, ViewBriefLawfirmLawyerCondition>{

    List<LawyerForSearchingListResp> getLawyerList(ViewBriefLawfirmLawyerCondition condition);

    List<ViewBriefLawfirmLawyer> selectNeedProcessList(ViewBriefLawfirmLawyerCondition condition);
}
