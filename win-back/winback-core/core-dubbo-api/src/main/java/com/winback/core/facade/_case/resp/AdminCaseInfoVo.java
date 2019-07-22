package com.winback.core.facade._case.resp;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author RuQ
 * @Title: AdminCaseInfoVo
 * @ProjectName winback
 * @Description:
 * @date 2019/2/19 9:47
 */
@Setter
@Getter
public class AdminCaseInfoVo implements Serializable {
    private List<CaseAttachmentVo> attachmentList;
    private List operateList;
}
