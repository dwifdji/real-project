package com._360pai.core.service.activity;


import com.github.pagehelper.PageInfo;

public interface PlatformBroadcastService {


    PageInfo getBroadcastList(int page, int perPage);

}