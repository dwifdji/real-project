package com.tzCloud.core.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.tzCloud.arch.common.PageInfoResp;
import com.tzCloud.core.dao.caseMatching.CaseHtmlDataDao;
import com.tzCloud.core.dao.caseMatching.CaseHtmlDsrxxDao;
import com.tzCloud.core.dao.legalEngine.DsrxxParseStatusDao;
import com.tzCloud.core.hanLP.Config;
import com.tzCloud.core.hanLP.HanLPFactory;
import com.tzCloud.core.hanLP.TextSegment;
import com.tzCloud.core.model.caseMatching.CaseHtmlData;
import com.tzCloud.core.model.caseMatching.CaseHtmlDsrxx;
import com.tzCloud.core.service.CaseHtmlDsrxxService;
import com.tzCloud.core.utils.WenshuHtmlSaveUntil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author xiaolei
 * @create 2019-03-08 15:01
 */
@Slf4j
@Service
public class CaseHtmlDsrxxServiceImpl implements CaseHtmlDsrxxService
{
    @Autowired
    private CaseHtmlDsrxxDao caseHtmlDsrxxDao;
    @Autowired
    private CaseHtmlDataDao caseHtmlDataDao;
    @Autowired
    private DsrxxParseStatusDao dsrxxParseStatusDao;

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 15, 600,
            TimeUnit.SECONDS, new LinkedBlockingDeque<>(5), new ThreadPoolExecutor.CallerRunsPolicy());

    @Override
    public void parseDsrxxFull()
    {
        int pageNum=1, pageSize=1000;
        try {
            dsrxxFind(pageNum, pageSize, true, Config.UPDATE.FULL.name());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dsrxxFind(int pageNum, int pageSize, boolean isHasNextPage, String type) throws IOException
    {
        if (isHasNextPage)
        {
            PageHelper.startPage(pageNum, pageSize);
            List<CaseHtmlData> byLimit;
            if (Config.UPDATE.FULL.name().equals(type))
                byLimit = caseHtmlDataDao.findByLimit();
            else
                byLimit = caseHtmlDataDao.findByNewForDsrxx();
            PageInfo<CaseHtmlData> pageInfo = new PageInfo<>(byLimit);
            insertDsrxx(pageInfo.getList());
            dsrxxFind(pageInfo.getNextPage(), pageSize, pageInfo.isHasNextPage(), type);
        }
    }


    private void insertDsrxx(List<CaseHtmlData> byLimit)
    {
        LinkedList<String> parsed      = new LinkedList<>();
        LinkedList<String> unParsed    = new LinkedList<>();
        LinkedList<String> errorParsed = new LinkedList<>();
        insertDsrxx(byLimit, parsed, unParsed, errorParsed);
    }


    private void insertDsrxx(List<CaseHtmlData> byLimit,
                             List<String> parsed,
                             List<String> unParsed,
                             List<String> errorParsed)
    {
        List<CaseHtmlDsrxx> list = new LinkedList<>();
        Segment segment = HanLPFactory.builder().segment(true);
        for (CaseHtmlData object : byLimit)
        {
            String docId = object.getDocId();
            // TODO: 2019/3/12 名字过长
//            if (docId.equals("ba05c06a-c7bd-4477-863c-b8e823d97ee9")) continue;
            String html = object.getHtml();
            if (html.contains("互联网公布")
                    || html.contains("离婚诉讼或者涉及未成年子女抚养、监护的")
                    || html.contains("未成年人犯罪的")
                    || html.contains("涉及国家")
                    || html.contains("笔误")
                    || html.contains("错误字句")
                    || html.contains("以调解方式结案")
//                    || html.contains("复议人")
//                    || html.contains("减刑")
//                    || html.contains("案外人")
//                    || html.contains("罪犯")
            ) {
                unParsed.add(docId);
                continue;
            }
            String parse = WenshuHtmlSaveUntil.parseDocJson5(html, segment);
//            Map<String, String> resetWordMap = new HashMap<>();
            if (StringUtils.isNotBlank(parse))
            {
                parseDsrxx(parse, segment, docId, list, unParsed);
            } else {
                unParsed.add(docId);
            }
            if (list.size()>1000)
            {
                batchInsertDsrxx(list, parsed, errorParsed);
                list.clear();
            }
//            wenshuHtmlSaveUntil.resetWord(resetWordMap);
        }
        if (!list.isEmpty())
        {
            batchInsertDsrxx(list,parsed, errorParsed);
        }
    }

    private void updateDsrxx(List<CaseHtmlData> htmlByDocIds,
                            List<String> parsed,
                            List<String> unParsed,
                            List<String> errorParsed) {
        List<CaseHtmlDsrxx> list = new LinkedList<>();
        Segment segment = HanLPFactory.builder().segment(true);
        for (CaseHtmlData data : htmlByDocIds) {
            String docId = data.getDocId();
            String html = data.getHtml();
            if (html.contains("互联网公布")
                    || html.contains("离婚诉讼或者涉及未成年子女抚养、监护的")
                    || html.contains("未成年人犯罪的")
                    || html.contains("涉及国家")
                    || html.contains("笔误")
                    || html.contains("错误字句")
                    || html.contains("以调解方式结案")
//                    || html.contains("复议人")
//                    || html.contains("减刑")
//                    || html.contains("案外人")
//                    || html.contains("罪犯")
            ) {
                continue;
            }
            String parse = WenshuHtmlSaveUntil.parseDocJson5(html, segment);
            if (StringUtils.isNotBlank(parse))
            {
                parseDsrxx(parse, segment, docId, list, unParsed);
            }
//            caseHtmlDsrxxDao.deleteById(id.intValue());
            if (list.size()>1000)
            {
                batchInsertDsrxx(list, parsed, errorParsed);
                list.clear();
            }
        }
        if (!list.isEmpty())
        {
            batchInsertDsrxx(list,parsed, errorParsed);
        }
    }

    private void parseDsrxx(String parse,Segment segment, String docId, List<CaseHtmlDsrxx> list, List<String> unParsed) {
        String[] split = parse.split("。");
        boolean flag = false;
        CaseHtmlDsrxx dsrxx;
        for (int j = 0;j < split.length; j++)
        {
            dsrxx = new CaseHtmlDsrxx();
            String s = split[j];
            List<Term> seg = segment.seg(s);
            List<String> natureList = seg.stream().map(t -> t.nature.toString()).collect(Collectors.toList());
            List<String> wordList   = seg.stream().map(t -> t.word).collect(Collectors.toList());
            List<String> nameList   = new LinkedList<>();
            WenshuHtmlSaveUntil.segProcessWithoutRestMap(natureList, wordList, nameList, segment);
            String name     = String.join(";",nameList);
            Term   identity = TextSegment.getIdentityDsrxx(s);
            String detail   = s.substring(s.indexOf(name)+name.length());
            if (detail.startsWith("，"))
                detail = detail.substring(1);
            if (detail.length() > 170)
                detail = detail.substring(0, 170);
            if (name.length() > 15)
                continue;
            if (identity != null)
            {
                dsrxx.setDocId(docId);
                dsrxx.setName(name);
                dsrxx.setDetail(detail);
                dsrxx.setIdentity(identity.word);
                dsrxx.setCreateTime(new Date());
                dsrxx.setUpdateTime(new Date());
                list.add(dsrxx);
                flag = true;
            }
        }
        if (!flag){
            unParsed.add(docId);
        }
    }

    private void batchInsertDsrxx(List<CaseHtmlDsrxx> list, List<String> parsed, List<String> errorParsed) {
        List<String> collect = list.stream().map(CaseHtmlDsrxx::getDocId).collect(Collectors.toList());
        try {
            caseHtmlDsrxxDao.batchInsert(list);
            parsed.addAll(collect);
        } catch (Exception e) {
            // TODO: 2019/6/26 list 处理
            errorParsed.addAll(collect);
            e.printStackTrace();
        }
    }

    @Override
    public void parseDsrxxIncrement() {
        int pageNum=1, pageSize=1000;
        try {
            dsrxxFind(pageNum, pageSize, true, Config.UPDATE.INCREMENT.name());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PageInfoResp parseDsrxxIncrement(int pageNum, int pageSize)
    {
        PageHelper.startPage(pageNum, pageSize);
        List<CaseHtmlData> byLimit = caseHtmlDataDao.findByNewForDsrxx();
        PageInfo<CaseHtmlData> pageInfo = new PageInfo<>(byLimit);
        try {
            insertDsrxx(pageInfo.getList());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new PageInfoResp(pageInfo);
    }

    @Override
    public void parseDsrxxIncrementNew() {
        int pageNum = 0, pageSize = 10000, count = 0;
        while (true) {
            count++;
            PageHelper.startPage(pageNum, pageSize);
            List<String> noParseDocId = dsrxxParseStatusDao.findNoParseDocId();
            PageInfo<String> pageInfo = new PageInfo<>(noParseDocId);
            log.info("totalSize: {}, count: {}, pageSize: {}", pageInfo.getTotal(), count, pageSize);
            // 查询 新增 更新
            queryHtmlByDocId(pageInfo.getList());
            if (!pageInfo.isHasNextPage()) {
                break;
            }
        }
    }

    @Override
    public void parseDsrxxIncrementThread() {
        int pageNum = 0, pageSize = 10000, count = 0;
        CountDownLatch countDownLatch = new CountDownLatch(2000);
        while (true) {
            PageHelper.startPage(pageNum, pageSize);
            List<String> noParseDocId = dsrxxParseStatusDao.findNoParseDocId();
            PageInfo<String> pageInfo = new PageInfo<>(noParseDocId);
            log.info("totalSize: {}, count: {}, pageSize: {}", pageInfo.getTotal(), count, pageSize);
            List<String> list = pageInfo.getList();
            dsrxxParseStatusDao.batchUpdate(list, 4);
            // 查询 新增 更新
            threadPoolExecutor.submit(new dsrxxProcessRunnable(list,countDownLatch));
            if (!pageInfo.isHasNextPage()) {
                break;
            }
            count++;
        }
        log.info("开始调用 shutdown");
        threadPoolExecutor.shutdown();
        try {
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("主线程结束");
    }



    class dsrxxProcessRunnable implements Runnable {

        private List<String> docIdList;
        private CountDownLatch countDownLatch;

        public dsrxxProcessRunnable(List<String> docIdList, CountDownLatch countDownLatch) {
            this.docIdList = docIdList;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            if (!docIdList.isEmpty())
                queryHtmlByDocId(docIdList);
            countDownLatch.countDown();
        }

    }

    @Override
    public void parseDsrxxUpdate() {
        int pageNum = 0, pageSize = 10000, count = 0;
        List<String> list = new LinkedList<>();
        while (true) {
            count++;
            PageHelper.startPage(pageNum, pageSize);
            List<CaseHtmlDsrxx> unusualList = caseHtmlDsrxxDao.findUnusualList();
            PageInfo<CaseHtmlDsrxx> pageInfo = new PageInfo<>(unusualList);
            log.info("totalSize: {}, count: {}, pageSize: {}", pageInfo.getTotal(), count, pageSize);
            // 查询 新增 更新
            updateUnusualList(pageInfo.getList(), list);
            if (!pageInfo.isHasNextPage()) {
                break;
            }
            pageNum++;
        }
    }

    private void updateUnusualList(List<CaseHtmlDsrxx> unusualList, List<String> all){
        int size = unusualList.size();
        int pageNum = 0;
        int pageSize = 1000;
        if (size > pageSize) {
            List<CaseHtmlDsrxx> caseHtmlDsrxxes = unusualList.subList(pageNum, pageSize);
            updateUnusualDsrxx(caseHtmlDsrxxes, all);
            updateUnusualList(unusualList.subList(pageSize, size), all);
        } else {
            updateUnusualDsrxx(unusualList, all);
        }
    }

    private void queryHtmlByDocId(List<String> docIds) {
        int size = docIds.size();
        int pageNum = 0;
        int pageSize = 1000;
        if (size > pageSize) {
            List<String> stringList = docIds.subList(pageNum, pageSize);
            updateDsrxx(stringList);
            queryHtmlByDocId(docIds.subList(pageSize, size));
        } else {
            updateDsrxx(docIds);
        }
    }

    private void updateUnusualDsrxx(List<CaseHtmlDsrxx> unusualList, List<String> all) {
        List<String> docIds = unusualList.stream().map(CaseHtmlDsrxx::getDocId).distinct().collect(Collectors.toList());
        if (!all.isEmpty()) {
            docIds = docIds.stream().filter(t -> !all.contains(t)).collect(Collectors.toList());
        }
        if (!docIds.isEmpty()) {
            List<CaseHtmlData> htmlByDocIds = caseHtmlDataDao.findHtmlByDocIds(docIds);
            all.addAll(docIds);
            LinkedList<String> parsed      = new LinkedList<>();
            LinkedList<String> unParsed    = new LinkedList<>();
            LinkedList<String> errorParsed = new LinkedList<>();
            updateDsrxx(htmlByDocIds,parsed, unParsed,errorParsed);
        }
    }

    private void updateDsrxx(List<String> docIds) {
        List<CaseHtmlData> htmlByDocIds = caseHtmlDataDao.findHtmlByDocIds(docIds);
        LinkedList<String> parsed      = new LinkedList<>();
        LinkedList<String> unParsed    = new LinkedList<>();
        LinkedList<String> errorParsed = new LinkedList<>();
        insertDsrxx(htmlByDocIds,parsed,unParsed,errorParsed);
        if (!parsed.isEmpty()) {
            dsrxxParseStatusDao.batchUpdate(parsed);
        }
        if (!unParsed.isEmpty()) {
            dsrxxParseStatusDao.batchUpdateUnParsed(unParsed);
        }
        if (!errorParsed.isEmpty()) {
            dsrxxParseStatusDao.batchUpdateErrorParsed(errorParsed);
        }
    }

}
