
package com.tzCloud.core.dao.caseMatching.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.tzCloud.core.condition.caseMatching.CaseHtmlDsrxxCondition;
import com.tzCloud.core.model.caseMatching.CaseHtmlDsrxx;
import com.tzCloud.arch.core.abs.BaseMapper;

import java.util.List;

/**
 * <p>CaseHtmlDsrxx数据层的基础操作</p>
 * @ClassName: CaseHtmlDsrxxMapper
 * @Description: CaseHtmlDsrxx数据层的基础操作
 * @author Generator
 * @date 2019年03月08日 15时17分56秒
 */
@Mapper
public interface CaseHtmlDsrxxMapper extends BaseMapper<CaseHtmlDsrxx, CaseHtmlDsrxxCondition>{
    void batchInsert(List<CaseHtmlDsrxx> list);
    List<String> findDocId();
    Long findDocId_COUNT();
    List<CaseHtmlDsrxx> findUnusualList();
    void deleteById(Integer id);
    List<CaseHtmlDsrxx> findMoreThanId(Integer id);
}
