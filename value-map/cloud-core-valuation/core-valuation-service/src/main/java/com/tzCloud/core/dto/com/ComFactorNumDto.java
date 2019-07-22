package com.tzCloud.core.dto.com;

import lombok.Data;

import java.io.Serializable;

/**
 *地点搜索
 */
@Data
public class ComFactorNumDto implements Serializable {
    private String factorName;//
    private String num;
    private String factorType;


}
