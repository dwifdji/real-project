package com.tzCloud.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tzCloud.arch.common.PageInfoResp;
import com.tzCloud.arch.common.constant.ElasticSearchConstant;
import com.tzCloud.core.condition.legalEngine.TCollectCondition;
import com.tzCloud.core.constant.CaseEnum;
import com.tzCloud.core.dao.lawyerSearch.TParseLawyerInfoDao;
import com.tzCloud.core.dao.legalEngine.TCollectDao;
import com.tzCloud.core.facade.legalEngine.req.CollectReq;
import com.tzCloud.core.facade.legalEngine.resp.DocVO;
import com.tzCloud.core.facade.legalEngine.resp.LawyerInfoVO;
import com.tzCloud.core.facade.legalEngine.resp.LawyerSearchVO;
import com.tzCloud.core.facade.legalEngine.vo.Case;
import com.tzCloud.core.facade.legalEngine.vo.CaseVo;
import com.tzCloud.core.model.lawyerSearch.TParseLawyerInfo;
import com.tzCloud.core.model.legalEngine.TCollect;
import com.tzCloud.core.service.CollectService;
import com.tzCloud.core.utils.AESUtil;
import com.tzCloud.core.utils.RespConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.Assert;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xiaolei
 * @create 2019-04-24 15:16
 */
@Slf4j
@Service
public class CollectServiceImpl implements CollectService {

    @Resource
    private TCollectDao tCollectDao;
    @Resource
    private TParseLawyerInfoDao tParseLawyerInfoDao;
    @Resource
    private RestHighLevelClient client;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Integer collect(CollectReq.Collect req)
    {
        TCollectCondition condition = new TCollectCondition();
        condition.setAccountId(req.getAccountId());
        condition.setCollectType(req.getCollectType());
        condition.setCollectKey(req.getCollectKey());
        List<TCollect> tCollects = tCollectDao.selectList(condition);
        if (CollectionUtils.isNotEmpty(tCollects))
        {
            throw new RuntimeException("您已收藏");
        }
        TCollect collect = new TCollect();
        BeanUtils.copyProperties(condition, collect);
        collect.setCreateTime(new Date());
        collect.setUpdateTime(new Date());
        try
        {
            tCollectDao.insert(collect);
            return collect.getId();
        } catch (Exception e)
        {
            log.info("收藏接口异常，异常信息为：", e);
            throw new RuntimeException();
        }
    }

    @Override
    public void cancelCollect(CollectReq.Collect req)
    {
        TCollectCondition condition = new TCollectCondition();
        condition.setAccountId(req.getAccountId());
        condition.setCollectType(req.getCollectType());
        if (req.getCollectType().equals(CaseEnum.CollectTypeEum.CASE.name())) {
            req.setCollectKey(AESUtil.decrypt(req.getCollectKey()));
        } else {
            condition.setCollectKey(req.getCollectKey());
        }
        List<TCollect> tCollects = tCollectDao.selectList(condition);
        if (tCollects.isEmpty()) return;
        TCollect collect = tCollects.get(0);
//        TCollect collect = tCollectDao.selectById(req.getId());
        if (collect != null) {
            collect.setUpdateTime(new Date());
            collect.setDelFlag(String.valueOf(System.currentTimeMillis()));
            try
            {
                tCollectDao.updateById(collect);
            } catch (Exception e)
            {
                log.info("取消收藏接口异常，异常信息为：", e);
                throw new RuntimeException();
            }
        }
    }

    @Override
    public PageInfoResp getCollectCase(CollectReq.Collect req) {
        // 查询 t_collect
        // 查询 case es
        req.setCollectType(CaseEnum.CollectTypeEum.CASE.name());
        PageInfo<TCollect> pageInfo = getCollectListByAccountId(req);
        List<TCollect> list = pageInfo.getList();
        List<String> docIdList = list.stream().map(TCollect::getCollectKey).collect(Collectors.toList());
        SearchResponse caseByES = getCaseByES(docIdList);
        List<CaseVo> caseVos = new ArrayList<>();
        if (caseByES != null) {
            SearchHits hits = caseByES.getHits();
            Iterator<SearchHit> itr = hits.iterator();
            while (itr.hasNext()) {
                SearchHit hit = itr.next();
                Case _case = JSON.parseObject(JSON.toJSONString(hit.getSourceAsMap()), Case.class);
                CaseVo caseVo = RespConvertUtil.convertToCaseVo(_case);
                // 获取律师信息
//                List<TParseLawyerInfo> lawyerByES = getLawyerByES(_case.getDocId());
//                List<String> lawyerName = lawyerByES.stream().map(TParseLawyerInfo::getLawyerName).collect(Collectors.toList());
//                caseVo.setLawyers(lawyerName);
                caseVos.add(caseVo);
            }
        }
        PageInfoResp<CaseVo> pageInfoResp = new PageInfoResp<>();
        pageInfoResp.setList(caseVos);
        pageInfoResp.setTotal(pageInfo.getTotal());
        pageInfoResp.setHasNextPage(pageInfo.isHasNextPage());
        return pageInfoResp;
    }

    private List<TParseLawyerInfo> getLawyerByES(String docId) {
        if (docId == null) {
            return null;
        }
        // 根据律所名查询律所信息
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(ElasticSearchConstant.LAWYER_INDEX);
        searchRequest.types(ElasticSearchConstant.DEFAULT_TYPE);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("caseBrief.docId", docId));
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            List<TParseLawyerInfo> list = new LinkedList<>();
            SearchHits hits = searchResponse.getHits();
            SearchHit[] hits1 = hits.getHits();
            for (SearchHit hit : hits1) {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                TParseLawyerInfo tParseLawyerInfo = objectMapper.convertValue(sourceAsMap, TParseLawyerInfo.class);
                list.add(tParseLawyerInfo);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private SearchResponse getCaseByES(List<String> docIds) {
        if (docIds.isEmpty()) {
            return null;
        }
        // 根据律所名查询律所信息
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(ElasticSearchConstant.CASE_INDEX);
        searchRequest.types(ElasticSearchConstant.DEFAULT_TYPE);
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        for (String docId : docIds) {
            if (docId == null) continue;
            boolQueryBuilder.should(QueryBuilders.matchQuery("docId",docId));
        }
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.size(docIds.size());
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            return searchResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public LawyerSearchVO getCollectLawyer(CollectReq.Collect req)
    {
        TCollectCondition condition = new TCollectCondition();
        condition.setAccountId(req.getAccountId());
        condition.setCollectType(CaseEnum.CollectTypeEum.LAWYER.name());
        PageHelper.startPage(req.getPage(), req.getPerPage());
        List<TCollect> tCollects = tCollectDao.selectList(condition);
        PageInfo<TCollect> pageInfo = new PageInfo<>(tCollects);
        List<TCollect> list = pageInfo.getList();
        List<Integer> idList = list.stream().map(tc -> Integer.valueOf(tc.getCollectKey())).collect(Collectors.toList());
        List<TParseLawyerInfo> tParseLawyerInfos = tParseLawyerInfoDao.searchByIdList(idList);
        return convertToLawyerSearchVO(tParseLawyerInfos, pageInfo.getTotal(), pageInfo.isHasNextPage());
    }

    @Override
    public PageInfoResp getLawyerByES(CollectReq.Collect req) {
        req.setCollectType(CaseEnum.CollectTypeEum.LAWYER.name());
        PageInfo<TCollect> collectListByAccountId = getCollectListByAccountId(req);
        List<TCollect> list = collectListByAccountId.getList();
        if (list.isEmpty()) return new PageInfoResp();
        // searchRequest  QueryBuilders SearchSourceBuilder client
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(ElasticSearchConstant.LAWYER_INDEX);
        searchRequest.types(ElasticSearchConstant.DEFAULT_TYPE);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for (TCollect tCollect : list) {
            boolQueryBuilder.should(QueryBuilders.matchQuery("id", tCollect.getCollectKey()));
        }
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.size(req.getPerPage());
        searchSourceBuilder.from(req.getPage()-1);
        searchRequest.source(searchSourceBuilder);
        try {
            log.info("index: {}, query: {}",ElasticSearchConstant.LAWYER_INDEX, searchRequest.source().toString());
            SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
            List<LawyerInfoVO> lawyerInfoVOS = convertLawyerInfoVO(search);
            PageInfoResp<LawyerInfoVO> pageInfoResp = new PageInfoResp<>();
            pageInfoResp.setTotal(collectListByAccountId.getTotal());
            pageInfoResp.setList(lawyerInfoVOS);
            return pageInfoResp;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<LawyerInfoVO> convertLawyerInfoVO(SearchResponse search) {
        SearchHits hits = search.getHits();
        List<LawyerInfoVO> resp = new ArrayList<>(((int) (hits.totalHits)));
        for (SearchHit searchHit : hits) {
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            TParseLawyerInfo tParseLawyerInfo = objectMapper.convertValue(sourceAsMap, TParseLawyerInfo.class);
            LawyerInfoVO lawyerInfoVO = new LawyerInfoVO();
            BeanUtils.copyProperties(tParseLawyerInfo, lawyerInfoVO);
            lawyerInfoVO.setLawFirmCity(StringUtils.isBlank(
                    tParseLawyerInfo.getLawFirmCity()) ? tParseLawyerInfo.getLawFirmProvince() : tParseLawyerInfo.getLawFirmCity());
            List<DocVO> caseBrief = tParseLawyerInfo.getCaseBrief();
            lawyerInfoVO.setAttentionFlag("1");
            lawyerInfoVO.setCaseCount(caseBrief.size());
            String[] strings = caseBrief.stream().map(DocVO::getBrief).distinct().toArray(String[]::new);
            lawyerInfoVO.setBriefArray(strings);
            resp.add(lawyerInfoVO);
        }
        return resp;
    }


    @Override
    public boolean isCollect(Integer accountId, String collectKey, String collectType) {
        Assert.notNull(accountId, "accountId 不能为空");
        Assert.notNull(collectKey, "collectKey 不能为空");
        Assert.notNull(collectType, "collectType 不能为空");
        TCollectCondition condition = new TCollectCondition();
        condition.setAccountId(accountId);
        condition.setCollectType(collectType);
        condition.setCollectKey(collectKey);
        List<TCollect> tCollects = tCollectDao.selectList(condition);
        return CollectionUtils.isNotEmpty(tCollects);
    }

    @Override
    public PageInfo<TCollect> getCollectListByAccountId(CollectReq.Collect req) {
        TCollectCondition condition = new TCollectCondition();
        condition.setAccountId(req.getAccountId());
        condition.setCollectType(req.getCollectType());
        PageHelper.startPage(req.getPage(), req.getPerPage());
        List<TCollect> tCollects = tCollectDao.selectList(condition);
        return new PageInfo<>(tCollects);
    }

    private LawyerSearchVO convertToLawyerSearchVO(List<TParseLawyerInfo> tParseLawyerInfos, long total, boolean isHasNextPage)
    {
        LawyerSearchVO lawyerSearchVO = new LawyerSearchVO();
        List<LawyerInfoVO> lawyerInfoVOList = new ArrayList<>();
        Set<String> briefSet = new LinkedHashSet<>();
        List<Integer> yearsList = Arrays.asList(1,2,3,4);
        Map<String, Long> lawFirmProvinceMap = new HashMap<>();
        for (TParseLawyerInfo tParseLawyerInfo : tParseLawyerInfos)
        {
            LawyerInfoVO lawyerInfoVO = new LawyerInfoVO();
            BeanUtils.copyProperties(tParseLawyerInfo, lawyerInfoVO);
            lawyerInfoVO.setAttentionFlag("1");
            if (tParseLawyerInfo.getBrief() != null)
            {
                lawyerInfoVO.setBriefArray(tParseLawyerInfo.getBrief().split(","));
                briefSet.addAll(Arrays.asList(tParseLawyerInfo.getBrief().split(",")));
            }
            lawyerInfoVOList.add(lawyerInfoVO);
            lawFirmProvinceMap.merge(tParseLawyerInfo.getLawFirmProvince(), 1l, (oldValue, newValue) -> lawFirmProvinceMap.get(tParseLawyerInfo.getLawFirmProvince())+1);
        }
        lawyerSearchVO.setLawyerInfoVOList(lawyerInfoVOList);
        lawyerSearchVO.setTotal(total);
        lawyerSearchVO.setHasNextPage(isHasNextPage);
        lawyerSearchVO.setBriefList(briefSet);
        lawyerSearchVO.setLawFirmProvinceMap(lawFirmProvinceMap);
        lawyerSearchVO.setYearsList(yearsList);
        return lawyerSearchVO;
    }
}
