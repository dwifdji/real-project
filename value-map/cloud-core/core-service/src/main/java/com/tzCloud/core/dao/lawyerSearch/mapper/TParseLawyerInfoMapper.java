
package com.tzCloud.core.dao.lawyerSearch.mapper;

import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.core.condition.lawyerSearch.TParseLawyerInfoCondition;
import com.tzCloud.core.facade.legalEngine.req.LawyerSearchCondition;
import com.tzCloud.core.facade.legalEngine.resp.LawFirmInfoVO;
import com.tzCloud.core.facade.legalEngine.resp.LawyerInfoVO;
import com.tzCloud.core.model.lawyerSearch.TParseLawyerInfo;
import com.tzCloud.core.model.legalEngine.FieldCount;
import com.tzCloud.core.model.legalEngine.LawFirmAnalysis;
import com.tzCloud.core.vo.LawyerVO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>TParseLawyerInfo数据层的基础操作</p>
 * @ClassName: TParseLawyerInfoMapper
 * @Description: TParseLawyerInfo数据层的基础操作
 * @author Generator
 * @date 2019年04月19日 09时15分09秒
 */
@Mapper
public interface TParseLawyerInfoMapper extends BaseMapper<TParseLawyerInfo, TParseLawyerInfoCondition>{
    int batchSave(List<TParseLawyerInfo> list);
    List<TParseLawyerInfo> searchList(@Param("conditions") LawyerSearchCondition conditions,@Param("orderBy")  String orderBy);
    List<TParseLawyerInfo> searchByIdList(List<Integer> lawyerId);
    List<LawFirmInfoVO> searchByIdLawFirmShort(@Param("lawFirmShort") String lawFirmShort);
    List<LawFirmInfoVO> searchByIdLawFirm(@Param("lawFirm") String lawFirm);
    List<TParseLawyerInfo> searchGroupByLawFirm();
    List<FieldCount> getBriefDistributed(String lawFirm);
    LawFirmAnalysis getJudgeResultCount(String lawFirm);
    List<FieldCount> getCourtLevel(String lawFirm);
    List<FieldCount> getDsrxxStatistics(String lawFirm);
    List<FieldCount> getJudgeStatistics(String lawFirm);
    List<FieldCount> getCourtStatistics(String lawFirm);
    List<LawyerInfoVO> getLawyerListByLawFirm(String lawFirm);
    List<TParseLawyerInfo> findByIdGreaterThan(Integer id);
    List<LawyerVO> findByIdGreaterThan2(Integer id);
    List<TParseLawyerInfo> getByLawyerFirmISNUll();
    List<TParseLawyerInfo> getByLawyerFirmError1();
    List<TParseLawyerInfo> getByLawyerFirmError2();
    int deleteById(Long id);
    List<TParseLawyerInfo> getLawyerByltUpdateTime(@Param("updateTime") String updateTime);
    List<TParseLawyerInfo> getLawyerByNoLawFirmId();
    List<TParseLawyerInfo> getCaseCountByLawFirm(String lawFirm);
    List<TParseLawyerInfo> getLawFirmGroupBylawFirm(@Param("toESTime") String toESTime);
    @MapKey("id")
    Map<Long, Map<String, String>> getDocIdsByGroupId();
    List<String> getLawFirmShortGroupBy();
    List<TParseLawyerInfo> searchByLawyerIds(List<Integer> lawyerIds);
}
