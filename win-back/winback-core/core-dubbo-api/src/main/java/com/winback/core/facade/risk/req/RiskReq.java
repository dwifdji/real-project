package com.winback.core.facade.risk.req;

import com.winback.arch.common.RequestModel;
import lombok.Getter;
import lombok.Setter;

public class RiskReq {

    @Setter
    @Getter
    public static class keyWordReq extends RequestModel {

        private String keyWord;

        private Integer type;

        private String operatorName;


    }


}
