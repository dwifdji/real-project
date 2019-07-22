
package com.winback.core.dao.operate.impl;

import javax.annotation.Resource;

import com.winback.core.dto.operate.OperateAnnouncementDto;
import com.winback.core.vo.operate.AnnouncementDetailVO;
import com.winback.core.vo.operate.HomeAnnouncementVO;
import com.winback.core.vo.operate.OperateAnnouncementVO;
import org.springframework.stereotype.Service;

import com.winback.core.condition.operate.TOperateAnnouncementCondition;
import com.winback.core.dao.operate.mapper.TOperateAnnouncementMapper;
import com.winback.core.model.operate.TOperateAnnouncement;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.operate.TOperateAnnouncementDao;

import java.util.List;

@Service
public class TOperateAnnouncementDaoImpl extends AbstractDaoImpl<TOperateAnnouncement, TOperateAnnouncementCondition, BaseMapper<TOperateAnnouncement,TOperateAnnouncementCondition>> implements TOperateAnnouncementDao{
	
	@Resource
	private TOperateAnnouncementMapper tOperateAnnouncementMapper;
	
	@Override
	protected BaseMapper<TOperateAnnouncement, TOperateAnnouncementCondition> daoSupport() {
		return tOperateAnnouncementMapper;
	}

	@Override
	protected TOperateAnnouncementCondition blankCondition() {
		return new TOperateAnnouncementCondition();
	}

	@Override
	public List<OperateAnnouncementVO> getAnnouncementList(OperateAnnouncementDto params) {
		return tOperateAnnouncementMapper.getAnnouncementList(params);
	}

	@Override
	public List<HomeAnnouncementVO> getHomeAnnouncementList(OperateAnnouncementDto params) {

		return tOperateAnnouncementMapper.getHomeAnnouncementList(params);
	}

	@Override
	public AnnouncementDetailVO getAnnouncementById(String id) {
		return tOperateAnnouncementMapper.getAnnouncementById(id);
	}
}
