package com.tzCloud.core.facade.legalEngine.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author xiaolei
 * @create 2019-05-15 16:36
 */
@Data
public class LawFirmESVO implements Serializable {

    private static final long serialVersionUID = -8853366374807419599L;

    // 公司名称
    private String lawFirm;
    // 城市
    private String lawFirmCity;
    // 省份
    private String lawFirmProvince;
    // 联系地址
    private String contactAddress;
    // 营业执照
    private String licenseNumber;
    // 联系电话
    private String contactNumber;
    // 创建时间
    private String foundTime;
    // 迁移至es时间
    private String toESTime;
    // 简洁
    private String introduction;
    //
    private List<LawyerStatistics> lawyerStatistics;

//    public class LawyerStatistics implements Serializable{
//        private static final long serialVersionUID = 6957485031049558762L;
//        public Long id;
//        public Integer caseCount;
//    }
}
