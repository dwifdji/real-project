package com.tzCloud.core.vo.com;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class FactorInfoDetailVO implements Serializable {

    private String primaryKey;
    private String name;
    private String address;
    private String phone;
    private String score;
    private String perPrice;
    private String pictureUrl;
    private String distance;
    private List<FactorCommentsVO> commentsList;


}
