package com.winback.core.commons.constants;

public class FinanceEnum {



    /**
     *
      *出款状态
     */
    public  enum EXPEND_STATUS
    {
        INITIAL("0", "待审核"),
        ALREADY("1", "已放款");


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
        EXPEND_STATUS(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (EXPEND_STATUS c : EXPEND_STATUS.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }

        //根据值获取type
        public static String getType(String typeName) {
            for (EXPEND_STATUS c : EXPEND_STATUS.values()) {
                if (c.getTypeName().equals(typeName)) {
                    return c.getType();
                }
            }
            return null;
        }
    }


    /**
     *
     *出票状态
     */
    public  enum INVOICE_STATUS
    {
        INITIAL("0", "待审核"),
        ALREADY("1", "已开票");


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
        INVOICE_STATUS(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (INVOICE_STATUS c : INVOICE_STATUS.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }

        //根据值获取type
        public static String getType(String typeName) {
            for (INVOICE_STATUS c : INVOICE_STATUS.values()) {
                if (c.getTypeName().equals(typeName)) {
                    return c.getType();
                }
            }
            return null;
        }
    }




    /**
     *
     *回款状态
     */
    public  enum RECEIVABLE_STATUS
    {
        INITIAL("0", "待审核"),
        ALREADY("1", "已收款");


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
        RECEIVABLE_STATUS(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (RECEIVABLE_STATUS c : RECEIVABLE_STATUS.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }

        //根据值获取type
        public static String getType(String typeName) {
            for (RECEIVABLE_STATUS c : RECEIVABLE_STATUS.values()) {
                if (c.getTypeName().equals(typeName)) {
                    return c.getType();
                }
            }
            return null;
        }
    }




    /**
     *1 增值税专用 2 增值税普通发票
     *出票状态
     */
    public  enum INVOICE_TYPE
    {
        INITIAL("1", "增值税专用"),
        ALREADY("2", "增值税普通发票");


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
        INVOICE_TYPE(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (INVOICE_TYPE c : INVOICE_TYPE.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }

        //根据值获取type
        public static String getType(String typeName) {
            for (INVOICE_TYPE c : INVOICE_TYPE.values()) {
                if (c.getTypeName().equals(typeName)) {
                    return c.getType();
                }
            }
            return null;
        }
    }





    /**
     *
     *回款类型
     */
    public  enum RECEIVABLE_TYPE
    {
        ONE("1", "收入"),
        TWO("2", "退费");


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
        RECEIVABLE_TYPE(String type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }

        //根据code 获取描述
        public static String getDesc(String type) {
            for (RECEIVABLE_TYPE c : RECEIVABLE_TYPE.values()) {
                if (c.getType().equals(type)) {
                    return c.getTypeName();
                }
            }
            return null;
        }

        //根据值获取type
        public static String getType(String typeName) {
            for (RECEIVABLE_TYPE c : RECEIVABLE_TYPE.values()) {
                if (c.getTypeName().equals(typeName)) {
                    return c.getType();
                }
            }
            return null;
        }
    }

}
