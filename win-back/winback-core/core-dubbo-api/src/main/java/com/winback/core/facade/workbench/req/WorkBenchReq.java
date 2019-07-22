package com.winback.core.facade.workbench.req;

import com.winback.arch.common.RequestModel;
import lombok.Getter;
import lombok.Setter;

public class WorkBenchReq {

    @Getter
    @Setter
    public static class SearchDay extends RequestModel {

        private String searchDay;
    }

    @Getter
    @Setter
    public static class SearchType extends RequestModel{

        private String searchType;
    }


    @Getter
    @Setter
    public static class WindControlReq extends RequestModel{

        private String type;

        private String status;
    }

    @Getter
    @Setter
    public static class FinanceReq extends RequestModel{

        private String type;

        private String status;
    }

}
