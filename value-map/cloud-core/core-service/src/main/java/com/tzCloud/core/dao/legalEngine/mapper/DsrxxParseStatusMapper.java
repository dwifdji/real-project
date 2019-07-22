
package com.tzCloud.core.dao.legalEngine.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.tzCloud.core.condition.legalEngine.DsrxxParseStatusCondition;
import com.tzCloud.core.model.legalEngine.DsrxxParseStatus;
import com.tzCloud.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>DsrxxParseStatus数据层的基础操作</p>
 * @ClassName: DsrxxParseStatusMapper
 * @Description: DsrxxParseStatus数据层的基础操作
 * @author Generator
 * @date 2019年06月25日 09时42分26秒
 */
@Mapper
public interface DsrxxParseStatusMapper extends BaseMapper<DsrxxParseStatus, DsrxxParseStatusCondition>{
    void batchInsert(List<String> docIds);
    void batchUpdate(List<String> docIds);
    void batchUpdateStatus(@Param("list") List<String> docIds,@Param("status") int status);
    List<String> findNoParseDocId();
    Long findNoParseDocId_COUNT();
    void batchUpdateUnParsed(List<String> list);
    void batchUpdateErrorParsed(List<String> list);
    List<String> findDocId(List<String> docIds);
}
