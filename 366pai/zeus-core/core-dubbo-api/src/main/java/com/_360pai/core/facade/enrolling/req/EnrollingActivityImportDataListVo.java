
package com._360pai.core.facade.enrolling.req;

import lombok.Data;

import java.util.List;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年02月14日 10时04分51秒
 */
@Data
public class EnrollingActivityImportDataListVo implements java.io.Serializable{

    private String id;


    private String userName;

    private String name;


    private String location;


    private String contactPerson;


    private String contactPhone;


    private String disposalService;


    private String fundProvider;

    private String image;

    private String refPrice;


    private List<String> images;


    private String status;

    private String rejectReason;
    private String debtorPro;

    private String debtorCity;
    private String debtorArea;

    private Boolean rejectFlag;




}
