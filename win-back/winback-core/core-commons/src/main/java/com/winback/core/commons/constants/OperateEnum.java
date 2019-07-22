package com.winback.core.commons.constants;

public class OperateEnum {

    public enum  HomeBanner{
        STARTUP_ITEM("1", "启动项"),
        ADVERTISING_SPACE("2", "广告位");
        private String type;

        private String typeDesc;

        HomeBanner(String type, String typeDesc) {
            this.type = type;
            this.typeDesc = typeDesc;
        }

        public static String getDescByType(String type) {
            OperateEnum.HomeBanner[] values = OperateEnum.HomeBanner.values();
            for (HomeBanner homeBanner:values) {
                if(homeBanner.type.equals(type)) {
                    return  homeBanner.typeDesc;
                }
            }
            return null;
        }
    }


    public enum OperateIconEnum{
        QUICK_RELEASE("1", "快速发布"),
        NAVIGATION_BAR("2", "导航栏配置"),
        QUALITY_CASE("3", "优质案件"),
        CONTRACT_MODEL("4", "合同范本");

        private String type;

        private String typeDesc;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTypeDesc() {
            return typeDesc;
        }

        public void setTypeDesc(String typeDesc) {
            this.typeDesc = typeDesc;
        }

        OperateIconEnum(String type, String typeDesc) {
            this.type = type;
            this.typeDesc = typeDesc;
        }

        public static String getDescByType(String type) {
            OperateEnum.OperateIconEnum[] values = OperateEnum.OperateIconEnum.values();
            for (OperateIconEnum operateIconEnum:values) {
                if(operateIconEnum.type.equals(type)) {
                    return  operateIconEnum.typeDesc;
                }
            }
            return null;
        }
    }


    public enum QuickReleaseEnum{
        RISK_INVESTMENT("0", "风险投"),
        JOINT_EXECUTION("1", "联合执行"),
        LITIGATION_EXECUTION("2", "诉讼执行"),
        COMMISSION_EXECUTION("3", "委托执行"),
        DEBT_ACQUISITION("4", "债权收购");

        private String type;

        private String typeDesc;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTypeDesc() {
            return typeDesc;
        }

        public void setTypeDesc(String typeDesc) {
            this.typeDesc = typeDesc;
        }

        QuickReleaseEnum(String type, String typeDesc) {
            this.type = type;
            this.typeDesc = typeDesc;
        }

        public static String getTypeByDesc(String typeDesc) {
            OperateEnum.QuickReleaseEnum[] values = OperateEnum.QuickReleaseEnum.values();
            for (QuickReleaseEnum operateIconEnum:values) {
                if(operateIconEnum.typeDesc.equals(typeDesc)) {
                    return operateIconEnum.type;
                }
            }
            return null;
        }
    }


    public enum  AnnouncementType{
        APP_ANNOUNCEMENT("1", "App公告");
        private String type;

        private String typeDesc;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTypeDesc() {
            return typeDesc;
        }

        public void setTypeDesc(String typeDesc) {
            this.typeDesc = typeDesc;
        }

        AnnouncementType(String type, String typeDesc) {
            this.type = type;
            this.typeDesc = typeDesc;
        }

        public static String getDescByType(String type) {
            OperateEnum.AnnouncementType[] values = OperateEnum.AnnouncementType.values();
            for (AnnouncementType announcementType:values) {
                if(announcementType.type.equals(type)) {
                    return  announcementType.typeDesc;
                }
            }
            return null;
        }
    }

}
