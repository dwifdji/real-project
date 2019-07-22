package com.winback.core.facade._case.resp;

import com.itextpdf.text.pdf.PRIndirectReference;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author RuQ
 * @Title: CaseAttachmentVo
 * @ProjectName winback
 * @Description:
 * @date 2019/1/24 10:17
 */
@Getter
@Setter
public class CaseAttachmentVo implements Serializable {

    private Integer id;
    /**
     * 附件名称
     */
    private java.lang.String attachName;
    /**
     * 附件url
     */
    private java.lang.String attachUrl;
    private java.lang.String attchType;

    /**
     * 附件类型
     */
    private java.lang.String fileType;

    /**
     * 附件大小
     */
    private String fileSize;

    /**
     * 创建时间
     */
    private String createTime;
}
