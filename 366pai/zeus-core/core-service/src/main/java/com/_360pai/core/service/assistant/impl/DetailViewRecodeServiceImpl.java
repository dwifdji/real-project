package com._360pai.core.service.assistant.impl;

import com._360pai.core.dao.assistant.DetailViewRecodeDao;
import com._360pai.core.model.assistant.DetailViewRecode;
import com._360pai.core.service.assistant.DetailViewRecodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2019/4/25 17:08
 */
@Service
public class DetailViewRecodeServiceImpl implements DetailViewRecodeService {

    private static Short recodeType          = 1;
    private static Short enrollingRecodeType = 2;

    private static Short webPathType    = 0;
    private static Short appletPathType = 1;

    @Autowired
    private DetailViewRecodeDao detailViewRecodeDao;


    @Override
    public void insertEnrollingAppletRecode(Integer activityId, Integer accountId, Integer partyId) {
        DetailViewRecode detailViewRecode = new DetailViewRecode(enrollingRecodeType, appletPathType);
        insert(detailViewRecode, activityId, accountId, partyId);
    }

    @Override
    public void insertEnrollingWebRecode(Integer activityId, Integer accountId, Integer partyId) {
        DetailViewRecode detailViewRecode = new DetailViewRecode(enrollingRecodeType, webPathType);
        insert(detailViewRecode,activityId, accountId, partyId);
    }

    @Override
    public void insertActivityAppletRecode(Integer activityId, Integer accountId, Integer partyId) {
        DetailViewRecode detailViewRecode = new DetailViewRecode(recodeType, appletPathType);
        insert(detailViewRecode,activityId, accountId, partyId);
    }

    @Override
    public void insertActivityWebRecode(Integer activityId, Integer accountId, Integer partyId) {
        DetailViewRecode detailViewRecode = new DetailViewRecode(recodeType, webPathType);
        insert(detailViewRecode,activityId, accountId, partyId);
    }

    private void insert(DetailViewRecode detailViewRecode, Integer activityId, Integer accountId, Integer partyId){
        if (accountId !=null) {
            detailViewRecodeDao.insert(detailViewRecode.build(activityId, accountId, partyId));
        }
    }
}
