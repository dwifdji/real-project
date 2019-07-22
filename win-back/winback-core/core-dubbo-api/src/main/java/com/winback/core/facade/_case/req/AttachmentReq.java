package com.winback.core.facade._case.req;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author RuQ
 * @Title: AttachmentReq
 * @ProjectName winback
 * @Description:
 * @date 2019/2/21 18:50
 */
@Getter
@Setter
public class AttachmentReq implements Serializable {
    /**
     * 案件id
     */
    private String caseId;
    /**
     * 案件状态
     */
    private String caseStatus;
    /**
     * 附件名称
     */
    private String attachName;
    /**
     * 附件url
     */
    private String attachUrl;

    private String attachType;
}
