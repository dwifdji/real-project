package com._360pai.core.service.activity;


import java.util.List;

public interface ActivityRejectRecordService{
    boolean saveActivityRejectRecord(Integer activityId, String reason);
}