package com._360pai.core.facade.assistant.req;

import com._360pai.arch.common.RequestModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author zxiao
 * @Title: DepositReq
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/12 14:03
 */
public class DepositReq {

    @Getter
    @Setter
    public static class BaseReq extends RequestModel {
        private Integer partyId;
        private Integer id;

        private String categoryId;
        private String name;
    }

    @Getter
    @Setter
    public static class QueryReq extends RequestModel {
        private String status;
        private String q;
        private String createdAtBegin;
        private String createdAtEnd;
        private Integer id;
    }

    @Getter
    @Setter
    public static class OfflineConfirmReq extends RequestModel {
        @NotNull
        private Long id;
        @NotBlank
        private String voucher;
        private String remark;
        private Integer operatorId;
    }
}
