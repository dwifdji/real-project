
package com.tzCloud.core.dao.caseMatching.impl;

import javax.annotation.Resource;

import com.tzCloud.arch.core.sysconfig.dataSource.DataType;
import org.springframework.stereotype.Service;

import com.tzCloud.core.condition.caseMatching.CaseHtmlDsrxxCondition;
import com.tzCloud.core.dao.caseMatching.mapper.CaseHtmlDsrxxMapper;
import com.tzCloud.core.model.caseMatching.CaseHtmlDsrxx;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.core.dao.caseMatching.CaseHtmlDsrxxDao;

import java.util.List;

@Service
public class CaseHtmlDsrxxDaoImpl extends AbstractDaoImpl<CaseHtmlDsrxx, CaseHtmlDsrxxCondition, BaseMapper<CaseHtmlDsrxx,CaseHtmlDsrxxCondition>> implements CaseHtmlDsrxxDao{
	
	@Resource
	private CaseHtmlDsrxxMapper caseHtmlDsrxxMapper;
	
	@Override
	protected BaseMapper<CaseHtmlDsrxx, CaseHtmlDsrxxCondition> daoSupport() {
		return caseHtmlDsrxxMapper;
	}

	@Override
	protected CaseHtmlDsrxxCondition blankCondition() {
		return new CaseHtmlDsrxxCondition();
	}

	@Override
	public void batchInsert(List<CaseHtmlDsrxx> list) {
		caseHtmlDsrxxMapper.batchInsert(list);
	}

	@Override
	public List<String> findDocId() {
		return caseHtmlDsrxxMapper.findDocId();
	}

	@Override
	public Long findDocId_COUNT() {
		return caseHtmlDsrxxMapper.findDocId_COUNT();
	}

	@Override
	public List<CaseHtmlDsrxx> findUnusualList() {
		return caseHtmlDsrxxMapper.findUnusualList();
	}

	@Override
	public void deleteById(Integer id) {
		caseHtmlDsrxxMapper.deleteById(id);
	}

	@Override
	@DataType(value = "mycatCrawler")
	public List<CaseHtmlDsrxx> findMoreThanId(Integer id) {
		return caseHtmlDsrxxMapper.findMoreThanId(id);
	}
}
