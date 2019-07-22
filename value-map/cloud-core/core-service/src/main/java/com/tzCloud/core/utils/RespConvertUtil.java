package com.tzCloud.core.utils;

import com.tzCloud.core.constant.CaseEnum;
import com.tzCloud.core.constant.SysConstant;
import com.tzCloud.core.facade.legalEngine.vo.*;
import com.tzCloud.core.model.caseMatching.TTreeContent;
import com.tzCloud.core.model.legalEngine.TCase;
import org.springframework.beans.BeanUtils;

import java.util.Calendar;

/**
 * @author xdrodger
 * @Title: RespConvertUtil
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019/5/22 14:45
 */
public class RespConvertUtil {

    public static CaseVo convertToCaseVo(Case model) {
        CaseVo vo = new CaseVo();
        BeanUtils.copyProperties(model, vo);
        vo.setDocId(AESUtil.encrypt(model.getDocId()));
        vo.setType(CaseEnum.CaseType.getValueByKey(model.getType()));
        vo.setJudgementType(CaseEnum.JudgementType.getValueByKey(model.getJudgementType()));
        vo.setTrialRound(CaseEnum.TrialRound.getValueByKey(model.getTrialRound()));
        Court court = model.getCourt();
        if (court != null) {
            vo.setCourtName(court.getName());
        }
        Brief brief = model.getBrief();
        if (brief != null) {
            vo.setBriefName(brief.getName());
        }
        vo.setOriginalTitle(model.getTitle());
        return vo;
    }

    public static CaseDetailVo convertToCaseDetailVo(TCase model) {
        CaseDetailVo vo = new CaseDetailVo();
        BeanUtils.copyProperties(model, vo);
        TTreeContent treeContent = (TTreeContent) SysConstant.briefTreeContents.get(model.getBriefId());
        if (treeContent != null) {
            vo.setBriefName(treeContent.getKeyWord());
        }
        vo.setCaseNumber(model.getCaseNumber());
        vo.setTitle(model.getTitle());
        vo.setCourtOpinion(model.getCourtOpinion());
        vo.setJudgementType(CaseEnum.JudgementType.getValueByKey(model.getJudgementType()));
        vo.setType(CaseEnum.CaseType.getValueByKey(model.getType()));
        vo.setCourtName(model.getCourtName());
        vo.setTrialRound(CaseEnum.TrialRound.getValueByKey(model.getTrialRound()));
        vo.setJudgementDate(model.getJudgementDate());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(model.getJudgementDate());
        vo.setJudgementYear(calendar.get(Calendar.YEAR));
        return vo;
    }
}
