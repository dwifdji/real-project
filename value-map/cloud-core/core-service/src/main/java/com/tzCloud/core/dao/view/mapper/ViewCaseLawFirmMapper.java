
package com.tzCloud.core.dao.view.mapper;

import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.core.condition.view.ViewCaseLawFirmCondition;
import com.tzCloud.core.model.view.ViewCaseLawFirm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>ViewCaseLawFirm数据层的基础操作</p>
 *
 * @author Generator
 * @ClassName: ViewCaseLawFirmMapper
 * @Description: ViewCaseLawFirm数据层的基础操作
 * @date 2019年04月28日 14时39分26秒
 */
@Mapper
public interface ViewCaseLawFirmMapper extends BaseMapper<ViewCaseLawFirm, ViewCaseLawFirmCondition> {

    List<Map<String, Object>> findByCourtName(@Param("courtName") String name);
}
