
package com.tzCloud.core.dao.caseMatching.impl;

import javax.annotation.Resource;

import com.tzCloud.arch.core.sysconfig.dataSource.DataType;
import com.tzCloud.core.model.caseMatching.TLawyerData;
import org.omg.CORBA.DATA_CONVERSION;
import org.springframework.stereotype.Service;

import com.tzCloud.core.condition.caseMatching.CaseHtmlDataCondition;
import com.tzCloud.core.dao.caseMatching.mapper.CaseHtmlDataMapper;
import com.tzCloud.core.model.caseMatching.CaseHtmlData;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.core.dao.caseMatching.CaseHtmlDataDao;

import java.util.List;

@Service
public class CaseHtmlDataDaoImpl extends AbstractDaoImpl<CaseHtmlData, CaseHtmlDataCondition, BaseMapper<CaseHtmlData,CaseHtmlDataCondition>> implements CaseHtmlDataDao{
	
	@Resource
	private CaseHtmlDataMapper caseHtmlDataMapper;
	
	@Override
	protected BaseMapper<CaseHtmlData, CaseHtmlDataCondition> daoSupport() {
		return caseHtmlDataMapper;
	}

	@Override
	protected CaseHtmlDataCondition blankCondition() {
		return new CaseHtmlDataCondition();
	}

	@Override
	public List<CaseHtmlData> findBySPCX(String spcx, int pageNum, int pageSize) {
		return caseHtmlDataMapper.findBySPCX(spcx, pageNum, pageSize);
	}

	@Override
	public List<CaseHtmlData> findBySPCX() {
		return caseHtmlDataMapper.findBySPCX();
	}

	@Override
	public List<CaseHtmlData> findByDocIds(List<String> docId) {
		return caseHtmlDataMapper.findByDocIdsNoSPCX(docId);
	}

	@Override
	public List<CaseHtmlData> findByLimit() {
		return caseHtmlDataMapper.findByLimit();
	}

	@Override
	public long findByLimit_Count() {
		return caseHtmlDataMapper.findByLimit_Count();
	}

	@Override
	public List<CaseHtmlData> findByNoSave(int pageNum, int pageSize) {
		return caseHtmlDataMapper.findByNoSave(pageNum, pageSize);
	}

	@Override
	public List<CaseHtmlData> findByNewForAnalysis() {
		return caseHtmlDataMapper.findByNewForAnalysis();
	}

	@Override
	public List<CaseHtmlData> findByNewForDsrxx() {
		return caseHtmlDataMapper.findByNewForDsrxx();
	}

	@Override
	public long findByNoUpdateCount() {
		return caseHtmlDataMapper.findByNoUpdateCount();
	}

	@Override
	public long findBySPCXCount() {
		return caseHtmlDataMapper.findBySPCXCount();
	}

	@Override
	public long findByLimitCount() {
		return caseHtmlDataMapper.findByLimitCount();
	}

	@Override
	@DataType(value = "mycatCrawler")
	public CaseHtmlData findBy(String docId) {
		CaseHtmlDataCondition condition = new CaseHtmlDataCondition();
		condition.setDocId(docId);
		List<CaseHtmlData> list = caseHtmlDataMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<String> findDocId() {
		return caseHtmlDataMapper.findDocId();
	}

	@Override
	public Long findDocId_COUNT() {
		return caseHtmlDataMapper.findDocId_COUNT();
	}

	@Override
	public List<CaseHtmlData> findHtmlByDocIds(List<String> docId) {
		return caseHtmlDataMapper.findHtmlByDocIds(docId);
	}
}
