
package com.tzCloud.core.dao.caseMatching.mapper;

import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.core.condition.caseMatching.TLawyerDataCondition;
import com.tzCloud.core.model.caseMatching.TLawyerData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * <p>TLawyerData数据层的基础操作</p>
 * @ClassName: TLawyerDataMapper
 * @Description: TLawyerData数据层的基础操作
 * @author Generator
 * @date 2019年03月05日 09时26分19秒
 */
@Mapper
public interface TLawyerDataMapper extends BaseMapper<TLawyerData, TLawyerDataCondition> {
    int batchUpdateWinFlag(@Param("list") List<Integer> ids,@Param("flag") String flag);
    List<TLawyerData> findJoinHtml();
    long count();
    int batchUpdateIdentity(@Param("list")List<Integer> ids,@Param("identity") String identity);
    List<TLawyerData> findNoIdentity();
    List<TLawyerData> findNoIdentity2();
    List<TLawyerData> findByParam(Map<String, Object> param);
    List<TLawyerData> findByLawyer(@Param("lawyer") String lawyer);
    List<TLawyerData> findLawyerDirty();
    List<TLawyerData> findLawyerNoLawyerId();
    int batchUpdateLawyerId(List<TLawyerData> list);
    List<TLawyerData> findUnusualData1();
    int deleteById(int id);
    List<TLawyerData> findLawyerByLawyerId(List<Long> id);
    int updateLawyerIdByLawyerId(@Param("oldId") Long oldId,@Param("newId") Long newId);
    Integer getCountDocId(@Param("lawyerId") String lawyerId);

    List<TLawyerData> findByDocIds(@Param("list") List<String> list);
    Map<Long,Map<String, String>> findGroupDocIdByLawyerIds(String lawyerIds);
}
