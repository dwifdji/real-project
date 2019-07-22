package com.tzCloud.core.provider.legalEngine;

import com.alibaba.dubbo.config.annotation.Service;
import com.gexin.fastjson.JSON;
import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.arch.common.constant.RedisKeyConstant;
import com.tzCloud.core.constant.AssistantEnum;
import com.tzCloud.core.facade.legalEngine.CaseFacade;
import com.tzCloud.core.facade.legalEngine.req.CaseSearchReq;
import com.tzCloud.core.facade.legalEngine.vo.CaseDetailVo;
import com.tzCloud.core.facade.legalEngine.vo.CaseHtmlDataVo;
import com.tzCloud.core.model.caseMatching.CaseHtmlData;
import com.tzCloud.core.service.AccountSearchRecordService;
import com.tzCloud.core.service.CaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Set;

/**
 * @author xdrodger
 * @Title: CaseProvider
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019-04-19 13:43
 */
@Slf4j
@Service(version = "1.0.0")
@Component
public class CaseProvider implements CaseFacade {

    @Autowired
    private CaseService caseService;
    @Autowired
    private AccountSearchRecordService accountSearchRecordService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private static long MAX_HOT_CASE_SIZE = 10000;

    @Override
    public ResponseModel searchCase(CaseSearchReq.SearchListReq req) {
        ResponseModel resp = caseService.searchCase(req);
        if (req.getLoginId() != null && req.getLoginId().intValue() != -1) {
            accountSearchRecordService.saveSearchRecord(AssistantEnum.SearchType.Case, req.getLoginId(), JSON.toJSONString(req));
        }
        return resp;
    }

    @Override
    public ResponseModel searchCaseSidebar(CaseSearchReq.SearchListReq req) {
        return caseService.searchCaseSidebar(req);
    }

    @Override
    public ResponseModel getCaseDetail(CaseSearchReq.BaseReq req) {
        CaseDetailVo resp;
        String cache = (String) stringRedisTemplate.opsForHash().get(RedisKeyConstant.HOT_CASE_VALUE, req.getDocId());
        stringRedisTemplate.opsForZSet().incrementScore(RedisKeyConstant.HOT_CASE_KEY, req.getDocId(), 1);
        if (StringUtils.isNotBlank(cache)) {
            resp = JSON.parseObject(cache, CaseDetailVo.class);
            long size = stringRedisTemplate.opsForZSet().zCard(RedisKeyConstant.HOT_CASE_KEY);
            if (size > MAX_HOT_CASE_SIZE) {
                Set<String> set = stringRedisTemplate.opsForZSet().range(RedisKeyConstant.HOT_CASE_KEY, 0, 0);
                if (set.size() > 0) {
                    Iterator<String> itr = set.iterator();
                    while (itr.hasNext()) {
                        String docId = itr.next();
                        stringRedisTemplate.opsForZSet().remove(RedisKeyConstant.HOT_CASE_KEY, docId);
                        stringRedisTemplate.opsForHash().delete(RedisKeyConstant.HOT_CASE_VALUE, docId);
                    }
                }
            }
        } else {
            resp = caseService.getCaseDetail(req);
            stringRedisTemplate.opsForHash().put(RedisKeyConstant.HOT_CASE_VALUE, req.getDocId(), JSON.toJSONString(resp));
        }
        return ResponseModel.succ(resp);
    }

    @Override
    public CaseHtmlDataVo getByDocId(String docId) {
        CaseHtmlDataVo vo = new CaseHtmlDataVo();
        CaseHtmlData data = caseService.getHtmlData(docId);
        BeanUtils.copyProperties(data, vo);
        return vo;
    }
}
