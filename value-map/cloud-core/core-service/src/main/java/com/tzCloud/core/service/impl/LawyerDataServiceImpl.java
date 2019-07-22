package com.tzCloud.core.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.tzCloud.arch.common.PageInfoResp;
import com.tzCloud.core.dao.caseMatching.CaseHtmlDataDao;
import com.tzCloud.core.dao.caseMatching.TLawyerDataDao;
import com.tzCloud.core.hanLP.Config;
import com.tzCloud.core.hanLP.HanLPFactory;
import com.tzCloud.core.hanLP.TextClassify;
import com.tzCloud.core.hanLP.TextSegment;
import com.tzCloud.core.model.LawyerInfo;
import com.tzCloud.core.model.caseMatching.CaseHtmlData;
import com.tzCloud.core.model.caseMatching.TLawyerData;
import com.tzCloud.core.service.CaseService;
import com.tzCloud.core.service.LawyerDataService;
import com.tzCloud.core.utils.WenshuHtmlSaveUntil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author xiaolei
 * @create 2019-03-04 19:21
 */
@Service
@Slf4j
public class LawyerDataServiceImpl implements LawyerDataService
{

    @Resource
    private TLawyerDataDao tLawyerDataDao;
    @Resource
    private CaseHtmlDataDao caseHtmlDataDao;
    @Resource
    private CaseService caseService;

    private static final WenshuHtmlSaveUntil wenshuHtmlSaveUntil = new WenshuHtmlSaveUntil();
    static AtomicInteger poolNumber = new AtomicInteger(1);
    private static final ThreadPoolExecutor  win_rate_pool = new ThreadPoolExecutor(2, 2,
            60, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(2), r -> new Thread(r,"win_rate_pool" + poolNumber.getAndIncrement()));


    @Override
    public void winFlagUpdate()
    {
        long begin = System.currentTimeMillis();
        Future<Map<key, LawyerInfo>> lawyerInfo_future     = win_rate_pool.submit(new LawyerInfoTask());
        Future<Map<String, TextPredict>> spcxInfo_future   = win_rate_pool.submit(new SpcxInfoTask());
        Map<key, LawyerInfo> lawyerInfoMap = null;
        Map<String, TextPredict> spcxInfoMap = null;
        try {
            lawyerInfoMap   = lawyerInfo_future.get();
            spcxInfoMap = spcxInfo_future.get();
            System.out.println("数据处理结束: " + (System.currentTimeMillis() - begin) / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        update(lawyerInfoMap, spcxInfoMap);
    }


    private void update(Map<key, LawyerInfo> lawyerInfoMap, Map<String, TextPredict> spcxInfoMap)
    {
        Objects.requireNonNull(lawyerInfoMap);
        Objects.requireNonNull(spcxInfoMap);
        try
        {
            List<Integer> win  = new LinkedList<>();
            List<Integer> lose = new LinkedList<>();
            List<Integer> draw = new LinkedList<>();
            for (Iterator<key> iteratorA = lawyerInfoMap.keySet().iterator(); iteratorA.hasNext();)
            {
                LawyerInfo lawyerInfo = lawyerInfoMap.get(iteratorA.next());
                Map<String, TLawyerData> docIds = lawyerInfo.docIds();
                for (Iterator<String> iteratorB = docIds.keySet().iterator(); iteratorB.hasNext();)
                {
                    String docId = iteratorB.next();
                    TextPredict textPredict = spcxInfoMap.get(docId);
                    TLawyerData lawyerData = docIds.get(docId);
                    String identity = lawyerData.getIdentity();
                    if (textPredict != null)
                    {
                        /**
                         * 不予立案？
                         * 其他：默认  对于上诉人  胜   对于被上诉人  负
                         * 驳回：默认  对于上诉人  负   对于被上诉人  胜
                         * 二审
                         * 撤销： 上诉人胜  被上诉人败
                         * 维持： 上诉人败  被上诉人胜
                         */
                        if (identity == null) continue;
                        if (Config.PJJG_result.解除.name().equals(textPredict.getPredict())
                                || Config.PJJG_result.撤销.name().equals(textPredict.getPredict())
                                || Config.PJJG_result.冻结.name().equals(textPredict.getPredict()))
                        {
                            if (identity.equals(Config.Constant.ACTIVE_LAWYER))
                            {
                                win.add(lawyerData.getId());
                            }
                            else lose.add(lawyerData.getId());
                            continue;
                        }
                        if (Config.PJJG_result.驳回.name().equals(textPredict.getPredict())
                                || Config.PJJG_result.维持.name().equals(textPredict.getPredict())
                                || Config.PJJG_result.不予立案.name().equals(textPredict.getPredict()))
                        {
                            if (identity.equals(Config.Constant.PASSIVE_LAWYER))
                            {
                                win.add(lawyerData.getId());
                            }
                            else lose.add(lawyerData.getId());
                            continue;
                        }
                        if (Config.PJJG_result.移送.name().equals(textPredict.getPredict())
                                || Config.PJJG_result.中止诉讼.name().equals(textPredict.getPredict())
                                || Config.PJJG_result.撤诉.name().equals(textPredict.getPredict())
                                || Config.PJJG_result.并入.name().equals(textPredict.getPredict()))
                        {
                            draw.add(lawyerData.getId());
                            continue;
                        }
                        if (Config.PJJG_result.其他.name().equals(textPredict.getPredict()))
                        {
                            // TODO: 2019/6/13 通过es查询case数据
                            /*
                                Case aCase = caseService.searchESByDocId(textPredict.getDoc_id());
                                String trialRound = aCase.getTrialRound();
                                String valueByKey = CaseEnum.TrialRound.getValueByKey(trialRound);
                             */
                            if (textPredict.getSpcx() != null && textPredict.getSpcx().equals("一审")) {
                                if (identity.equals(Config.Constant.ACTIVE_LAWYER))
                                {
                                    win.add(lawyerData.getId());
                                }
                                else lose.add(lawyerData.getId());
                            }
                            else
                                draw.add(lawyerData.getId());
                        }
                    }
                }
                if (win.size() >= 500)
                {
                    tLawyerDataDao.batchUpdateWinFlag(win, Config.JUDGE_result.win.name());
                    win.clear();
                }
                if (lose.size() >= 500)
                {
                    tLawyerDataDao.batchUpdateWinFlag(lose, Config.JUDGE_result.lose.name());
                    lose.clear();
                }
                if (draw.size() >= 500)
                {
                    tLawyerDataDao.batchUpdateWinFlag(draw, Config.JUDGE_result.draw.name());
                    draw.clear();
                }
            }
            if (!win.isEmpty())  tLawyerDataDao.batchUpdateWinFlag(win,  Config.JUDGE_result.win.name());
            if (!lose.isEmpty()) tLawyerDataDao.batchUpdateWinFlag(lose, Config.JUDGE_result.lose.name());
            if (!draw.isEmpty()) tLawyerDataDao.batchUpdateWinFlag(draw, Config.JUDGE_result.draw.name());
        } catch (Exception e)
        {
            //todo
            e.printStackTrace();
        }
    }

    @Override
    public void winFlagUpdateFull()
    {
        List<TLawyerData> byParam = tLawyerDataDao.findByLawyer("沈强");
//        List<TLawyerData> by = tLawyerDataDao.findBy("fec31c81-66d0-4366-aabf-a84f00bccab6");
        updatePre(byParam);
    }

    @Override
    public void winFlagUpdateIncrement() {
        Map<String, Object> param = new HashMap<>();
        param.put("winFlag", true);
        List<TLawyerData> byParam = tLawyerDataDao.findByParam(param);
        int begin=0;
        System.out.printf("total: %d \n", byParam.size());
        for (int i=0,size=byParam.size();i<size;i++)
        {
            if (i != 0 && i%10000==0)
            {
                System.out.printf("分批处理： begin: %d, end: %d \n", begin, i);
                List<TLawyerData> tLawyerData = byParam.subList(begin, i);
                updatePre(tLawyerData);
                begin = i;
                continue;
            }
            if (i==size-1)
            {
                System.out.printf("最末批次： begin: %d, end: %d \n", begin, i);
                List<TLawyerData> tLawyerData = byParam.subList(begin, i);
                updatePre(tLawyerData);
            }
        }
    }

    @Override
    public void winFlagUpdateIncrementPage() {
        Map<String, Object> param = new HashMap<>();
        param.put("winFlag", true);
        int pageNum = 0, pageSize = 10000, count = 0;
        while (true) {
            PageHelper.startPage(pageNum, pageSize);
            List<TLawyerData> byParam = tLawyerDataDao.findByParam(param);
            PageInfo<TLawyerData> pageInfo = new PageInfo<>(byParam);
            updatePre(pageInfo.getList());
            if (!pageInfo.isHasNextPage()) {
                break;
            }
            log.info("totalSize: {}, count: {}", pageInfo.getTotal(), count);
            count++;
        }
    }

    private void updatePre(List<TLawyerData> byParam)
    {
        Map<key, LawyerInfo> map = new HashMap<>(byParam.size()/2);
        Map<String, TextPredict> container = new ConcurrentHashMap<>(byParam.size());
        lawyerListProcess(byParam, map);
        updatePrePage(byParam, container);
        update(map, container);
    }

    private void updatePrePage(List<TLawyerData> byParam, Map<String, TextPredict> container) {
        int size = byParam.size();
        int pageNum = 0;
        int pageSize = 1000;
        if (size > pageSize) {
            List<TLawyerData> stringList = byParam.subList(pageNum, pageSize);
            List<String> docIds = stringList.stream().map(TLawyerData::getDocid).distinct().collect(Collectors.toList());
            List<CaseHtmlData> byDocIds = caseHtmlDataDao.findByDocIds(docIds);
            caseSpcxProcess(byDocIds, container);
            updatePrePage(byParam.subList(pageSize, size), container);
        } else {
            List<String> docIds = byParam.stream().map(TLawyerData::getDocid).distinct().collect(Collectors.toList());
            List<CaseHtmlData> byDocIds = caseHtmlDataDao.findByDocIds(docIds);
            caseSpcxProcess(byDocIds, container);
        }
    }

    @Override
    public void lawyerIdentityFull()
    {
        int pageNum=1,  pageSize=1000;
        try {
            lawyerIdentityFind(pageNum, pageSize, true, Config.UPDATE.FULL.name());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void lawyerIdentityIncrement() {
        int pageNum=1,  pageSize=1000;
        lawyerIdentityFind(pageNum, pageSize, true,Config.UPDATE.INCREMENT.name());
    }

    @Override
    public PageInfoResp lawyerIdentityIncrement(int pageNum, int pageSize)
    {
        PageHelper.startPage(pageNum, pageSize);
        List<TLawyerData> byLimit = tLawyerDataDao.findNoIdentity2();
        PageInfo<TLawyerData> pageInfo = new PageInfo<>(byLimit);
        List<String> collect = byLimit.stream().map(TLawyerData::getDocid).distinct().collect(Collectors.toList());
        Map<String,String> htmlMap = new HashMap<>(collect.size());
        Map<String, String> stringMap = htmlMap(collect, htmlMap);
        try {
            findAndUpdate(pageInfo.getList(), stringMap);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new PageInfoResp(pageInfo);
    }

    private Map<String, String> htmlMap(List<String> docIds, Map<String, String> htmlMap) {
        int size = docIds.size();
        int pageNum = 0;
        int pageSize = 1000;
        if (size > pageSize) {
            List<String> stringList = docIds.subList(pageNum, pageSize);
            getHtml(stringList, htmlMap);
            htmlMap(docIds.subList(pageSize, size), htmlMap);
        } else {
            getHtml(docIds, htmlMap);
        }
        return htmlMap;
    }


    private void getHtml(List<String> docIds, Map<String, String> htmlMap) {
        List<CaseHtmlData> htmlByDocIds = caseHtmlDataDao.findHtmlByDocIds(docIds);
        Map<String, String> temp = htmlByDocIds.stream().collect(Collectors.toMap(CaseHtmlData::getDocId, CaseHtmlData::getHtml));
        htmlMap.putAll(temp);
    }

    private void lawyerIdentityFind(int pageNum, int pageSize, boolean isHasNextPage, String type)
    {
        if (isHasNextPage)
        {
            PageHelper.startPage(pageNum, pageSize);
            List<TLawyerData> joinHtml;
            if (Config.UPDATE.FULL.name().equals(type))
                joinHtml = tLawyerDataDao.findJoinHtml();
            else
                joinHtml = tLawyerDataDao.findNoIdentity();
            PageInfo<TLawyerData> pageInfo = new PageInfo<>(joinHtml);
//            findAndUpdate(pageInfo.getList());
//            lawyerIdentityFind(pageInfo.getNextPage(), pageSize, pageInfo.isHasNextPage(), type);
        }
    }

    private void findAndUpdate(List<TLawyerData> joinHtml, Map<String, String> stringMap)
    {
        List<Integer> activeLawyerList  = new LinkedList<>();//起诉人代理律师
        List<Integer> passiveLawyerList = new LinkedList<>();//被起诉人代理律师
        Segment segment = HanLPFactory.builder().segment(true);
        for (TLawyerData object : joinHtml)
        {
            String lawyerName = object.getLawyer();
            if (lawyerName.contains("。")) lawyerName = lawyerName.substring(0, lawyerName.indexOf("。"));
//            String html = object.getHtml();
            String html = stringMap.get(object.getDocid());
            if (StringUtils.isNotBlank(lawyerName) && StringUtils.isNotBlank(html))
            {
                String text = WenshuHtmlSaveUntil.parseDocJson5(html, segment);
                if (StringUtils.isNotBlank(text))
                {
                    String[] split = text.split("。");
                    int tag = 0;
                    for (int j = 0, size = split.length; j < size; j++)
                    {
                        String s = split[j];
                        Term identity = TextSegment.getIdentity(s);
                        if (identity == null)
                        {
                            log.info(s + " 未识别出身份");
                            continue;
                        }
                        Nature nature = identity.nature;
                        // 被告
                        if (nature.equals(Nature.fromString(Config.IDENTITY.identity_d.name()))) {
                            tag = -1;
                            continue;
                        }
                        // 原告
                        if (nature.equals(Nature.fromString(Config.IDENTITY.identity_p.name()))) {
                            tag = 1;
                            continue;
                        }
                        // 代理律师
                        if (nature.equals(Nature.fromString(Config.IDENTITY.identity_l.name())))
                        {
                            if (tag != -1)
                            {
                                if (s.contains(lawyerName))
                                {
                                    activeLawyerList.add(object.getId());
                                    if (activeLawyerList.size() >= 500)
                                    {
                                        tLawyerDataDao.batchUpdateIdentity(activeLawyerList, Config.Constant.ACTIVE_LAWYER);
                                        activeLawyerList.clear();
                                    }
                                    break;
                                }
                            }
                            else
                            {
                                if (s.contains(lawyerName))
                                {
                                    passiveLawyerList.add(object.getId());
                                    if (passiveLawyerList.size() >= 500)
                                    {
                                        tLawyerDataDao.batchUpdateIdentity(passiveLawyerList, Config.Constant.PASSIVE_LAWYER);
                                        passiveLawyerList.clear();
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (!activeLawyerList.isEmpty())
            tLawyerDataDao.batchUpdateIdentity(activeLawyerList, Config.Constant.ACTIVE_LAWYER);
        if (!passiveLawyerList.isEmpty())
            tLawyerDataDao.batchUpdateIdentity(passiveLawyerList, Config.Constant.PASSIVE_LAWYER);
    }





    class LawyerInfoTask implements Callable
    {
        @Override
        public Map<key, LawyerInfo> call()
        {
            long begin = System.currentTimeMillis();
            List<TLawyerData> allLawyer = tLawyerDataDao.selectAll();
            System.out.println("律师数据获取完成: " + (System.currentTimeMillis() - begin)/1000);
            Map<key, LawyerInfo> map = new HashMap<>(allLawyer.size()/2);
            lawyerListProcess(allLawyer, map);
            return map;
        }
    }

    public class SpcxInfoTask implements Callable
    {
        @Override
        public Map<String, TextPredict> call()
        {
            return allCaseSPCXInfo();
        }
    }


    public Map<String, TextPredict> allCaseSPCXInfo()
    {
        long begin = System.currentTimeMillis();
        long byNoUpdateCount = caseHtmlDataDao.findBySPCXCount();
        Map<String, TextPredict> container = new ConcurrentHashMap<>((int) byNoUpdateCount);
        int pageNum=0,  pageSize=1000;
        caseSpcxPageFind(pageNum, pageSize, container, true);
        System.out.println("案件数据处理完成，花费时间： " + (System.currentTimeMillis() - begin) / 1000);
        return container;
    }

    // v1
    private void caseSpcxPageFind(int pageNum, int pageSize, Map<String, TextPredict> container, boolean isHasNextPage)
    {
        if (isHasNextPage)
        {
            PageHelper.startPage(pageNum, pageSize);
            List<CaseHtmlData> htmlData = caseHtmlDataDao.findBySPCX();
            PageInfo<CaseHtmlData> pageInfo = new PageInfo<>(htmlData);
            caseSpcxProcess(pageInfo.getList(), container);
            caseSpcxPageFind(pageInfo.getNextPage(), pageSize, container, pageInfo.isHasNextPage());
        }
    }

    private void caseSpcxProcess(List<CaseHtmlData> list, Map<String, TextPredict> container)
    {
        list.parallelStream().forEach(object -> {
            String text   = wenshuHtmlSaveUntil.PJJGExtract(object.getHtml());
            String spcx = (object.getHtml().contains("一审") || object.getHtml().contains("原审")) ? null : "一审";
            if (!container.containsKey(object.getDocId()))
            {
                 container.put(object.getDocId(),
                        new TextPredict(object.getDocId(),
                                text,
                                spcx,
                                predict(text, spcx)));
            }
        });
    }


    @Override
    public void lawyerWinnerRate() {
        long begin = System.currentTimeMillis();
        List<TLawyerData> allLawyer = tLawyerDataDao.selectAll();
        System.out.println("律师数据获取完成: " + (System.currentTimeMillis() - begin)/1000);
        Map<key, LawyerInfo> map = new HashMap<>(allLawyer.size()/2);
        lawyerListProcess(allLawyer, map);
        System.out.println(map.toString());
    }

    private void lawyerListProcess(List<TLawyerData> allLawyer, Map<key, LawyerInfo> map)
    {
        LawyerDataServiceImpl.key key;
        LawyerInfo lawyerInfo;
        for (TLawyerData lawyer : allLawyer)
        {
            if (StringUtils.isBlank(lawyer.getLawyer())) continue;
            key = new key(lawyer.getLawyer(), lawyer.getLawFirm());
            if (map.containsKey(key))
            {
                map.get(key).docAdd(lawyer.getDocid(), lawyer);
            }
            else
            {
                lawyerInfo = LawyerInfo.builder().key(key).docAdd(lawyer.getDocid(), lawyer);
                map.put(key, lawyerInfo);
            }
        }
    }

    // TODO: 2019/3/5
    private String predict(String text, String spcx)
    {
        return TextClassify.builder().keyWordsJudge(text);
//        switch (spcx)
//        {
//            case "一审":
//                return TextClassify.builder(Config.Model.一审_JUDGE).classify(text);
//            case "二审":
//                break;
//            case "再审":
//                break;
//            case "再审审查与审判监督":
//                break;
//            case "其他":
//                break;
//            default:
//                return TextClassify.builder(Config.Model.一审_JUDGE).classify(text);
//        }
//        return "";
    }

    @Data
    public class key
    {
        String lawyer;
        String lawFirm;

        private key(String lawyer, String lawFirm)
        {
            this.lawyer  = lawyer;
            this.lawFirm = lawFirmProcess(lawFirm);
        }

        private String lawFirmProcess(String lawFirm)
        {
            if (StringUtils.isBlank(lawFirm)) return null;
            if (lawFirm.contains("律师事务所")) lawFirm = lawFirm.substring(0, lawFirm.lastIndexOf("律师事务所"));
            lawFirm = lawFirm.replace("(", "（")
                    .replace(")","）");
            return lawFirm;
        }

        private String bracketsSubtring(String left, String right, String lawFirm)
        {
            if (lawFirm.contains(left))
            {
                if (lawFirm.contains(right))
                {
                    String lString = lawFirm.substring(0, lawFirm.indexOf(left));
                    String rString = lawFirm.substring(lawFirm.indexOf(right));
                    lawFirm = lString.concat(rString);
                }
            }
            return lawFirm;
        }


        @Override
        public boolean equals(Object o)
        {
            if (this == o) return true;
            if (!(o instanceof key)) return false;
            key key = (key) o;
            return Objects.equals(lawyer, key.lawyer) &&
                    Objects.equals(lawFirm, key.lawFirm);
        }

        @Override
        public int hashCode()
        {
            return Objects.hash(lawyer, lawFirm);
        }
    }

    @Data
    public class TextPredict
    {
        String doc_id;
        String text;
        String spcx;
        String predict;

        private TextPredict(String doc_id, String text, String spcx, String predict)
        {
            this.doc_id = doc_id;
            this.text = text;
            this.spcx = spcx;
            this.predict = predict;
        }
    }



}
