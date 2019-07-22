package com.tzCloud.core.service;

import com.tzCloud.core.facade.legalEngine.vo.GuidedCase;

import java.util.List;

/**
 * @author xdrodger
 * @Title: AssistantService
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019/6/19 15:54
 */
public interface AssistantService {
    List<String> getHotWords();

    List<GuidedCase> getGuidedCases();
}
