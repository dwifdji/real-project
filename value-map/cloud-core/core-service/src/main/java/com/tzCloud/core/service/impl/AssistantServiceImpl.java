package com.tzCloud.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.tzCloud.arch.core.redis.RedisCachemanager;
import com.tzCloud.core.constant.AssistantEnum;
import com.tzCloud.core.constant.SysConstant;
import com.tzCloud.core.dao.assistant.TConfigDao;
import com.tzCloud.core.dao.caseMatching.TLawyerDataDao;
import com.tzCloud.core.dao.caseMatching.TLawyerInfoDao;
import com.tzCloud.core.dao.legalEngine.TCaseDao;
import com.tzCloud.core.facade.legalEngine.vo.GuidedCase;
import com.tzCloud.core.model.caseMatching.TLawyerData;
import com.tzCloud.core.model.caseMatching.TLawyerInfo;
import com.tzCloud.core.model.legalEngine.TCase;
import com.tzCloud.core.service.AssistantService;
import com.tzCloud.core.utils.AESUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author xdrodger
 * @Title: AssistantServiceImpl
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019/6/19 15:54
 */
@Slf4j
@Service
public class AssistantServiceImpl implements AssistantService {
    @Autowired
    private TConfigDao configDao;
    @Autowired
    private RedisCachemanager redisCachemanager;
    @Autowired
    private TCaseDao caseDao;
    @Autowired
    private TLawyerDataDao lawyerDataDao;
    @Autowired
    private TLawyerInfoDao lawyerInfoDao;

    @Override
    public List<String> getHotWords() {
        String str = configDao.getValue(AssistantEnum.ConfigType.HotWord.getKey());
        if (StringUtils.isNotBlank(str)) {
            return Arrays.asList(str.split(","));
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<GuidedCase> getGuidedCases() {
        String cache = (String) redisCachemanager.get(SysConstant.GUIDED_CASE_KEY);
        if (StringUtils.isBlank(cache)) {
            List<GuidedCase> list = new ArrayList<>();
            String str = configDao.getValue(AssistantEnum.ConfigType.GuidedCase.getKey());
            if (StringUtils.isNotBlank(str)) {
                List<String> docIds = Arrays.asList(str.split(","));
                for (String docId : docIds) {
                    GuidedCase guidedCase = new GuidedCase();
                    TCase tCase = caseDao.findBy(docId);
                    guidedCase.setDocId(AESUtil.encrypt(tCase.getDocId()));
                    guidedCase.setTitle(tCase.getTitle());
                    guidedCase.setCourtOpinion(tCase.getCourtOpinion());
                    guidedCase.setJudgementDate(tCase.getJudgementDate());
                    List<TLawyerData> lawyerDataList = lawyerDataDao.findBy(docId);
                    if (lawyerDataList.size() > 0) {
                        TLawyerData lawyerData = lawyerDataList.get(0);
                        guidedCase.setLawyer(lawyerData.getLawyer());
                        guidedCase.setLawFirm(lawyerData.getLawFirm());
                        guidedCase.setLawyerImgUrl(SysConstant.DEFAULT_LAWYER_IMG_URL);
                        TLawyerInfo lawyerInfo = lawyerInfoDao.findBy(lawyerData.getLawyer(), lawyerData.getLawFirm());
                        if (lawyerInfo != null) {
                            guidedCase.setLawyerImgUrl(lawyerInfo.getImgUrl());
                        }
                    }
                    list.add(guidedCase);
                }
                redisCachemanager.set(SysConstant.GUIDED_CASE_KEY, JSON.toJSONString(list));
            }
            return list;
        }
        return JSON.parseArray(cache, GuidedCase.class);
    }
}
