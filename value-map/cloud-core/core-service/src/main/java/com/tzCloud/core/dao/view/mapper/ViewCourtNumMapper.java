
package com.tzCloud.core.dao.view.mapper;

import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.core.condition.view.ViewCourtNumCondition;
import com.tzCloud.core.facade.caseMatching.resp.ViewCourtNumResp;
import com.tzCloud.core.model.view.ViewCourtNum;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>ViewCourtNum数据层的基础操作</p>
 * @ClassName: ViewCourtNumMapper
 * @Description: ViewCourtNum数据层的基础操作
 * @author Generator
 * @date 2019年04月19日 09时44分49秒
 */
@Mapper
public interface ViewCourtNumMapper extends BaseMapper<ViewCourtNum, ViewCourtNumCondition>{

    List<ViewCourtNumResp> findByCourtName(ViewCourtNum viewCourtNum);
}
