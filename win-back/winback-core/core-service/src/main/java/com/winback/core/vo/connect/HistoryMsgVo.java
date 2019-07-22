package com.winback.core.vo.connect;


import lombok.Data;

import java.io.Serializable;

@Data
public class HistoryMsgVo implements Serializable {

    private String id;

    private String name;

    private String imageUrl;

    private String msgInfo;

    private String createTime;

    private String type;

    private String acctId;

    private String msgType;

    private String personType;

    private String timeInfo;//时长




}
