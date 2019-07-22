package com.winback.core.facade.operate.req;

import com.winback.arch.common.RequestModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class OperationReq {

    @Setter
    @Getter
    public static class SaveOperateIconReq extends RequestModel {

        private String groupType;

        private String name;

        private String imgUrl;

        private String type;

        private String sort;

    }

    @Setter
    @Getter
    public static class UpdateOperateIconReq extends RequestModel{
        private String id;

        private String groupType;

        private String name;

        private String imgUrl;

        private String type;

        private String sort;

        private String updateFlag;
    }

    @Setter
    @Getter
    public static class ListOperateIconReq extends RequestModel{

        private String groupType;

        private String id;

    }

    @Setter
    @Getter
    public static class SaveAdvertisingSpace extends RequestModel{

        private String type;

        private String name;

        private String desc;

        private String imgUrl;

        private String jumpUrl;

        private Date beginTime;

        private Date endTime;

        private String onlineFlag;

        private String sort;

        private String fixedJumpType;


    }


    @Getter
    @Setter
    public static class UpdateAdvertisingSpace extends RequestModel{
        private String updateFlag;

        private String id;

        private String type;

        private String name;

        private String desc;

        private String imgUrl;

        private String jumpUrl;

        private Date beginTime;

        private Date endTime;

        private String onlineFlage;

        private String sort;

        private String fixedJumpType;

    }

    @Getter
    @Setter
    public static class ListAdvertisingSpaceReq extends RequestModel{

        private String type;

        private String id;
    }

    @Getter
    @Setter
    public static class AppBannerListReq extends RequestModel{

        private String type;
    }

    @Getter
    @Setter
    public static class OperateIconListReq extends RequestModel{

        private String groupType;

        private Integer limitSize;
    }


    @Getter
    @Setter
    public static class AnnouncementSaveReq extends RequestModel{

        private String type;

        private String name;

        private Date beginTime;

        private Date endTime;

        private String desc;
    }

    @Getter
    @Setter
    public static class AnnouncementUpdateReq extends RequestModel{

        private String updateFlag;

        private String id;

        private String type;

        private String name;

        private Date beginTime;

        private Date endTime;

        private String desc;
    }

    @Getter
    @Setter
    public static class AnnouncementListReq extends RequestModel{
        private String id;
        private String type;
    }


    @Setter
    @Getter
    public static class  HomeAnnouncementReq extends RequestModel{
        private String type;
    }


    @Setter
    @Getter
    public static class  CaseSizeReq extends RequestModel{
        private Integer caseSize;

        private String lawyerFlag;
    }


    @Setter
    @Getter
    public static class  OpenType extends RequestModel{
        private String type;
    }
}
