package com.tzCloud.core.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tzCloud.core.dao.caseMatching.CaseHtmlDataDao;
import com.tzCloud.core.dao.caseMatching.CaseHtmlDsrxxDao;
import com.tzCloud.core.dao.legalEngine.DsrxxParseStatusDao;
import com.tzCloud.core.service.DsrxxParseStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiaolei
 * @create 2019-06-25 09:47
 */
@Slf4j
@Service
public class DsrxxParseStatusServiceImpl implements DsrxxParseStatusService {

    @Resource
    private CaseHtmlDataDao caseHtmlDataDao;
    @Resource
    private DsrxxParseStatusDao dsrxxParseStatusDao;
    @Resource
    private CaseHtmlDsrxxDao caseHtmlDsrxxDao;

    /**
     *  初始化 文书解析状态
     */
    @Override
    public void initDsrxxParseStatusData() {
        int pageNum = 0, pageSize = 10000;
        // totalSize: 13619542, pages: 1362, pageNum: 1276, pageSize: 10000
        while (true) {
            PageHelper.startPage(pageNum, pageSize);
            List<String> docIds = caseHtmlDataDao.findDocId();
            PageInfo<String> pageInfo = new PageInfo<>(docIds);
            insertDsrxxParseStatus(pageInfo.getList());
            log.info("totalSize: {}, pages: {}, pageNum: {}, pageSize: {}", pageInfo.getTotal(), pageInfo.getPages(), pageNum, pageSize);
            if (!pageInfo.isHasNextPage()) {
                break;
            }
            pageNum++;
        }
    }

    /**
     *  将case_html_dsrxx已更新过的数据 状态更新在 dsrxx_parse_status
     */
    @Override
    public void updateDsrxxParseStatusData() {
        int pageNum = 1, pageSize = 10000;
        while (true) {
            PageHelper.startPage(pageNum, pageSize);
            List<String> docIds = caseHtmlDsrxxDao.findDocId();
            PageInfo<String> pageInfo = new PageInfo<>(docIds);
            updateDsrxxParseStatus(pageInfo.getList());
            if (!pageInfo.isHasNextPage()) {
                break;
            }
            pageNum++;
        }
    }

    private void updateDsrxxParseStatus(List<String> docIds) {
        // 分组进行插入
        int size = docIds.size();
        int pageNum = 0;
        int pageSize = 1000;
        if (size > pageSize) {
            List<String> stringList = docIds.subList(pageNum, pageSize);
            dsrxxParseStatusDao.batchUpdate(stringList);
            updateDsrxxParseStatus(docIds.subList(pageSize, size));
        } else {
            dsrxxParseStatusDao.batchUpdate(docIds);
        }
    }

    private void insertDsrxxParseStatus(List<String> docIds) {
        // 分组进行插入
        int size = docIds.size();
        int pageNum = 0;
        int pageSize = 1000;
        if (size > pageSize) {
            List<String> stringList = docIds.subList(pageNum, pageSize);
            dsrxxParseStatusDao.batchInsert(stringList);
            insertDsrxxParseStatus(docIds.subList(pageSize, size));
        } else {
            dsrxxParseStatusDao.batchInsert(docIds);
        }
    }
    private void insertDsrxxParseStatus2(List<String> docIds) {
        // 分组进行插入
        int size = docIds.size();
        int pageNum = 0;
        int pageSize = 1000;
        if (size > pageSize) {
            List<String> source = docIds.subList(pageNum, pageSize);
            List<String> result = dsrxxParseStatusDao.findDocId(source);
            if (result.size() != source.size()) {
                List<String> collect = source.stream().filter(t -> !result.contains(t)).collect(Collectors.toList());
                if (!collect.isEmpty()) {
                    log.info("发现未插入的docId: {}", collect.toString());
//                dsrxxParseStatusDao.batchInsert(collect);
                }
            }
            insertDsrxxParseStatus2(docIds.subList(pageSize, size));
        } else {
            List<String> result = dsrxxParseStatusDao.findDocId(docIds);
            if (docIds.size() != result.size()) {
                List<String> collect = docIds.stream().filter(t -> !result.contains(t)).collect(Collectors.toList());
                if (!collect.isEmpty()) {
                    log.info("发现未插入的docId: {}", collect.toString());
//                dsrxxParseStatusDao.batchInsert(collect);
                }
            }
        }
    }


}
