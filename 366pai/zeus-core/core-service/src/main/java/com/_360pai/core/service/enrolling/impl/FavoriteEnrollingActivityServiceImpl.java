package com._360pai.core.service.enrolling.impl;

import com._360pai.core.common.constants.EnrollingEnum;
import com._360pai.core.condition.enrolling.FavoriteEnrollingActivityCondition;
import com._360pai.core.dao.enrolling.FavoriteEnrollingActivityDao;
import com._360pai.core.dto.enrolling.ActivityIdReqDto;
import com._360pai.core.facade.enrolling.req.EnrollingReq.activityIdTypeReq;
import com._360pai.core.model.enrolling.EnrollingActivity;
import com._360pai.core.model.enrolling.FavoriteEnrollingActivity;
import com._360pai.core.service.enrolling.FavoriteEnrollingActivityService;
import com._360pai.core.vo.enrolling.EnrollingInfoVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteEnrollingActivityServiceImpl implements FavoriteEnrollingActivityService {

    @Autowired
    private FavoriteEnrollingActivityDao favoriteEnrollingActivityDao;


    @Override
    public PageInfo getFocusList(ActivityIdReqDto req) {

        PageHelper.startPage(req.getPage(), req.getPerPage());

        List<EnrollingInfoVo> list = favoriteEnrollingActivityDao.getFocusList(req);
        return new PageInfo<>(list);

     }

    @Override
    public int saveFocus(FavoriteEnrollingActivity req) {
        return favoriteEnrollingActivityDao.insert(req);
    }

	@Override
	public FavoriteEnrollingActivity getFilterModel(activityIdTypeReq req) {
    	if (null == req.getAccountId()) {
			return null;
		}
		FavoriteEnrollingActivityCondition condition = new FavoriteEnrollingActivityCondition();
		condition.setActivityId(Integer.parseInt(req.getActivityId()));
		condition.setResourceId(req.getResourceId());
		condition.setType(req.getFoucsType());
		condition.setPartyId(null != req.getPartyId()?Integer.parseInt(req.getPartyId()):null);
		condition.setAccountId(Integer.valueOf(req.getAccountId()));
		FavoriteEnrollingActivity selectModel = favoriteEnrollingActivityDao.selectOneResult(condition);
		return selectModel;
	}

	@Override
	public Integer deleteModel(String id) {
		Integer deleteCount = favoriteEnrollingActivityDao.deleteModel(id);

		return deleteCount;
	}

	@Override
	public Integer cancelMyFocusList(List<String> focusList) {
		Integer deleteCount = favoriteEnrollingActivityDao.deleteModelList(focusList);
		return deleteCount;
	}

	@Override
	public List<FavoriteEnrollingActivity> getFocusActivityList(Integer partyId, Integer accountId) {
		FavoriteEnrollingActivityCondition favoriteCondition = new FavoriteEnrollingActivityCondition();
		favoriteCondition.setPartyId(partyId);
		favoriteCondition.setAccountId(accountId);
		favoriteCondition.setType(EnrollingEnum.FOCUS_TYPE.WEB.getType());
		return favoriteEnrollingActivityDao.selectList(favoriteCondition);
	}

	@Override
	public int partyIdBind(String accountId, String partyId) {
    	if (partyId != null) {
			FavoriteEnrollingActivityCondition favoriteCondition = new FavoriteEnrollingActivityCondition();
			favoriteCondition.setAccountId(Integer.valueOf(accountId));
			List<FavoriteEnrollingActivity> activities = favoriteEnrollingActivityDao.selectList(favoriteCondition);
			int count = 0;
			for (int i = 0, size = activities.size(); i < size; i ++) {
				FavoriteEnrollingActivity favoriteEnrollingActivity = activities.get(i);
				if (favoriteEnrollingActivity.getPartyId() == null) {
					favoriteEnrollingActivity.setPartyId(Integer.valueOf(partyId));
					count += favoriteEnrollingActivityDao.updateById(favoriteEnrollingActivity);
				}
			}
			return count;
		}
		return 0;
	}

	@Override
	public List<EnrollingActivity> getBeginIn5MinuteList(Integer accountId, Integer partyId) {
		return favoriteEnrollingActivityDao.getBeginIn5MinuteList(accountId, partyId);
	}

	@Override
	public List<EnrollingActivity> getEndIn5MinuteList(Integer accountId, Integer partyId) {
		return favoriteEnrollingActivityDao.getEndIn5MinuteList(accountId, partyId);
	}
}