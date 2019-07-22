
package com.tzCloud.core.dao.caseMatching.impl;

import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.arch.core.sysconfig.dataSource.DataType;
import com.tzCloud.core.condition.caseMatching.TLawyerDataCondition;
import com.tzCloud.core.dao.caseMatching.TLawyerDataDao;
import com.tzCloud.core.dao.caseMatching.mapper.TLawyerDataMapper;
import com.tzCloud.core.model.caseMatching.TLawyerData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service
public class TLawyerDataDaoImpl extends AbstractDaoImpl<TLawyerData, TLawyerDataCondition, BaseMapper<TLawyerData,TLawyerDataCondition>> implements TLawyerDataDao {
	
	@Resource
	private TLawyerDataMapper tLawyerDataMapper;
	
	@Override
	protected BaseMapper<TLawyerData, TLawyerDataCondition> daoSupport() {
		return tLawyerDataMapper;
	}

	@Override
	protected TLawyerDataCondition blankCondition() {
		return new TLawyerDataCondition();
	}


	@Override
	public int batchUpdateWinFlag(List<Integer> ids, String flag) {
		return tLawyerDataMapper.batchUpdateWinFlag(ids, flag);
	}

	@Override
	public List<TLawyerData> findJoinHtml() {
		return tLawyerDataMapper.findJoinHtml();
	}

	@Override
	public long count() {
		return tLawyerDataMapper.count();
	}

	@Override
	public int batchUpdateIdentity(List<Integer> ids, String identity) {
		return tLawyerDataMapper.batchUpdateIdentity(ids, identity);
	}

	@Override
	public List<TLawyerData> findNoIdentity() {
		return tLawyerDataMapper.findNoIdentity();
	}

	@Override
	public List<TLawyerData> findNoIdentity2() {
		return tLawyerDataMapper.findNoIdentity2();
	}

	@Override
	public List<TLawyerData> findByParam(Map<String, Object> param) {
		return tLawyerDataMapper.findByParam(param);
	}

	@Override
	public List<TLawyerData> findByLawyer(String lawyer) {
		return tLawyerDataMapper.findByLawyer(lawyer);
	}

	@Override
	public List<TLawyerData> findLawyerDirty() {
		return tLawyerDataMapper.findLawyerDirty();
	}

	@Override
	public List<TLawyerData> findLawyerNoLawyerId() {
		return tLawyerDataMapper.findLawyerNoLawyerId();
	}

	@Override
	public int batchUpdateLawyerId(List<TLawyerData> list) {
		return tLawyerDataMapper.batchUpdateLawyerId(list);
	}

	@Override
	public List<TLawyerData> findUnusualData1() {
		return tLawyerDataMapper.findUnusualData1();
	}

	@Override
	public int deleteById(int id) {
		return tLawyerDataMapper.deleteById(id);
	}

	@Override
	public List<TLawyerData> findLawyerByLawyerId(List<Long> id) {
		return tLawyerDataMapper.findLawyerByLawyerId(id);
	}

	@Override
	public int updateLawyerIdByLawyerId(Long oldId, Long newId) {
		return tLawyerDataMapper.updateLawyerIdByLawyerId(oldId, newId);
	}

	@Override
	public Integer getCountDocId(String lawyerId) {
		return tLawyerDataMapper.getCountDocId(lawyerId);
	}

	@Override
	public List<TLawyerData> findBy(List<String> docIds) {
		return tLawyerDataMapper.findByDocIds(docIds);
	}

	@Override
	public List<TLawyerData> findBy(String docId) {
		TLawyerDataCondition condition = new TLawyerDataCondition();
		condition.setDocid(docId);
		return tLawyerDataMapper.selectByCondition(condition);
	}

	@Override
	@DataType(value = "mycatCrawler")
	public Map<Long,Map<String, String>> findGroupDocIdByLawyerIds(String lawyerIds) {
		return tLawyerDataMapper.findGroupDocIdByLawyerIds(lawyerIds);
	}
}
