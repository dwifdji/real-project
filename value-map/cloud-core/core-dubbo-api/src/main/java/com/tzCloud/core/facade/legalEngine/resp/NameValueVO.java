package com.tzCloud.core.facade.legalEngine.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xiaolei
 * @create 2019-06-14 14:59
 */
@Data
public class NameValueVO implements Serializable {
    private static final long serialVersionUID = -5873007281393424218L;

    private String name;
    private String value;
}
