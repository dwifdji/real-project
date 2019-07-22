package com._360pai.core.facade.account.req;

import com._360pai.arch.common.RequestModel;
import com._360pai.core.facade.account.vo.ModuleVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author xdrodger
 * @Title: PermissionReq
 * @ProjectName zeus
 * @Description:
 * @date 2018/10/7 22:11
 */
public class RoleReq {

    @Getter
    @Setter
    public static class BaseReq extends RequestModel {
        private Integer roleId;
        private Integer operatorId;
    }

    @Getter
    @Setter
    public static class QueryReq extends RequestModel {
        private String q;
    }

    @Getter
    @Setter
    public static class CreateReq extends RequestModel {
        @NotBlank
        private String name;
        private String description;
        private List<ModuleVo> modules;
        private Integer operatorId;
    }

    @Getter
    @Setter
    public static class UpdateReq extends RequestModel {
        @NotNull
        private Integer id;
        private String name;
        private String description;
        private List<ModuleVo> modules;
        private Integer operatorId;
    }
}
