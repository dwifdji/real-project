package com._360pai.core.facade.activity.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xdrodger
 * @Title: FileInfo
 * @ProjectName zeus
 * @Description:
 * @date 05/09/2018 15:29
 */
@Getter
@Setter
public class FileInfo implements Serializable {

    private String downloadUrl;

    private String viewpdfUrl;

    private Date allSignedAt;
}
