package com._360pai.core.service.enrolling.impl;

import com._360pai.core.condition.enrolling.NotifyPartyEnrollingActivityCondition;
import com._360pai.core.dao.enrolling.NotifyPartyEnrollingActivityDao;
import com._360pai.core.dto.enrolling.ActivityIdReqDto;
import com._360pai.core.facade.enrolling.req.EnrollingReq.activityIdTypeReq;
import com._360pai.core.model.enrolling.EnrollingActivity;
import com._360pai.core.model.enrolling.NotifyPartyEnrollingActivity;
import com._360pai.core.service.enrolling.NotifyPartyEnrollingActivityService;
import com._360pai.core.vo.enrolling.EnrollingInfoVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotifyPartyEnrollingActivityServiceImpl implements NotifyPartyEnrollingActivityService{

	@Autowired
	private NotifyPartyEnrollingActivityDao notifyPartyEnrollingActivityDao;


	@Override
	public PageInfo getNotifiedList(ActivityIdReqDto req) {

		PageHelper.startPage(req.getPage(), req.getPerPage());


		List<EnrollingInfoVo> list = notifyPartyEnrollingActivityDao.getNotifyList(req);


		return new PageInfo<>(list);

 	}

	@Override
	public int saveNotify(NotifyPartyEnrollingActivity notice) {
		return notifyPartyEnrollingActivityDao.insert(notice);
	}

	@Override
	public NotifyPartyEnrollingActivity getFilterModel(activityIdTypeReq req) {
		if (req.getAccountId() == null) return null;
		NotifyPartyEnrollingActivityCondition notifyCondition = new NotifyPartyEnrollingActivityCondition();
		notifyCondition.setAccountId(Integer.parseInt(req.getAccountId()));
		notifyCondition.setActivityId(Integer.parseInt(req.getActivityId()));
		if (req.getPartyId() != null)
			notifyCondition.setPartyId(Integer.parseInt(req.getPartyId()));
		NotifyPartyEnrollingActivity notifyModel = notifyPartyEnrollingActivityDao.selectOneResult(notifyCondition);
		return notifyModel;
	}

	@Override
	public Integer deleteModel(String id) {
		Integer deleteCount = notifyPartyEnrollingActivityDao.deleteModel(id);
		return deleteCount;
	}

	@Override
	public NotifyPartyEnrollingActivity getNotifyPartyById(Integer noticeModelId) {
		NotifyPartyEnrollingActivityCondition notifyCondition = new NotifyPartyEnrollingActivityCondition();
		notifyCondition.setId(noticeModelId);
		return notifyPartyEnrollingActivityDao.selectFirst(notifyCondition);
	}

	@Override
	public List<NotifyPartyEnrollingActivity> getNotifyListByActivityId(Integer noticeModelId) {
		NotifyPartyEnrollingActivityCondition notifyCondition = new NotifyPartyEnrollingActivityCondition();
		notifyCondition.setActivityId(noticeModelId);
		return notifyPartyEnrollingActivityDao.selectList(notifyCondition);
	}

	@Override
	public int partyIdBind(String accountId, String partyId) {
		if (partyId != null) {
			NotifyPartyEnrollingActivityCondition condition = new NotifyPartyEnrollingActivityCondition();
			condition.setAccountId(Integer.valueOf(accountId));
			List<NotifyPartyEnrollingActivity> notifyPartyEnrollingActivities = notifyPartyEnrollingActivityDao.selectList(condition);
			int count = 0;
			for (int i = 0, size = notifyPartyEnrollingActivities.size(); i < size; i++) {
				NotifyPartyEnrollingActivity notifyPartyEnrollingActivity = notifyPartyEnrollingActivities.get(i);
				if (notifyPartyEnrollingActivity.getPartyId() == null) {
					notifyPartyEnrollingActivity.setPartyId(Integer.valueOf(partyId));
					count += notifyPartyEnrollingActivityDao.updateById(notifyPartyEnrollingActivity);
				}
			}
			return count;
		}
		return 0;
	}

	@Override
	public List<EnrollingActivity> getBeginIn5MinuteList(Integer accountId, Integer partyId) {
		return notifyPartyEnrollingActivityDao.getBeginIn5MinuteList(accountId, partyId);
	}

	@Override
	public List<EnrollingActivity> getEndIn5MinuteList(Integer accountId, Integer partyId) {
		return notifyPartyEnrollingActivityDao.getEndIn5MinuteList(accountId, partyId);
	}

}