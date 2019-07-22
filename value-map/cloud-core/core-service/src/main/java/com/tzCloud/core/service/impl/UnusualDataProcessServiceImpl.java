package com.tzCloud.core.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.tzCloud.core.condition.lawyerSearch.TParseLawyerInfoCondition;
import com.tzCloud.core.dao.caseMatching.TLawyerDataDao;
import com.tzCloud.core.dao.caseMatching.TLawyerInfoDao;
import com.tzCloud.core.dao.lawyerSearch.TParseLawyerInfoDao;
import com.tzCloud.core.dao.legalEngine.TLawyerFirmInfoDao;
import com.tzCloud.core.hanLP.HanLPFactory;
import com.tzCloud.core.model.caseMatching.TLawyerData;
import com.tzCloud.core.model.caseMatching.TLawyerInfo;
import com.tzCloud.core.model.lawyerSearch.TParseLawyerInfo;
import com.tzCloud.core.model.legalEngine.TLawyerFirmInfo;
import com.tzCloud.core.service.UnusualDataProcessService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xiaolei
 * @create 2019-05-07 09:14d
 */
@Slf4j
@Service
public class UnusualDataProcessServiceImpl implements UnusualDataProcessService {

    @Resource
    private TLawyerDataDao tLawyerDataDao;
    @Resource
    private TParseLawyerInfoDao tParseLawyerInfoDao;
    @Resource
    private TLawyerInfoDao tLawyerInfoDao;
    @Resource
    private TLawyerFirmInfoDao tLawyerFirmInfoDao;

    /*
        处理单条记录中包含多个律师的情况  王洋、邵春杏
        删除原记录
        新生成两条记录
     */
    @Override
    public void unusualLawyerDataProcess1() {
        List<TLawyerData> unusualData1 = tLawyerDataDao.findUnusualData1();
        for (TLawyerData tLawyerData : unusualData1) {
            String lawyer = tLawyerData.getLawyer();
            lawyer = lawyer.replaceAll("\\(.*?\\)|\\{.*?}|\\[.*?]|（.*?）", "");
            int count = 0;
            String[] split ;
            if (lawyer.contains("、")) {
                split = lawyer.split("、");
                for (String string : split) {
                    if (split.length <= 3) {
                        //新增
                        TLawyerData tLawyerData1 = new TLawyerData();
                        BeanUtils.copyProperties(tLawyerData, tLawyerData1);
                        tLawyerData1.setId(null);
                        tLawyerData1.setUpdatedAt(new Date());
                        tLawyerData1.setLawyer(string);
                        tLawyerDataDao.insert(tLawyerData1);
                        count ++;
                    }
                }
                if (count == split.length) {
                    tLawyerDataDao.deleteById(tLawyerData.getId());
                }
            } else {
                if (lawyer.length() <= 3) {
                    //新增
                    TLawyerData tLawyerData1 = new TLawyerData();
                    BeanUtils.copyProperties(tLawyerData, tLawyerData1);
                    tLawyerData1.setId(null);
                    tLawyerData1.setUpdatedAt(new Date());
                    tLawyerData1.setLawyer(lawyer);
                    int insert = tLawyerDataDao.insert(tLawyerData1);
                    if (insert > 0) {
                        tLawyerDataDao.deleteById(tLawyerData.getId());
                    }
                }
            }
        }
    }

    @Override
    public void unusualLawyerDataProcess2() {

        Segment segment = HanLPFactory.builder().segment(true);
//        int pageNum = 1;
//        int pageSize = 5000;
//        while(pageNum < 100) {
//            PageHelper.startPage(pageNum, pageSize);
//            List<TLawyerData> lawyerByLike =    tLawyerDataDao.findLawyerNoLawyerId();
//            PageInfo<TLawyerData> pageInfo = new PageInfo<>(lawyerByLike);
//            updateLawyerIdTest(pageInfo.getList(), segment);
//            if (!pageInfo.isHasNextPage()) {
//                break;
//            }
//            pageNum++ ;
//        }
        List<TLawyerData> lawyerByLike =    tLawyerDataDao.findLawyerNoLawyerId();
        updateLawyerIdTest(lawyerByLike, segment);
    }

    @Override
    public void unusualLawyerDataProcess3() {
        Segment segment = HanLPFactory.builder().segment(true);
/*        int pageNum = 1;// 6
        int pageSize = 5000;
        while(pageNum < 100)
        {
            PageHelper.startPage(pageNum, pageSize);
            List<TLawyerData> lawyerByLike =    tLawyerDataDao.findLawyerNoLawyerId();
            PageInfo<TLawyerData> pageInfo = new PageInfo<>(lawyerByLike);
            searchTest(pageInfo.getList(), segment);
            if (!pageInfo.isHasNextPage())
            {
                break;
            }
            pageNum++ ;
        }*/
        int pageNum=0, pageSize=10000, count=0;
        while (true) {
            PageHelper.startPage(pageNum, pageSize);
            List<TLawyerData> lawyerByLike = tLawyerDataDao.findLawyerNoLawyerId();
            PageInfo<TLawyerData> pageInfo = new PageInfo<>(lawyerByLike);
            searchTest(pageInfo.getList(), segment);
            if (!pageInfo.isHasNextPage()) {
                break;
            }
            log.info("更新no_lawyer_id 数据 totalSize: {}, count:{}", pageInfo.getTotal(), count);
            count++;
            pageNum++;
        }

    }

    @Override
    public void unusualLawyerDataProcess4() {
        Segment segment = HanLPFactory.builder().segment(true);
        List<TParseLawyerInfo> byLawyerFirmISNUll = tParseLawyerInfoDao.selectAll();
        for (TParseLawyerInfo tParseLawyerInfo : byLawyerFirmISNUll) {
            Map<String, String> stringMap = UnusualDataProcessServiceImpl.lawFirmNameProcess(tParseLawyerInfo.getLawFirm(), segment);
            if (!tParseLawyerInfo.getLawFirmShort().equals(stringMap.get("lawFirm"))) {
                String lawFirm = tParseLawyerInfo.getLawFirm();
                String lawyerName = tParseLawyerInfo.getLawyerName();
                Integer years = null;
                String lawFirmId = null;
                List<TLawyerInfo> lawyerByName = tLawyerInfoDao.getLawyerByName(lawyerName, stringMap.get("lawFirm"));
                if (CollectionUtils.isNotEmpty(lawyerByName)) {
                    years = lawyerByName.get(0).getYears();
                    lawFirmId = lawyerByName.get(0).getId().toString();
                }
                tParseLawyerInfo.setLawyerName(lawyerName);
                tParseLawyerInfo.setLawFirm(lawFirm);
                tParseLawyerInfo.setLawFirmShort(stringMap.get("lawFirm"));
                tParseLawyerInfo.setLawFirmCity(stringMap.get("city"));
                tParseLawyerInfo.setLawFirmProvince(stringMap.get("province"));
                tParseLawyerInfo.setYears(years);
                tParseLawyerInfo.setLawyerFirmId(lawFirmId);
                tParseLawyerInfo.setUpdateTime(new Date());
                try {
                    tParseLawyerInfoDao.updateById(tParseLawyerInfo);
                } catch (DuplicateKeyException e) {
                    duplicateKeyProcess(tParseLawyerInfo);
                }
            }
        }
    }

    @Override
    public void unusualLawyerDataProcess5() {
        List<TParseLawyerInfo> byLawyerFirmError1 = tParseLawyerInfoDao.getByLawyerFirmError2();
        Segment segment = HanLPFactory.builder().segment(true);
        for (int i=0,size=byLawyerFirmError1.size();i<size;i++) {
            TParseLawyerInfo tParseLawyerInfo = byLawyerFirmError1.get(i);
            String lawyerName = tParseLawyerInfo.getLawyerName();
            String lawFirm = tParseLawyerInfo.getLawFirm();
            Integer years = null;
            String lawFirmId = null;

            // 姓名异常
            if (lawyerName.startsWith("同")) {
                lawyerName = lawyerName.substring(1);
            }

            // 律所名称
            if (lawFirm.contains("指派的")) {
                lawFirm = lawFirm.substring(lawFirm.indexOf("指派的")+3);
            } else if (lawFirm.contains("指派")) {
                lawFirm = lawFirm.substring(lawFirm.indexOf("指派")+2);
            } else if (lawFirm.contains("指定的")) {
                lawFirm = lawFirm.substring(lawFirm.indexOf("指定的")+3);
            } else if (lawFirm.contains("指定")) {
                lawFirm = lawFirm.substring(lawFirm.indexOf("指定")+2);
            } else if (lawFirm.contains("系")) {
                lawFirm = lawFirm.substring(lawFirm.indexOf("系")+1);
            }

            if (StringUtils.isBlank(lawyerName) || StringUtils.isBlank(lawFirm)) {
                continue;
            }

            Map<String, String> stringMap = UnusualDataProcessServiceImpl.lawFirmNameProcess(lawFirm, segment);
            List<TLawyerInfo> lawyerByName = tLawyerInfoDao.getLawyerByName(lawyerName, stringMap.get("lawFirm"));
            if (CollectionUtils.isNotEmpty(lawyerByName)) {
                years = lawyerByName.get(0).getYears();
                lawFirmId = lawyerByName.get(0).getId().toString();
            }
            tParseLawyerInfo.setLawyerName(lawyerName);
            tParseLawyerInfo.setLawFirm(lawFirm);
            tParseLawyerInfo.setLawFirmShort(stringMap.get("lawFirm"));
            tParseLawyerInfo.setLawFirmCity(stringMap.get("city"));
            tParseLawyerInfo.setLawFirmProvince(stringMap.get("province"));
            tParseLawyerInfo.setYears(years);
            tParseLawyerInfo.setLawyerFirmId(lawFirmId);
            tParseLawyerInfo.setUpdateTime(new Date());
            try {
                tParseLawyerInfoDao.updateById(tParseLawyerInfo);
            } catch (DuplicateKeyException e) {
                duplicateKeyProcess(tParseLawyerInfo);
            }
        }
    }

    @Override
    public void unusualLawyerDataProcess6() {
        Segment segment = HanLPFactory.builder().segment(true);
        List<TParseLawyerInfo> all = tParseLawyerInfoDao.getLawyerByltUpdateTime("2019-05-14 15:30:00");
        for (int i=0,size=all.size();i<size;i++) {
            TParseLawyerInfo tParseLawyerInfo = all.get(i);
            Integer years ;
            Long lawFirmId ;
            String lawFirm = tParseLawyerInfo.getLawFirm();
            String lawyerName = tParseLawyerInfo.getLawyerName();
            Map<String, String> stringMap = UnusualDataProcessServiceImpl.lawFirmNameProcess(lawFirm, segment);
            List<TLawyerInfo> lawyerByName = tLawyerInfoDao.getLawyerByName(lawyerName, stringMap.get("lawFirm"));
            if (CollectionUtils.isEmpty(lawyerByName)) {
                if (tParseLawyerInfo.getYears() != null) {
                    System.out.println("===");
                }
                continue;
            }
            years = lawyerByName.get(0).getYears();
            lawFirmId = lawyerByName.get(0).getLawFirmId();
            if (years != tParseLawyerInfo.getYears()) {
                System.out.println("====");
            }
            tParseLawyerInfo.setYears(years);
            tParseLawyerInfo.setLawyerFirmId(lawFirmId == null? null : lawFirmId.toString());
            tParseLawyerInfo.setUpdateTime(new Date());
            try {
                tParseLawyerInfoDao.updateById(tParseLawyerInfo);
            } catch (DuplicateKeyException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void unusualLawyerDataProcess7() {
        Segment segment = HanLPFactory.builder().segment(true);
        List<TParseLawyerInfo> all = tParseLawyerInfoDao.getLawyerByNoLawFirmId();
        for (int i=0,size=all.size();i<size;i++) {
            TParseLawyerInfo tParseLawyerInfo = all.get(i);
            String lawFirmShort = tParseLawyerInfo.getLawFirmShort();
            String lawFirmProvince = tParseLawyerInfo.getLawFirmProvince();
            List<TLawyerFirmInfo> tLawyerFirmInfos = tLawyerFirmInfoDao.selectByFirmNameLike(lawFirmShort);
            for (TLawyerFirmInfo tLawyerFirmInfo : tLawyerFirmInfos) {
                Map<String, String> stringMap = UnusualDataProcessServiceImpl.lawFirmNameProcess(tLawyerFirmInfo.getFirmName(), segment);
                if (lawFirmShort.equals(stringMap.get("lawFirm")) ) {
                    if (lawFirmProvince.equals(stringMap.get("province"))) {
                        // 更新
                        try {
                            tParseLawyerInfo.setLawyerFirmId(tLawyerFirmInfo.getId().toString());
                            tParseLawyerInfoDao.updateById(tParseLawyerInfo);
                            break;
                        } catch (DuplicateKeyException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void unusualLawyerDataProcess7Page() {
        Segment segment = HanLPFactory.builder().segment(true);
        int pageNum=0,pageSize=10000, count=0;
        while (true) {
            PageHelper.startPage(pageNum, pageSize);
            List<TParseLawyerInfo> all = tParseLawyerInfoDao.getLawyerByNoLawFirmId();
            PageInfo<TParseLawyerInfo> pageInfo = new PageInfo<>(all);
            updateLawFrimId(pageInfo.getList(), segment);
            if (!pageInfo.isHasNextPage()) {
                break;
            }
            log.info("更新law_firm_id pageSize:{}, count:{}", pageInfo.getTotal(), count);
            count++;
        }
    }

    private void updateLawFrimId(List<TParseLawyerInfo> all, Segment segment) {
        for (int i=0,size=all.size();i<size;i++) {
            TParseLawyerInfo tParseLawyerInfo = all.get(i);
            String lawFirmShort = tParseLawyerInfo.getLawFirmShort();
            String lawFirmProvince = tParseLawyerInfo.getLawFirmProvince();
            List<TLawyerFirmInfo> tLawyerFirmInfos = tLawyerFirmInfoDao.selectByFirmNameLike(lawFirmShort);
            for (TLawyerFirmInfo tLawyerFirmInfo : tLawyerFirmInfos) {
                Map<String, String> stringMap = UnusualDataProcessServiceImpl.lawFirmNameProcess(tLawyerFirmInfo.getFirmName(), segment);
                if (lawFirmShort.equals(stringMap.get("lawFirm")) ) {
                    if (lawFirmProvince.equals(stringMap.get("province"))) {
                        // 更新
                        try {
                            tParseLawyerInfo.setLawyerFirmId(tLawyerFirmInfo.getId().toString());
                            tParseLawyerInfoDao.updateById(tParseLawyerInfo);
                            break;
                        } catch (DuplicateKeyException e) {
                            log.error(e.getMessage());
                        }
                    }
                }
            }
        }
    }

    private void duplicateKeyProcess(TParseLawyerInfo tParseLawyerInfo) {
        TParseLawyerInfoCondition condition = new TParseLawyerInfoCondition();
        condition.setLawyerName(tParseLawyerInfo.getLawyerName());
        condition.setLawFirmShort(tParseLawyerInfo.getLawFirmShort());
        condition.setLawFirmProvince(tParseLawyerInfo.getLawFirmProvince());
        List<TParseLawyerInfo> tParseLawyerInfos = tParseLawyerInfoDao.selectList(condition);
        TParseLawyerInfo tParseLawyerInfo1 = tParseLawyerInfos.get(0);
        tLawyerDataDao.updateLawyerIdByLawyerId(tParseLawyerInfo1.getId(), tParseLawyerInfo.getId());
        tParseLawyerInfoDao.deleteById(tParseLawyerInfo1.getId());
        tParseLawyerInfoDao.updateById(tParseLawyerInfo);
    }

    private void searchTest(List<TLawyerData> lawyerDataList, Segment segment) {
        if (!lawyerDataList.isEmpty()) {
            List<TParseLawyerInfo> saveList = new ArrayList<>(5000);
            for (TLawyerData tLawyerData : lawyerDataList) {
                String lawyerName = tLawyerData.getLawyer();
                lawyerName = lawyerName.replaceAll("\\(.*?\\)|\\{.*?}|\\[.*?]|（.*?）", "");
                // 对律师名字过滤
                String[] nameArray = nameProcess(lawyerName);
                if (StringUtils.isBlank(lawyerName) || lawyerName.length() > 3) continue;
                String lawFirm     = tLawyerData.getLawFirm();
                if (lawFirm.contains("指派的")) {
                    lawFirm = lawFirm.substring(lawFirm.indexOf("指派的")+3);
                } else if (lawFirm.contains("指派")) {
                    lawFirm = lawFirm.substring(lawFirm.indexOf("指派")+2);
                } else if (lawFirm.contains("指定的")) {
                    lawFirm = lawFirm.substring(lawFirm.indexOf("指定的")+3);
                } else if (lawFirm.contains("指定")) {
                    lawFirm = lawFirm.substring(lawFirm.indexOf("指定")+2);
                } else if (lawFirm.contains("系")) {
                    lawFirm = lawFirm.substring(lawFirm.indexOf("系")+1);
                }
                Integer years = null;// 可能没有 没有标明省份
                Map<String, String> stringObjectMap = lawFirmNameProcess(lawFirm, segment);
                for (String name : nameArray) {
                    List<TLawyerInfo> lawyerByName = tLawyerInfoDao.getLawyerByName(name, stringObjectMap.get("lawFirm"));
                    if (CollectionUtils.isNotEmpty(lawyerByName)) {
                        years = lawyerByName.get(0).getYears();
                    }
                    // System.out.printf("全称： %s; 城市： %s; 省份： %s; 简称： %s \n", tLawyerData.getLawFirm(), city, province, lawFirm);
                    TParseLawyerInfo info = new TParseLawyerInfo();
                    info.setLawyerName(name);
                    info.setLawFirm(tLawyerData.getLawFirm());
                    info.setLawFirmShort(stringObjectMap.get("lawFirm"));
                    info.setLawFirmCity(stringObjectMap.get("city"));
                    info.setYears(years);
                    info.setLawFirmProvince(stringObjectMap.get("province"));
                    info.setCreateTime(new Date());
                    info.setUpdateTime(new Date());
                    saveList.add(info);
                }
                if (saveList.size() >= 1000)
                {
                    tParseLawyerInfoDao.batchSave(saveList);
                    saveList.clear();
                }
            }

            if (saveList.size()>0)
            {
                tParseLawyerInfoDao.batchSave(saveList);
            }
        }

    }

    public static Map<String, String> lawFirmNameProcess(String lawFirm, Segment segment) {
        String city = ""; // 可能没有 只存在省
        String province = "";// 可能没有 没有标明省份
        StringBuilder nameSB = new StringBuilder();
        Map<String, String> map = new HashMap<>();
        List<Term> seg = segment.seg(lawFirm);
        List<String> natureList = seg.stream().map(t -> t.nature.toString()).collect(Collectors.toList());
        List<String> wordList = seg.stream().map(t -> t.word).collect(Collectors.toList());
        if (natureList.isEmpty()) {
            return map ;
        }
        for (int i = natureList.size() - 1; i >= 0; i--) {
            String nature = natureList.get(i);
            if (nature.startsWith("city_") && StringUtils.isBlank(city)) {
                city = wordList.get(i);
                province = nature.substring(nature.indexOf("_") + 1);
            }
            if (nature.startsWith("province_") && StringUtils.isBlank(city)) {
                province = nature.substring(nature.indexOf("_") + 1);
            }

            if (!nature.startsWith("city_") && !nature.startsWith("province_")) {
                nameSB.insert(0, wordList.get(i));
            }
        }
//        if (province.length() == 0 && city.length() == 0) {
//            return map ;
//        }
        lawFirm = nameSB.toString().replace("（）", "").replace("()", "");
        lawFirm = lawFirm.replace("（)", "").replace("(）", "");
        if (lawFirm.contains("律师事务所")) {
            lawFirm = lawFirm.substring(0, lawFirm.indexOf("律师事务所"));
        } else if (lawFirm.contains("事务所")) {
            lawFirm = lawFirm.substring(0, lawFirm.indexOf("事务所"));
        } else if (lawFirm.contains("律师所事务所")) {
            lawFirm = lawFirm.substring(0, lawFirm.indexOf("律师所事务所"));
        } else if (lawFirm.contains("律师师事务所")) {
            lawFirm = lawFirm.substring(0, lawFirm.indexOf("律师师事务所"));
        }
        map.put("lawFirm", lawFirm);
        map.put("city", city);
        map.put("province", province);
        return map;
    }

    private String[] nameProcess(String lawyerName)
    {
        String[] nameArray;
        if (lawyerName.contains("实习")|| lawyerName.contains("特别授权") || lawyerName.contains("共同") || lawyerName.contains("：") || lawyerName.contains(":"))
            lawyerName = lawyerName.replace("实习","")
                    .replace("特别授权","")
                    .replace("共同","")
                    .replace(":","")
                    .replace("：","");
        lawyerName = lawyerName.replace("（）","").replace("()","");
        if (lawyerName.contains("、"))
        {
            nameArray = lawyerName.split("、");
        }
        else
        {
            nameArray = new String[1];
            nameArray[0] = lawyerName;
        }
        return nameArray;
    }

    /*
        律所名字提取 : 除去 city、province ，如果包含括号而且括号中是地址  则去除括号
        如果包含city 取 最后出现的city
        如果不包含city 判断是否包含 province
        取最后的province
        两者都没有则标识没有标识地点
    */
    private void updateLawyerIdTest(List<TLawyerData> lawyerDataList, Segment segment) {
        if (!lawyerDataList.isEmpty()) {
            for (TLawyerData tLawyerData : lawyerDataList) {
                TParseLawyerInfo tParseLawyerInfo = getLawyerInfoByParse(tLawyerData, segment);
                if (tParseLawyerInfo == null) continue;
                TLawyerData tLawyerData1 = new TLawyerData();
                tLawyerData1.setId(tLawyerData.getId());
                tLawyerData1.setLawyerId(tParseLawyerInfo.getId().intValue());
                tLawyerData1.setUpdatedAt(new Date());
                tLawyerDataDao.updateById(tLawyerData1);
            }
        }
    }


    private TParseLawyerInfo getLawyerInfoByParse(TLawyerData tLawyerData, Segment segment) {
        String lawyerName = tLawyerData.getLawyer();
        String lawFirm    = tLawyerData.getLawFirm();
        String city       = ""; // 可能没有 只存在省
        String province   = "";// 可能没有 没有标明省份
        StringBuilder nameSB = new StringBuilder();
        lawyerName = lawyerName.replaceAll("\\(.*?\\)|\\{.*?}|\\[.*?]|（.*?）", "");
        lawyerName = nameProcess2(lawyerName);
        if (StringUtils.isBlank(lawyerName) || lawyerName.length() > 3) return null;
        // 对律师名字处理
        List<Term> seg = segment.seg(lawFirm);
        List<String> natureList = seg.stream().map(t -> t.nature.toString()).collect(Collectors.toList());
        List<String> wordList = seg.stream().map(t -> t.word).collect(Collectors.toList());
        if (natureList.isEmpty()) {
            System.out.println("数据异常：" + lawFirm);
            return null;
        }
        for (int i = natureList.size() - 1; i >= 0; i--) {
            String nature = natureList.get(i);
            if (nature.startsWith("city_") && StringUtils.isBlank(city)) {
                city = wordList.get(i);
                province = nature.substring(nature.indexOf("_") + 1);
            }
            if (nature.startsWith("province_") && StringUtils.isBlank(city)) {
                province = nature.substring(nature.indexOf("_") + 1);
            }
            if (!nature.startsWith("city_") && !nature.startsWith("province_")) {
                nameSB.insert(0, wordList.get(i));
            }
        }
        if (province.length() == 0 && city.length() == 0) {
            System.out.println("没有城市：" + tLawyerData.getLawFirm());
            return null;
        }
        lawFirm = nameSB.toString().replace("（）", "").replace("()", "");
        if (lawFirm.contains("律师事务所")) {
            lawFirm = lawFirm.substring(0, lawFirm.indexOf("律师事务所"));
        } else if (lawFirm.contains("事务所")) {
            lawFirm = lawFirm.substring(0, lawFirm.indexOf("事务所"));
        }
        // 根据 lawyer, lawFirmShot , province 查出 lawyer_id
        TParseLawyerInfoCondition tParseLawyerInfoCondition = new TParseLawyerInfoCondition();
        tParseLawyerInfoCondition.setLawyerName(lawyerName);
        tParseLawyerInfoCondition.setLawFirmShort(lawFirm);
        tParseLawyerInfoCondition.setLawFirmProvince(province);
        TParseLawyerInfo tParseLawyerInfo = tParseLawyerInfoDao.selectOneResult(tParseLawyerInfoCondition);
        return tParseLawyerInfo;
    }

    private String nameProcess2(String lawyerName)
    {
        if (lawyerName.contains("实习")|| lawyerName.contains("特别授权") || lawyerName.contains("共同") || lawyerName.contains("：") || lawyerName.contains(":"))
            lawyerName = lawyerName.replace("实习","")
                    .replace("特别授权","")
                    .replace("共同","")
                    .replace(":","")
                    .replace("：","");
        lawyerName = lawyerName.replace("（）","").replace("()","");

        return lawyerName;
    }

}
