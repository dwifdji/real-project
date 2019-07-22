
package com.tzCloud.core.dao.caseMatching.mapper;

import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.core.condition.caseMatching.CaseHtmlDataCondition;
import com.tzCloud.core.model.caseMatching.CaseHtmlData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>CaseHtmlData数据层的基础操作</p>
 * @ClassName: CaseHtmlDataMapper
 * @Description: CaseHtmlData数据层的基础操作
 * @author Generator
 * @date 2019年03月05日 11时07分13秒
 */
@Mapper
public interface CaseHtmlDataMapper extends BaseMapper<CaseHtmlData, CaseHtmlDataCondition>{
    List<CaseHtmlData> findBySPCX(@Param("spcx") String spcx,@Param("pageNum") int begin,@Param("pageSize") int end);
    List<CaseHtmlData> findBySPCX();
    List<CaseHtmlData> findByDocIds(List<String> docId);
    List<CaseHtmlData> findByDocIdsNoSPCX(List<String> docId);
    List<CaseHtmlData> findByLimit();
    Long findByLimit_Count();
    List<CaseHtmlData> findByNoSave(@Param("pageNum") int pageNum,@Param("pageSize") int pageSize);
    List<CaseHtmlData> findByNewForAnalysis();
    List<CaseHtmlData> findByNewForDsrxx();
    long findByNoUpdateCount();
    long findBySPCXCount();
    long findByLimitCount();
    List<String> findDocId();
    Long findDocId_COUNT();
    List<CaseHtmlData> findHtmlByDocIds(List<String> docId);
}
