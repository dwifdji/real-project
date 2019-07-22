package com.tzCloud.core.model.legalEngine;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xiaolei
 * @create 2019-05-05 15:02
 */
@Data
public class FieldCount implements Serializable {
    private static final long serialVersionUID = 4473934863226508818L;
    private String field1;
    private Integer count1;
    private String field2;
}
