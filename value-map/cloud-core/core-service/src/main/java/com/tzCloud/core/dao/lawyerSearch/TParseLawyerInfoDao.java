
package com.tzCloud.core.dao.lawyerSearch;

import com.github.pagehelper.PageInfo;
import com.tzCloud.arch.core.abs.BaseDao;
import com.tzCloud.core.condition.lawyerSearch.TParseLawyerInfoCondition;
import com.tzCloud.core.facade.legalEngine.req.LawyerSearchCondition;
import com.tzCloud.core.facade.legalEngine.resp.LawFirmInfoVO;
import com.tzCloud.core.facade.legalEngine.resp.LawyerInfoVO;
import com.tzCloud.core.model.lawyerSearch.TParseLawyerInfo;
import com.tzCloud.core.model.legalEngine.FieldCount;
import com.tzCloud.core.model.legalEngine.LawFirmAnalysis;
import com.tzCloud.core.vo.LawyerVO;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * <p>TParseLawyerInfo的基础操作Dao</p>
 * @ClassName: TParseLawyerInfoDao
 * @Description: TParseLawyerInfo基础操作的Dao
 * @author Generator
 * @date 2019年04月19日 09时15分10秒
 */
public interface TParseLawyerInfoDao extends BaseDao<TParseLawyerInfo,TParseLawyerInfoCondition>{
    int batchSave(List<TParseLawyerInfo> list);
    List<TParseLawyerInfo> searchList(LawyerSearchCondition conditions, String orderBy);
    List<TParseLawyerInfo> searchByIdList(List<Integer> lawyerId);
    List<LawFirmInfoVO> searchByIdLawFirmShort(String lawFirmShort);
    List<LawFirmInfoVO> searchByIdLawFirm(String lawFirm);
    List<TParseLawyerInfo> searchGroupByLawFirm();
    Future<List<FieldCount>> getBriefDistributed(String lawFirm);
    Future<LawFirmAnalysis> getJudgeResultCount(String lawFirm);
    Future<List<FieldCount>> getCourtLevel(String lawFirm);
    Future<List<FieldCount>> getDsrxxStatistics(String lawFirm);
    Future<List<FieldCount>> getJudgeStatistics(String lawFirm);
    Future<List<FieldCount>> getCourtStatistics(String lawFirm);
    List<LawyerInfoVO> getLawyerListByLawFirm(String lawFirm);
    PageInfo<TParseLawyerInfo> findByIdGreaterThan(int page, int perPage, Integer id);
    PageInfo<LawyerVO> findByIdGreaterThan2(int page, int perPage, Integer id);
    List<TParseLawyerInfo> getByLawyerFirmISNUll();
    List<TParseLawyerInfo> getByLawyerFirmError1();
    List<TParseLawyerInfo> getByLawyerFirmError2();
    int deleteById(Long id);
    List<TParseLawyerInfo> getLawyerByltUpdateTime(String updateTime);
    List<TParseLawyerInfo> getLawyerByNoLawFirmId();
    List<TParseLawyerInfo> getCaseCountByLawFirm(String lawFirm);
    List<TParseLawyerInfo> getLawFirmGroupBylawFirm(String toESTime);
    Map<Long, Map<String, String>> getDocIdsByGroupId();
    List<String> getLawFirmShortGroupBy();
    List<TParseLawyerInfo> searchByLawFirm(String lawFirm);
    List<TParseLawyerInfo> searchByLawyerIds(List<Integer> lawyerIds);
}
