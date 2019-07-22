
package com._360pai.core.dao.applet.impl;

import javax.annotation.Resource;

import com._360pai.core.facade.shop.dto.DeleteSearchRecordDto;
import com._360pai.core.facade.shop.dto.SearchRecordListDto;
import com._360pai.core.facade.shop.req.ShopReq;
import com._360pai.core.facade.shop.vo.SearchRecordListVO;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.applet.TAppletSearchRecordCondition;
import com._360pai.core.dao.applet.mapper.TAppletSearchRecordMapper;
import com._360pai.core.model.applet.TAppletSearchRecord;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.applet.TAppletSearchRecordDao;

import java.util.List;
import java.util.Map;

@Service
public class TAppletSearchRecordDaoImpl extends AbstractDaoImpl<TAppletSearchRecord, TAppletSearchRecordCondition, BaseMapper<TAppletSearchRecord,TAppletSearchRecordCondition>> implements TAppletSearchRecordDao{
	
	@Resource
	private TAppletSearchRecordMapper tAppletSearchRecordMapper;
	
	@Override
	protected BaseMapper<TAppletSearchRecord, TAppletSearchRecordCondition> daoSupport() {
		return tAppletSearchRecordMapper;
	}

	@Override
	protected TAppletSearchRecordCondition blankCondition() {
		return new TAppletSearchRecordCondition();
	}

	@Override
	public List<SearchRecordListVO> getSearchRecordList(SearchRecordListDto params) {

		return tAppletSearchRecordMapper.getSearchRecordList(params);
	}

	@Override
	public void deleteSearchRecord(Map<String, Object> params) {
		tAppletSearchRecordMapper.deleteSearchRecord(params);
	}
}
