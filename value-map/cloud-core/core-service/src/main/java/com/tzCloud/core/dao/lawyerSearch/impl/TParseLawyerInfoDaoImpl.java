
package com.tzCloud.core.dao.lawyerSearch.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.arch.core.sysconfig.dataSource.DataType;
import com.tzCloud.core.condition.lawyerSearch.TParseLawyerInfoCondition;
import com.tzCloud.core.dao.lawyerSearch.TParseLawyerInfoDao;
import com.tzCloud.core.dao.lawyerSearch.mapper.TParseLawyerInfoMapper;
import com.tzCloud.core.facade.legalEngine.req.LawyerSearchCondition;
import com.tzCloud.core.facade.legalEngine.resp.LawFirmInfoVO;
import com.tzCloud.core.facade.legalEngine.resp.LawyerInfoVO;
import com.tzCloud.core.model.lawyerSearch.TParseLawyerInfo;
import com.tzCloud.core.model.legalEngine.FieldCount;
import com.tzCloud.core.model.legalEngine.LawFirmAnalysis;
import com.tzCloud.core.vo.LawyerVO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

@Service
public class TParseLawyerInfoDaoImpl extends AbstractDaoImpl<TParseLawyerInfo, TParseLawyerInfoCondition, BaseMapper<TParseLawyerInfo,TParseLawyerInfoCondition>> implements TParseLawyerInfoDao{
	
	@Resource
	private TParseLawyerInfoMapper tParseLawyerInfoMapper;
	
	@Override
	protected BaseMapper<TParseLawyerInfo, TParseLawyerInfoCondition> daoSupport() {
		return tParseLawyerInfoMapper;
	}

	@Override
	protected TParseLawyerInfoCondition blankCondition() {
		return new TParseLawyerInfoCondition();
	}

	@Override
	public int batchSave(List<TParseLawyerInfo> list) {
		return tParseLawyerInfoMapper.batchSave(list);
	}

	@Override
	public List<TParseLawyerInfo> searchList(LawyerSearchCondition conditions, String orderBy) {
		return tParseLawyerInfoMapper.searchList(conditions, orderBy);
	}

	@Override
	public List<TParseLawyerInfo> searchByIdList(List<Integer> lawyerId) {
		return tParseLawyerInfoMapper.searchByIdList(lawyerId);
	}

	@Override
	public List<LawFirmInfoVO> searchByIdLawFirmShort(String lawFirmShort) {
		return tParseLawyerInfoMapper.searchByIdLawFirmShort(lawFirmShort);
	}

	@Override
	public List<LawFirmInfoVO> searchByIdLawFirm(String lawFirm) {
		return tParseLawyerInfoMapper.searchByIdLawFirm(lawFirm);
	}

	@Override
	public List<TParseLawyerInfo> searchGroupByLawFirm() {
		return tParseLawyerInfoMapper.searchGroupByLawFirm();
	}

	@Override
	@Async("taskExecutor")
	public Future<List<FieldCount>> getBriefDistributed(String lawFirm) {
		List<FieldCount> briefDistributed = tParseLawyerInfoMapper.getBriefDistributed(lawFirm);
		return new AsyncResult<>(briefDistributed);
	}

	@Override
	@Async("taskExecutor")
	public Future<LawFirmAnalysis> getJudgeResultCount(String lawFirm) {
		LawFirmAnalysis judgeResultCount = tParseLawyerInfoMapper.getJudgeResultCount(lawFirm);
		return new AsyncResult<>(judgeResultCount);
	}

	@Override
	@Async("taskExecutor")
	public Future<List<FieldCount>> getCourtLevel(String lawFirm) {
		List<FieldCount> courtLevel = tParseLawyerInfoMapper.getCourtLevel(lawFirm);
		return new AsyncResult<>(courtLevel);
	}

	@Override
	@Async("taskExecutor")
	public Future<List<FieldCount>> getDsrxxStatistics(String lawFirm) {
		List<FieldCount> dsrxxStatistics = tParseLawyerInfoMapper.getDsrxxStatistics(lawFirm);
		return new AsyncResult<>(dsrxxStatistics);
	}

	@Override
	@Async("taskExecutor")
	public Future<List<FieldCount>> getJudgeStatistics(String lawFirm) {
		List<FieldCount> judgeStatistics = tParseLawyerInfoMapper.getJudgeStatistics(lawFirm);
		return new AsyncResult<>(judgeStatistics);
	}

	@Override
	@Async("taskExecutor")
	public Future<List<FieldCount>> getCourtStatistics(String lawFirm) {
		List<FieldCount> courtStatistics = tParseLawyerInfoMapper.getCourtStatistics(lawFirm);
		return new AsyncResult<>(courtStatistics);
	}

	@Override
	public List<LawyerInfoVO> getLawyerListByLawFirm(String lawFirm) {
		return tParseLawyerInfoMapper.getLawyerListByLawFirm(lawFirm);
	}

    @Override
	@DataType(value = "mycatCrawler")
    public PageInfo<TParseLawyerInfo> findByIdGreaterThan(int page, int perPage, Integer id) {
        PageHelper.startPage(page, perPage);
        List<TParseLawyerInfo> byIdGreaterThan = tParseLawyerInfoMapper.findByIdGreaterThan(id);
        return new PageInfo<>(byIdGreaterThan);
    }

	@Override
	public PageInfo<LawyerVO> findByIdGreaterThan2(int page, int perPage, Integer id) {
		PageHelper.startPage(page, perPage);
		List<LawyerVO> byIdGreaterThan = tParseLawyerInfoMapper.findByIdGreaterThan2(id);
		return new PageInfo<>(byIdGreaterThan);
	}

	@Override
	public List<TParseLawyerInfo> getByLawyerFirmISNUll() {
		return tParseLawyerInfoMapper.getByLawyerFirmISNUll();
	}

	@Override
	public List<TParseLawyerInfo> getByLawyerFirmError1() {
		return tParseLawyerInfoMapper.getByLawyerFirmError1();
	}

	@Override
	public List<TParseLawyerInfo> getByLawyerFirmError2() {
		return tParseLawyerInfoMapper.getByLawyerFirmError2();
	}

	@Override
	public int deleteById(Long id) {
		return tParseLawyerInfoMapper.deleteById(id);
	}

	@Override
	public List<TParseLawyerInfo> getLawyerByltUpdateTime(String updateTime) {
		return tParseLawyerInfoMapper.getLawyerByltUpdateTime(updateTime);
	}

	@Override
	public List<TParseLawyerInfo> getLawyerByNoLawFirmId() {
		return tParseLawyerInfoMapper.getLawyerByNoLawFirmId();
	}

	@Override
	public List<TParseLawyerInfo> getCaseCountByLawFirm(String lawFirm) {
		return tParseLawyerInfoMapper.getCaseCountByLawFirm(lawFirm);
	}

	@Override
	@DataType(value = "mycatCrawler")
    @Async("taskExecutor")
	public List<TParseLawyerInfo> getLawFirmGroupBylawFirm(String toESTime) {
		return tParseLawyerInfoMapper.getLawFirmGroupBylawFirm(toESTime);
	}

    @Override
    @Async("taskExecutor")
    public Map<Long, Map<String, String>> getDocIdsByGroupId() {
        return tParseLawyerInfoMapper.getDocIdsByGroupId();
    }

	@Override
	public List<String> getLawFirmShortGroupBy() {
		return tParseLawyerInfoMapper.getLawFirmShortGroupBy();
	}

	@Override
	public List<TParseLawyerInfo> searchByLawFirm(String lawFirm) {
		TParseLawyerInfoCondition condition = new TParseLawyerInfoCondition();
		condition.setLawFirm(lawFirm);
		return tParseLawyerInfoMapper.selectByCondition(condition);
	}

	@Override
	public List<TParseLawyerInfo> searchByLawyerIds(List<Integer> lawyerIds) {
		return tParseLawyerInfoMapper.searchByLawyerIds(lawyerIds);
	}
}
