
package com.winback.core.dao.systemsite.mapper;

import com.winback.core.vo.systemsite.CaseStatusMsgVO;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.systemsite.TCaseStatusMsgTemplateCondition;
import com.winback.core.model.systemsite.TCaseStatusMsgTemplate;
import com.winback.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>TCaseStatusMsgTemplate数据层的基础操作</p>
 * @ClassName: TCaseStatusMsgTemplateMapper
 * @Description: TCaseStatusMsgTemplate数据层的基础操作
 * @author Generator
 * @date 2019年01月23日 10时13分56秒
 */
@Mapper
public interface TCaseStatusMsgTemplateMapper extends BaseMapper<TCaseStatusMsgTemplate, TCaseStatusMsgTemplateCondition>{

    List<CaseStatusMsgVO> getCaseStatusMsgList(@Param("type") String type);
}
