
package com.tzCloud.core.dao.legalEngine.impl;

import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.core.condition.legalEngine.DsrxxParseStatusCondition;
import com.tzCloud.core.dao.legalEngine.DsrxxParseStatusDao;
import com.tzCloud.core.dao.legalEngine.mapper.DsrxxParseStatusMapper;
import com.tzCloud.core.model.legalEngine.DsrxxParseStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DsrxxParseStatusDaoImpl extends AbstractDaoImpl<DsrxxParseStatus, DsrxxParseStatusCondition, BaseMapper<DsrxxParseStatus,DsrxxParseStatusCondition>> implements DsrxxParseStatusDao{
	
	@Resource
	private DsrxxParseStatusMapper dsrxxParseStatusMapper;
	
	@Override
	protected BaseMapper<DsrxxParseStatus, DsrxxParseStatusCondition> daoSupport() {
		return dsrxxParseStatusMapper;
	}

	@Override
	protected DsrxxParseStatusCondition blankCondition() {
		return new DsrxxParseStatusCondition();
	}

	@Override
	public void batchInsert(List<String> docIds) {
		dsrxxParseStatusMapper.batchInsert(docIds);
	}

	@Override
	public void batchUpdate(List<String> docIds) {
		dsrxxParseStatusMapper.batchUpdate(docIds);
	}

	@Override
	public void batchUpdate(List<String> docIds, int status) {
		dsrxxParseStatusMapper.batchUpdateStatus(docIds, status);
	}

	@Override
	public void batchUpdateUnParsed(List<String> list) {
		dsrxxParseStatusMapper.batchUpdateUnParsed(list);
	}

	@Override
	public void batchUpdateErrorParsed(List<String> list) {
		dsrxxParseStatusMapper.batchUpdateErrorParsed(list);
	}

	@Override
	public List<String> findNoParseDocId() {
		return dsrxxParseStatusMapper.findNoParseDocId();
	}

	@Override
	public Long findNoParseDocId_COUNT() {
		return dsrxxParseStatusMapper.findNoParseDocId_COUNT();
	}

	@Override
	public List<String> findDocId(List<String> docIds) {
		return dsrxxParseStatusMapper.findDocId(docIds);
	}
}
