package com._360pai.core.common.constants;

/**
 * 描述：预付款枚举类
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/21 23:51
 */
public class EnrollingEnum {


    /**
     *
     *查询枚举类转换
     */
    public  enum QUERY_STATUS
    {
        PUBLISHED("1", "PUBLISHED"),
        AGENCY_REJECT("2", "AGENCY_REJECT"),
        AGENCY_APPROVED("3", "AGENCY_APPROVED"),
        PLATFORM_REJECT("4", "PLATFORM_REJECT"),
        PLATFORM_APPROVED("5", "PLATFORM_APPROVED"),
        ONLINE("6", "ONLINE"),
        FINISHED("7", "FINISHED"),
        CLOSED("8", "CLOSED"),
        WAIT_PAY("9", "WAIT_PAY"),
        END_PAY("10", "END_PAY");
        private String type;
        private String typeName;

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        public String getTypeName()
        {
            return typeName;
        }

        public void setTypeName(String typeName)
        {
            this.typeName = typeName;
        }
        public boolean test(String code) {
            if (this.getType().equals(code)) {
                return true;
            }
            return false;
        }
         QUERY_STATUS(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (QUERY_STATUS c : QUERY_STATUS.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }
        
        //根据值获取type
        public static String getType(String typeName) {
            for (QUERY_STATUS c : QUERY_STATUS.values()) {
                if (c.getTypeName().equals(typeName)) {
                    return c.getType();
                }
            }
            return null;
        }
    }



    /**
     *
     *查询枚举类转换
     */
    public  enum STATUS
    {
        PUBLISHED("PUBLISHED", "等待机构审核"),
        AGENCY_REJECT("AGENCY_REJECT", "机构审核不通过"),
        AGENCY_APPROVED("AGENCY_APPROVED", "等待平台审核"),
        IMPORT("IMPORT", "上传初始状态"),
        PLATFORM_REJECT("PLATFORM_REJECT", "平台审核不通过"),
        PLATFORM_APPROVED("PLATFORM_APPROVED", "待签协议"),
        ONLINE("ONLINE", "正在报名"),
        FINISHED("FINISHED", "报名结束"),
        CLOSED("CLOSED", "招商结束"),
        NOT_PAY("NOT_PAY", "数据保存未支付"),

        WAIT_PAY("WAIT_PAY", "等待支付"),
        END_PAY("END_PAY", "已失效"),

        ;
        private String type;
        private String typeName;

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        public String getTypeName()
        {
            return typeName;
        }

        public void setTypeName(String typeName)
        {
            this.typeName = typeName;
        }
        public boolean test(String code) {
            if (this.getType().equals(code)) {
                return true;
            }
            return false;
        }
         STATUS(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (STATUS c : STATUS.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }


        //根据code 获取描述
        public static String getStatus(String typeDesc) {
            for (STATUS c : STATUS.values()) {
                if (c.getTypeName().equals(typeDesc)) {
                    return c.getType();
                }
            }
            return null;
        }

    }



    /**
     *
     *提交订单状态
     */
    public  enum ORDER_STATUS
    {
        NOT_PAY("1","未支付"),
        PAY("2", "已支付");
        private String type;
        private String typeName;

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        public String getTypeName()
        {
            return typeName;
        }

        public void setTypeName(String typeName)
        {
            this.typeName = typeName;
        }
        public boolean test(String code) {
            if (this.getType().equals(code)) {
                return true;
            }
            return false;
        }
        ORDER_STATUS(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (ORDER_STATUS c : ORDER_STATUS.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }
    }




    /**
     *
     *查询枚举类转换
     */
    public  enum ERROR_CODE
    {
        EMPTY("001", "参数不能为空！"),

        CLOSED("9", "CLOSED");
        private String type;
        private String typeName;

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        public String getTypeName()
        {
            return typeName;
        }

        public void setTypeName(String typeName)
        {
            this.typeName = typeName;
        }
        public boolean test(String code) {
            if (this.getType().equals(code)) {
                return true;
            }
            return false;
        }
         ERROR_CODE(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (QUERY_STATUS c : QUERY_STATUS.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }
    }




    /**
     * PLATFORM_DEFAULT = 1  # 进ES/上首页/详情页面可见
     * PLATFORM_DETAIL = 2  # 不进ES/不上首页/详情页面可见
     * PLATFORM_INVISIBLE = 3  # 不进ES/不上首页/详情页面不可见
     *查询枚举类转换
     */
    public  enum VISIBILITY_LEVEL
    {
        PLATFORM_DEFAULT("PLATFORM_DEFAULT", "全部可见"),
        PLATFORM_DETAIL("PLATFORM_DETAIL", "详情可见"),
        PLATFORM_INVISIBLE("PLATFORM_INVISIBLE", "全部不可见");

        private String type;
        private String typeName;

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        public String getTypeName()
        {
            return typeName;
        }

        public void setTypeName(String typeName)
        {
            this.typeName = typeName;
        }
        public boolean test(String code) {
            if (this.getType().equals(code)) {
                return true;
            }
            return false;
        }
        private VISIBILITY_LEVEL(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (VISIBILITY_LEVEL c : VISIBILITY_LEVEL.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }
    }


    public  enum VISIBILITY_LEVEL_QUERY
    {
        PLATFORM_DEFAULT("PLATFORM_DEFAULT", "1"),
        PLATFORM_DETAIL("PLATFORM_DETAIL", "2"),
        PLATFORM_INVISIBLE("PLATFORM_INVISIBLE", "3");

        private String type;
        private String typeName;

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        public String getTypeName()
        {
            return typeName;
        }

        public void setTypeName(String typeName)
        {
            this.typeName = typeName;
        }
        public boolean test(String code) {
            if (this.getType().equals(code)) {
                return true;
            }
            return false;
        }
        private VISIBILITY_LEVEL_QUERY(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (VISIBILITY_LEVEL_QUERY c : VISIBILITY_LEVEL_QUERY.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }
    }



    /**
     * LOCKED = 1  # 锁定
     *TAKEN = 2  # 没收
     * RELEASED = 3  # 释放
       *查询枚举类转换
     */
    public  enum PAY_STATUS
    {
        WAIT_LOCKED("WAIT_LOCKED", "锁定等待通知"),
        LOCKED("LOCKED", "锁定"),
        TAKEN("TAKEN", "没收"),
        RELEASED("RELEASED", "释放"),
        NONE_PAY("NONE_PAY", "-");

        private String type;
        private String typeName;

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        public String getTypeName()
        {
            return typeName;
        }

        public void setTypeName(String typeName)
        {
            this.typeName = typeName;
        }
        public boolean test(String code) {
            if (this.getType().equals(code)) {
                return true;
            }
            return false;
        }
         PAY_STATUS(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (PAY_STATUS c : PAY_STATUS.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }
    }


    /**
     * LOCKED = 1  # 锁定
     *TAKEN = 2  # 没收
     * RELEASED = 3  # 释放
     *查询枚举类转换
     */
    public  enum QUERY_PAY_STATUS
    {
        WAIT_LOCKED("WAIT_LOCKED", "4"),
        LOCKED("LOCKED", "1"),
        TAKEN("TAKEN", "3"),
        RELEASED("RELEASED", "2");

        private String type;
        private String typeName;

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        public String getTypeName()
        {
            return typeName;
        }

        public void setTypeName(String typeName)
        {
            this.typeName = typeName;
        }
        public boolean test(String code) {
            if (this.getType().equals(code)) {
                return true;
            }
            return false;
        }
         QUERY_PAY_STATUS(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (QUERY_PAY_STATUS c : QUERY_PAY_STATUS.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }
    }


    /**
     *  模板id 与抵押物类型对应
     *
     */
    public  enum TEMPLATE_TYPE {

        MORTGAGED_PROPERTY("7", "1"),

        PROPERTY_RIGHTS("8", "2"),

        CREDITOR_RIGHTS("6", "3"),

        PROPERTY_RIGHTS_WU("5", "2"),

        THIRD_PARTY_PROPERTY_RIGHTS("14", "2"),
        THIRD_PARTY_PROPERTY_RIGHTS_WU("17", "2"),

        THIRD_PARTY_CREDITOR_RIGHTS("18", "3");



        private String type;

        private String typeName;

        private TEMPLATE_TYPE(String type, String typeName) {
            this.type = type;
            this.typeName = typeName;
        }

        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
        public String getTypeName() {
            return typeName;
        }
        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public static String getDesc(String type) {

            for (TEMPLATE_TYPE enrolling_TYPE : TEMPLATE_TYPE.values()) {
                if(type.equals(enrolling_TYPE.getType())) {
                    return enrolling_TYPE.getTypeName();
                }
            }
            return null;
        }
    }


    /**
     * "1": 抵押物预招商
     * "2": 物权预招商
     * "3": 债权预招商
     *
     */
    public enum ENROLLING_TYPE {
    	
    	MORTGAGED_PROPERTY("1", "抵押物转让"),
    	
    	PROPERTY_RIGHTS("2", "债权转让"),
    	
    	CREDITOR_RIGHTS("3", "物权转让");
    	
    	private String type;
    	
    	private String typeName;
    	
		private ENROLLING_TYPE(String type, String typeName) {
			this.type = type;
			this.typeName = typeName;
		}
		
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getTypeName() {
			return typeName;
		}
		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}
		
		public static String getDesc(String type) {
			
			for (ENROLLING_TYPE enrolling_TYPE : ENROLLING_TYPE.values()) {
				if(type.equals(enrolling_TYPE.getType())) {
					return enrolling_TYPE.getTypeName();
				}
			}
			return null;
		}
    }

    /**
     * 第三方招商类型
     */
    public enum  THIRTY_PARTY_STATUS{
        PLATFORM_INVESTMENT(0, "平台招商"),

        TAOBAO_INVESTMENT(1, "淘宝招商"),

        AGENCY_INVESTMENT(2, "机构招商"),

        CHANG_CHENG(3, "长城资产")

        ;
        private Integer status;

        private String statusDesc;

        THIRTY_PARTY_STATUS(Integer status, String statusDesc) {
            this.status = status;
            this.statusDesc = statusDesc;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getStatusDesc() {
            return statusDesc;
        }

        public void setStatusDesc(String statusDesc) {
            this.statusDesc = statusDesc;
        }

        //根据code 获取描述
        public static String getStatusDesc(Integer status) {
            for (THIRTY_PARTY_STATUS c : THIRTY_PARTY_STATUS.values()) {
                if (c.getStatus() == status) {
                    return c.getStatusDesc();
                }
            }
            return null;
        }
    }


    /**
     *
     *根据code 获取
     */
    public  enum SHOW_AMOUNT
    {
        DYZS("保证金", "1"),
        ZYZS("债权本金", "2"),
        WQZS("市场参考价", "3");
        private String type;
        private String typeName;

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        public String getTypeName()
        {
            return typeName;
        }

        public void setTypeName(String typeName)
        {
            this.typeName = typeName;
        }
        public boolean test(String code) {
            if (this.getType().equals(code)) {
                return true;
            }
            return false;
        }
        private SHOW_AMOUNT(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (SHOW_AMOUNT c : SHOW_AMOUNT.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }


        //根据desc 获取code
        public static String getCode(String desc) {
            for (SHOW_AMOUNT c : SHOW_AMOUNT.values()) {
                if (c.getTypeName().equals(desc)) {
                    return c.getType();
                }
            }
            return null;
        }
    }




    /**
     *
     *预招商生成code前缀
     */
    public  enum MARK_CODE
    {
        DYZS("DYZS-", "1"),
        ZYZS("ZQZS-", "2"),
        WQZS("WQZS-", "3");
        private String type;
        private String typeName;

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        public String getTypeName()
        {
            return typeName;
        }

        public void setTypeName(String typeName)
        {
            this.typeName = typeName;
        }
        public boolean test(String code) {
            if (this.getType().equals(code)) {
                return true;
            }
            return false;
        }
        private MARK_CODE(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (MARK_CODE c : MARK_CODE.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }


        //根据desc 获取code
        public static String getCode(String desc) {
            for (MARK_CODE c : MARK_CODE.values()) {
                if (c.getTypeName().equals(desc)) {
                    return c.getType();
                }
            }
            return null;
        }
    }



    /**
     *
     *报名方展示名称前缀
     */
    public  enum SHOW_NAME_MARK_CODE
    {
        DYZS("D", "1"),
        ZQZS("Z", "2"),
        WQZS("W", "3");
        private String type;
        private String typeName;

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        public String getTypeName()
        {
            return typeName;
        }

        public void setTypeName(String typeName)
        {
            this.typeName = typeName;
        }
        public boolean test(String code) {
            if (this.getType().equals(code)) {
                return true;
            }
            return false;
        }
        private SHOW_NAME_MARK_CODE(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (SHOW_NAME_MARK_CODE c : SHOW_NAME_MARK_CODE.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }


        //根据desc 获取code
        public static String getCode(String desc) {
            for (SHOW_NAME_MARK_CODE c : SHOW_NAME_MARK_CODE.values()) {
                if (c.getTypeName().equals(desc)) {
                    return c.getType();
                }
            }
            return null;
        }
    }


    /**
     *
     *请求的端类型
     */
    public  enum REQ_TYPE
    {
        WEB("WEB", "平台端请求"),
        ADMIN("ADMIN", "管理后台请求"),
        AGENCY("AGENCY", "机构端请求");
        private String type;
        private String typeName;

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        public String getTypeName()
        {
            return typeName;
        }

        public void setTypeName(String typeName)
        {
            this.typeName = typeName;
        }
        public boolean test(String code) {
            if (this.getType().equals(code)) {
                return true;
            }
            return false;
        }
        private REQ_TYPE(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (REQ_TYPE c : REQ_TYPE.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }


        //根据desc 获取code
        public static String getCode(String desc) {
            for (REQ_TYPE c : REQ_TYPE.values()) {
                if (c.getTypeName().equals(desc)) {
                    return c.getType();
                }
            }
            return null;
        }
    }


    /**
     *
     *详情的类型
     */
    public  enum INPUT_TYPE
    {
        FILE("FILE", "文件类型"),
        TEXTAREA("TEXTAREA", "文本框类型"),
        SEDATE("SEDATE", "连接日期类型"),
        DATE("DATE", "日期类型"),
        JSON("JSON", "json类型"),
        CHECKBOX("CHECKBOX", "选择框类型"),
        CITY("CITY", "城市类型"),
        TEXT("TEXT", "文本类型"),
        FILEIMG("FIlEIMG", "图片类型"),

        ;
        private String type;
        private String typeName;

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        public String getTypeName()
        {
            return typeName;
        }

        public void setTypeName(String typeName)
        {
            this.typeName = typeName;
        }
        public boolean test(String code) {
            if (this.getType().equals(code)) {
                return true;
            }
            return false;
        }
        private INPUT_TYPE(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (INPUT_TYPE c : INPUT_TYPE.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }


        //根据desc 获取code
        public static String getCode(String desc) {
            for (INPUT_TYPE c : INPUT_TYPE.values()) {
                if (c.getTypeName().equals(desc)) {
                    return c.getType();
                }
            }
            return null;
        }
    }



    /**
     *
     *抵押物预招商模板必须字段枚举
     */
    public  enum GUARANTEE_MUST_KEY
    {
        NAME("ename", "name","请输入预招商名称！"),
        CITY("ecity", "city","请输入城市信息！"),
        AGENCY_ID("eagency", "agencyId","请输入送拍机构！"),
        REF_PRICE("erefPrice", "refPrice","请输入市场参考价！"),
        DEPOSIT("edeposit", "deposit","请输入保证金金额！"),
        BEGIN_AT("epreviewAt", "beginAt","请输入展示时间！"),
        IMAGES("eimages", "images","请输入展示图片！"),

        EYJCZSJ("eyjczsj", "resp","请输入预计展示时间！"),

        DETAIL("ezcld", "detail",""),
        CONTACT_NAME("econtactName", "contactName",""),
        CONTACT_PHONE("econtactPhone", "contactPhone",""),

        BUS_TYPE_NAME("ebdwlx", "标的物属性",""),

        ;
        private String type;
        private String typeName;
        private String typeDesc;

        public String getTypeDesc() {
            return typeDesc;
        }

        public void setTypeDesc(String typeDesc) {
            this.typeDesc = typeDesc;
        }

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        public String getTypeName()
        {
            return typeName;
        }

        public void setTypeName(String typeName)
        {
            this.typeName = typeName;
        }

        private GUARANTEE_MUST_KEY(String type, String typeName,String typeDesc)
        {
            this.type = type;
            this.typeName = typeName;
            this.typeDesc = typeDesc;
        }

        //根据code 获取描述
        public static String getName(String type) {
            for (GUARANTEE_MUST_KEY c : GUARANTEE_MUST_KEY.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }

        //根据desc 获取提示描述
        public static String getDesc(String type) {
            for (GUARANTEE_MUST_KEY c : GUARANTEE_MUST_KEY.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeDesc();
                }
            }
            return null;
        }
    }





    /**
     *
     * 物权债权必填字段
     */
    public  enum WU_QUAN_MUST_KEY
    {
        NAME("ename", "name","请输入预招商名称！"),
        CITY("ecity", "city","请输入城市信息！"),
        REF_PRICE("erefPrice", "refPrice","请输入市场参考价！"),
        BEGIN_AT("epreviewAt", "beginAt","请输入展示时间！"),
        IMAGES("eimages", "images","请输入展示图片！"),
        DETAIL("ezcld", "detail",""),
        CONTACT_NAME("econtactName", "contactName",""),
        CONTACT_PHONE("econtactPhone", "contactPhone",""),
        ESFDY("esfdy", "esfdy","是否抵押情况"),
        BUS_TYPE_NAME("ebdwlx", "标的物属性",""),


        EBDWSFYDY("ebdwsfydy", "ebdwsfydy","标的物是否有抵押"),



        ;
        private String type;
        private String typeName;
        private String typeDesc;

        public String getTypeDesc() {
            return typeDesc;
        }

        public void setTypeDesc(String typeDesc) {
            this.typeDesc = typeDesc;
        }

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        public String getTypeName()
        {
            return typeName;
        }

        public void setTypeName(String typeName)
        {
            this.typeName = typeName;
        }

        private WU_QUAN_MUST_KEY(String type, String typeName,String typeDesc)
        {
            this.type = type;
            this.typeName = typeName;
            this.typeDesc = typeDesc;
        }

        //根据code 获取描述
        public static String getName(String type) {
            for (WU_QUAN_MUST_KEY c : WU_QUAN_MUST_KEY.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }

        //根据desc 获取提示描述
        public static String getDesc(String type) {
            for (WU_QUAN_MUST_KEY c : WU_QUAN_MUST_KEY.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeDesc();
                }
            }
            return null;
        }
    }




    /**
     *
     * 债权
     */
    public  enum ZHAI_QUAN_MUST_KEY
    {
        NAME("ename", "name","请输入预招商名称！"),
        CITY("ecity", "city","请输入城市信息！"),
        REF_PRICE("ezqbj", "refPrice","请输入债券本金！"),
        DEPOSIT("ezqlx", "deposit","请输入债权利息！"),
        BEGIN_AT("epreviewAt", "beginAt","请输入展示时间！"),
        IMAGES("eimages", "images","请输入展示图片！"),
        DETAIL("ezcld", "detail",""),
        CONTACT_NAME("econtactName", "contactName",""),
        CONTACT_PHONE("econtactPhone", "contactPhone",""),
        ESFYDZYW("esfydzyw", "esfydzyw","是否有抵（质）押物"),
        EDYQK("edyqk", "edyqk","抵押情况"),
        BUS_TYPE_NAME("ebdwlx", "标的物属性",""),


        ;
        private String type;
        private String typeName;
        private String typeDesc;

        public String getTypeDesc() {
            return typeDesc;
        }

        public void setTypeDesc(String typeDesc) {
            this.typeDesc = typeDesc;
        }

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        public String getTypeName()
        {
            return typeName;
        }

        public void setTypeName(String typeName)
        {
            this.typeName = typeName;
        }

        private ZHAI_QUAN_MUST_KEY(String type, String typeName,String typeDesc)
        {
            this.type = type;
            this.typeName = typeName;
            this.typeDesc = typeDesc;
        }

        //根据code 获取描述
        public static String getName(String type) {
            for (ZHAI_QUAN_MUST_KEY c : ZHAI_QUAN_MUST_KEY.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }

        //根据desc 获取提示描述
        public static String getDesc(String type) {
            for (ZHAI_QUAN_MUST_KEY c : ZHAI_QUAN_MUST_KEY.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeDesc();
                }
            }
            return null;
        }
    }
    
    
    public enum ENROLLING_FIELD_TYPE{
    	
    	VARCHAR_255("VARCHAR", "255", "字符长度不能超过255"),
    	VARCHAR_50("VARCHAR", "50", "字符长度不能超过50"),
    	INT("INT", "2147483647", "最大数值不能超过2147483647"),
    	TINYINT("INT", "127", "最大数值不能超过127"),
    	DATETIME("DATETIME", "NOW", "开始时间应该大于当前时间"),
    	BETWENDATE("BETWENDATE", "NOW", "开始时间应该大于当前时间"),
    	DECIMAL_4("DECIMAL", "4", "不能输入超过四位小数的整数或整数"),
        ISBLANK("ISBLANK", "255", "字符长度不能超过255");
    	
    	private String fieldType;
    	
    	private String fieldValue;
    	
    	private String errorMsg;

		public String getFieldType() {
			return fieldType;
		}

		public String getFieldValue() {
			return fieldValue;
		}

		public void setFieldType(String fieldType) {
			this.fieldType = fieldType;
		}

		public void setFieldValue(String fieldValue) {
			this.fieldValue = fieldValue;
		}
		
		public String getErrorMsg() {
			return errorMsg;
		}

		public void setErrorMsg(String errorMsg) {
			this.errorMsg = errorMsg;
		}

		private ENROLLING_FIELD_TYPE(String fieldType, String fieldValue, String errorMsg) {
			this.fieldType = fieldType;
			this.fieldValue = fieldValue;
			this.errorMsg = errorMsg;
		}

		public String getTypeValue(String type) {
			
			for (ENROLLING_FIELD_TYPE enrolling_FIELD_TYPE : ENROLLING_FIELD_TYPE.values()) {
				if(enrolling_FIELD_TYPE.getFieldType().equals(type)) {
					return enrolling_FIELD_TYPE.getFieldValue();
				}
			}
			return null;
		}
		
    }
    
    
    public enum FIELD_TYPE {
    	
    	VARCHAR("VARCHAR"),
    	INT("INT"),
    	TINYINT("TINYINT"),
    	DATETIME("DATETIME"),
    	BETWENDATE("BETWENDATE"),
    	DECIMAL("DECIMAL"),
        ISBLANK("ISBLANK");
    	
    	private String typeName;

		public String getTypeName() {
			return typeName;
		}

		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}

		private FIELD_TYPE(String typeName) {
			this.typeName = typeName;
		}
    	
    }
    
    
    public enum FIELD_regExp {

    	PRICE_REGEXP("^[0-9]+([.]{1}[0-9]+){0,1}$", "只能输入整数或者小数");
    	
    	private String regExpName;
    	
    	private String regExpDesc;

		public String getRegExpName() {
			return regExpName;
		}

		public String getRegExpDesc() {
			return regExpDesc;
		}

		public void setRegExpName(String regExpName) {
			this.regExpName = regExpName;
		}

		public void setRegExpDesc(String regExpDesc) {
			this.regExpDesc = regExpDesc;
		}

		private FIELD_regExp(String regExpName, String regExpDesc) {
			this.regExpName = regExpName;
			this.regExpDesc = regExpDesc;
		}
		
		public String getDesc(String type) {
			for (FIELD_regExp e : FIELD_regExp.values()) {
				if(e.getRegExpName().equals(type)) {
					return e.getRegExpDesc();
				}
			}
			return null;
		}
    }
    
    
    public enum ENROLLING_GUARANTEE {
    	
    	MORTGAGE("0", "无抵押"),
    	UNSECURED("1", "有抵押");
    	
    	private String type;
    	
    	private String typeName;


		public String getType() {
			return type;
		}

		public String getTypeName() {
			return typeName;
		}

		public void setType(String type) {
			this.type = type;
		}

		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}

		
		private ENROLLING_GUARANTEE(String type, String typeName) {
			this.type = type;
			this.typeName = typeName;
		}

		public static String getDesc(String type) {
			for (ENROLLING_GUARANTEE e : ENROLLING_GUARANTEE.values()) {
				if(e.getType().equals(type)) {
					return e.getTypeName();
				}
			}
			return null;
		} 
    	
    }
    
    
    public enum ENROLLING_LABEL {
    	
    	LOCATION("LOCATION", "所在地"),
    	DEBT_PRINCIPAL("DEBT_PRINCIPAL", "债权本金"),
    	REFERENCE_PRICE("REFERENCE_PRICE", "市场参考价"),
    	MORTGAGE("MORTGAGE", "抵押情况"),
    	MARGIN("MARGIN", "保证金");
    	
    	private String type;
    	
    	private String typeName;


		public String getType() {
			return type;
		}

		public String getTypeName() {
			return typeName;
		}

		public void setType(String type) {
			this.type = type;
		}

		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}
		
		private ENROLLING_LABEL(String type, String typeName) {
			this.type = type;
			this.typeName = typeName;
		}

		public static String getDesc(String type) {
			for (ENROLLING_GUARANTEE e : ENROLLING_GUARANTEE.values()) {
				if(e.getType().equals(type)) {
					return e.getTypeName();
				}
			}
			return null;
		} 
    }


    public enum ENROLLING_EXPORT_MODEL{

        MORTGAGED_PROPERTY_MODEL
                ("1", 0, "抵押物预招商",
                new String[]{"申请日期", "预招商活动编号", "预招商名称", "所在省份", "所在地", "委托人", "送拍机构", "市场参考价", "保证金",
                        "项目经理", "报名起始时间", "报名结束时间", "预计处置完成时间", "预招商成交佣金比例", "分公司名称"},
                new String[]{"createdAt", "code", "name", "provinceName", "cityName", "partyName", "agencyName", "refPrice", "deposit", "projectManager", "beginAt", "endAt",
                        "expireAt", "commissionPercent", "branchComName"}
                ),
        PROPERTY_RIGHTS_MODEL
                ("2", 0, "债权招商",
                new String[]{"申请日期", "预招商活动编号", "预招商名称", "所在省份", "所在地", "委托人", "债权本金", "债权利息", "状态",
                        "项目经理", "报名起始时间", "报名结束时间", "分公司名称" },
                new String[]{"createdAt", "code", "name", "provinceName", "cityName", "partyName", "refPrice","deposit", "status", "projectManager", "beginAt", "endAt", "branchComName"}
                ),
        CREDITOR_RIGHTS_MODEL
                ("3", 0, "物权招商",
                new String[]{"申请日期", "预招商活动编号", "预招商名称", "所在省份", "所在地", "委托人", "市场参考价", "状态",
                        "项目经理", "报名起始时间", "报名结束时间", "分公司名称" },
                new String[]{"createdAt", "code", "name", "provinceName", "cityName", "partyName", "refPrice","status", "projectManager", "beginAt", "endAt", "branchComName"}
                ),
        PROPERTY_RIGHTS_TAOBAO_MODEL
                ("2", 1, "第三方债权招商",
                        new String[]{"申请日期", "预招商活动编号", "预招商名称", "所在省份", "所在地", "操作人", "债权本金", "债权利息", "状态",
                                "项目经理", "报名起始时间", "报名结束时间", "分公司名称", "渠道类型", "渠道标题", "渠道路径" },
                        new String[]{"createdAt", "code", "name", "provinceName", "cityName", "operateName", "refPrice","deposit", "status", "projectManager", "beginAt",
                                "endAt", "branchComName", "thirdPartyName", "thirdPartyTitle", "thirdPartyUrl"}
                ),
        CREDITOR_RIGHTS_TAOBAO_MODEL
                ("3", 1, "第三方物权招商",
                        new String[]{"申请日期", "预招商活动编号", "预招商名称", "所在省份", "所在地", "操作人", "市场参考价", "状态",
                                "项目经理", "报名起始时间", "报名结束时间", "分公司名称", "渠道类型", "渠道标题", "渠道路径" },
                        new String[]{"createdAt", "code", "name", "provinceName", "cityName", "operateName", "refPrice","status", "projectManager", "beginAt",
                                "endAt", "branchComName", "thirdPartyName", "thirdPartyTitle", "thirdPartyUrl"}
                )
        ;

        private String enrollingType;

        private Integer thirtyPartyStatus;

        private String fileName;

        private String[] exportCloums;

        private String[] keys;

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String[] getExportCloums() {
            return exportCloums;
        }

        public void setExportCloums(String[] exportCloums) {
            this.exportCloums = exportCloums;
        }

        public String[] getKeys() {
            return keys;
        }

        public void setKeys(String[] keys) {
            this.keys = keys;
        }

        public String getEnrollingType() {
            return enrollingType;
        }

        public void setEnrollingType(String enrollingType) {
            this.enrollingType = enrollingType;
        }

        public Integer getThirtyPartyStatus() {
            return thirtyPartyStatus;
        }

        public void setThirtyPartyStatus(Integer thirtyPartyStatus) {
            this.thirtyPartyStatus = thirtyPartyStatus;
        }

        ENROLLING_EXPORT_MODEL(String enrollingType, Integer thirtyPartyStatus,
                               String fileName, String[] exportCloums, String[] keys) {
            this.enrollingType = enrollingType;
            this.thirtyPartyStatus = thirtyPartyStatus;
            this.fileName = fileName;
            this.exportCloums = exportCloums;
            this.keys = keys;
        }


        public static EnrollingEnum.ENROLLING_EXPORT_MODEL getFileNameByTypeStatus(String type, Integer status){
            for(EnrollingEnum.ENROLLING_EXPORT_MODEL model :EnrollingEnum.ENROLLING_EXPORT_MODEL.values()) {
                if(model.getEnrollingType().equals(type) && model.getThirtyPartyStatus() == status) {
                    return model;
                }
            }
            return  null;
        }
    }




    public enum ENROLLING_THIRD_PARTY_STATUS {

        WEB(0, "平台发布"),
        TAO_BAO(1, "淘宝发布"),
        AGENCY(2, "机构发布"),
        IMPORT(3, "后台批量导入"),
        IMPORT_REAL(4, "后台物权导入"),

        ;

        private Integer type;

        private String typeName;


        public Integer getType() {
            return type;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        ENROLLING_THIRD_PARTY_STATUS(Integer type, String typeName) {
            this.type = type;
            this.typeName = typeName;
        }

        public static String getDesc(Integer type) {
            for (ENROLLING_THIRD_PARTY_STATUS e : ENROLLING_THIRD_PARTY_STATUS.values()) {
                if(e.getType()==type) {
                    return e.getTypeName();
                }
            }
            return null;
        }
    }





    public enum FOCUS_TYPE {

        WEB(0, "平台发布"),
        APPLET(1, "小程序关注"),
        ;

        private Integer type;

        private String typeName;


        public Integer getType() {
            return type;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        FOCUS_TYPE(Integer type, String typeName) {
            this.type = type;
            this.typeName = typeName;
        }

        public static String getDesc(Integer type) {
            for (FOCUS_TYPE e : FOCUS_TYPE.values()) {
                if(e.getType()==type) {
                    return e.getTypeName();
                }
            }
            return null;
        }
    }



    public enum UserType {
        /**
         * # 正常人
         */
        NORMAL_USER("NORMAL_USER", "个人"),
        /**
         * 银行
         */
        BANK_COMPANY("BANK_COMPANY", "银行"),
        /**
         * AMC
         */
        AMC_COMPANY("AMC_COMPANY", "AMC"),
        /**
         * 民营资管
         */
        FOLK_ASSET_COMPANY("FOLK_ASSET_COMPANY", "民营资管"),
        /**
         * 普通公司
         */
        NORMAL_COMPANY("NORMAL_COMPANY", "一般公司"),
        /**
         * 拍卖公司
         */
        AUCTION_COMPANY("AUCTION_COMPANY", "拍卖机构"),
        /**
         * 交易所
         */
        EXCHANGE("EXCHANGE", "交易所")
        ;

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        UserType(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public static String getValueByKey(String key) {
            for (UserType model : UserType.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }
}
