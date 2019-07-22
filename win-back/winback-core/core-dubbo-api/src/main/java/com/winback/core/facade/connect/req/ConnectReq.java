package com.winback.core.facade.connect.req;

import com.winback.arch.common.RequestModel;
import lombok.Getter;
import lombok.Setter;

public class ConnectReq {

    @Setter
    @Getter
    public static class sendReq extends RequestModel {

        private String caseId;

        private String roomId;//房间号id

        private String msgInfo;

        private String accountId; //登录人id

        private String source; //来源

        private String personType; //类型信息

        private String msgType; //消息类型 1 文本 2图片

        private String adminAccountId; //后台平台账号

        private String roomFlag; //是否为小房间推送

        private String status;  // 状态

        private String partyName; //客户姓名


    }


}
