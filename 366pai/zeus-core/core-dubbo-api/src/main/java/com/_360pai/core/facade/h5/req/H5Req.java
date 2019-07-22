package com._360pai.core.facade.h5.req;

import com._360pai.arch.common.RequestModel;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: OnlineActivityReq
 * @ProjectName zeus
 * @Description:
 * @date 2019/1/8 13:32
 */
@Data
public class H5Req implements Serializable {

    @Data
    public static class AnnualMeetingApplyReq extends RequestModel {
        /**
         * 姓名
         */
        @NotBlank
        private String name;
        /**
         * 公司名称
         */
        @NotBlank
        private String companyName;
        /**
         * 职务
         */
        @NotBlank
        private String position;
        /**
         * 手机号
         */
        @NotBlank
        private String mobile;
        /**
         * 是否入驻酒店（0 否 1 是）
         */
        private Boolean isStayOvernight = false;
    }
}
