package com.tzCloud.core.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.model.perceptron.PerceptronNERecognizer;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.tzCloud.arch.common.PageInfoResp;
import com.tzCloud.core.dao.caseMatching.CaseHtmlAnalysisDao;
import com.tzCloud.core.dao.caseMatching.CaseHtmlDataDao;
import com.tzCloud.core.hanLP.Config;
import com.tzCloud.core.hanLP.HanLPFactory;
import com.tzCloud.core.hanLP.TextSegment;
import com.tzCloud.core.model.caseMatching.CaseHtmlAnalysis;
import com.tzCloud.core.model.caseMatching.CaseHtmlData;
import com.tzCloud.core.service.CaseHtmlAnalysisService;
import com.tzCloud.core.utils.WenshuHtmlSaveUntil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xiaolei
 * @create 2019-03-04 19:22
 */
@Service
@Slf4j
public class CaseHtmlAnalysisImpl implements CaseHtmlAnalysisService {

    @Autowired
    private CaseHtmlDataDao caseHtmlDataDao;
    @Autowired
    private CaseHtmlAnalysisDao caseHtmlAnalysisDao;
    private WenshuHtmlSaveUntil wenshuHtmlSaveUntil = new WenshuHtmlSaveUntil();

    @Override
    public void analysisFull()
    {
        int pageNum = 1, pageSize = 1000;
        try {
            analysisFind(pageNum, pageSize, true, Config.UPDATE.FULL.name());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void analysisIncrement() {
        int pageNum = 1, pageSize = 1000;
        try {
            analysisFind(pageNum, pageSize, true, Config.UPDATE.INCREMENT.name());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PageInfoResp analysisFull(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CaseHtmlData> byLimit = caseHtmlDataDao.findByLimit();
        PageInfo<CaseHtmlData> pageInfo = new PageInfo<>(byLimit);
        try {
            insertOrUpdateAnalysis(pageInfo.getList());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return new PageInfoResp(pageInfo);
    }

    @Override
    public PageInfoResp analysisIncrement(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CaseHtmlData> byLimit = caseHtmlDataDao.findByNewForAnalysis();
        PageInfo<CaseHtmlData> pageInfo = new PageInfo<>(byLimit);
        try {
            insertOrUpdateAnalysis(pageInfo.getList());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return new PageInfoResp(pageInfo);
    }


    private void analysisFind(int pageNum, int pageSize, boolean isHasNextPage, String type) throws IOException
    {
        if (isHasNextPage)
        {
            PageHelper.startPage(pageNum, pageSize);
            List<CaseHtmlData> byLimit;
            if (Config.UPDATE.FULL.name().equals(type))
                byLimit = caseHtmlDataDao.findByLimit();
            else
                byLimit = caseHtmlDataDao.findByNewForAnalysis();
            PageInfo<CaseHtmlData> pageInfo = new PageInfo<>(byLimit);
            insertOrUpdateAnalysis(pageInfo.getList());
            analysisFind(pageInfo.getNextPage(), pageSize, pageInfo.isHasNextPage(), type);
        }
    }

    private void insertOrUpdateAnalysis(List<CaseHtmlData> byLimit) throws IOException
    {
        PerceptronNERecognizer recognizer = new PerceptronNERecognizer(Config.Model.NER);
        Segment segment = HanLPFactory.builder().segment(true);
        List<CaseHtmlAnalysis> list = new LinkedList<>();
        for (CaseHtmlData object : byLimit)
        {
            CaseHtmlAnalysis analysis1 = analysis(object, recognizer, segment);
            if (analysis1 != null)
            {
                list.add(analysis1);
            }

            if (list.size() >= 500)
            {
                try
                {
                    caseHtmlAnalysisDao.insertOrUpdateAnalysisList(list);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }

                list.clear();
            }
        }
        if (list.size() > 0)
        {
            try
            {
                caseHtmlAnalysisDao.insertOrUpdateAnalysisList(list);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public CaseHtmlAnalysis analysis(CaseHtmlData object, PerceptronNERecognizer recognizer, Segment segment)
    {
        CaseHtmlAnalysis analysis;
        StringBuilder defendant,prosecutor,prosecutorLawyer,defendantLawyer;
        String docId = object.getDocId();
        String html  = object.getHtml();
        String parse ;
        try
        {
            parse = WenshuHtmlSaveUntil.parseDocJson5(html, segment);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

//        Map<String, String> resetWordMap = new HashMap<>();
        if (StringUtils.isNotBlank(parse))
        {
            defendant        = new StringBuilder();
            prosecutor       = new StringBuilder();
            prosecutorLawyer = new StringBuilder();
            defendantLawyer  = new StringBuilder();
            analysis         = new CaseHtmlAnalysis();
            String[] split = parse.split("。");
            int tag = 0;
            for (int j = 0;j < split.length; j++)
            {
                String s      = split[j];
//                String f1= s.replaceAll("\\(.*?\\)|\\{.*?}|\\[.*?]|（.*?）", "");
                List<Term> seg = segment.seg(s);
                List<String> natureList = seg.stream().map(t -> t.nature.toString()).collect(Collectors.toList());
                List<String> wordList = seg.stream().map(t -> t.word).collect(Collectors.toList());
//                Term identity = TextSegment.getIdentity(segment, s);
                List<String> nameList = new LinkedList<>();
//                String name   = TextSegment.getName(s, recognizer);s
                String identity = WenshuHtmlSaveUntil.segProcessWithoutRestMap(natureList, wordList, nameList, segment);
                if (s.startsWith("住所地")) continue;
                if (identity == null) continue;
                Nature nature = Nature.fromString(identity);
                String name = String.join(";", nameList);
                if (nature.equals(Nature.fromString(Config.IDENTITY.identity_d.name())))
                {
                    tag = -1;
                    if (defendant.toString().length() > 0)
                        defendant.append(";").append(name);
                    else
                        defendant.append(name);
                    continue;
                }

                if (nature.equals(Nature.fromString(Config.IDENTITY.identity_p.name()))
                        || nature.equals(Nature.fromString(Config.IDENTITY.identity_p1.name())))
                {
                    tag = 1;
                    if (prosecutor.toString().length() > 0)
                        prosecutor.append(";").append(name);
                    else
                        prosecutor.append(name);
                    continue;
                }

                if (nature.equals(Nature.fromString(Config.IDENTITY.identity_l.name())))
                {
                    if (s.contains("律师") || s.contains("律师事务所"))
                    {
                        if (tag != -1)
                        {
                            if (!nameExit(prosecutorLawyer.toString(), name))
                            {
                                if (prosecutorLawyer.toString().length() > 0)
                                    prosecutorLawyer.append(";").append(name);
                                else
                                    prosecutorLawyer.append(name);
                            }
                        }
                        else
                        {
                            if (!nameExit(defendantLawyer.toString(), name)) {
                                if (defendantLawyer.toString().length() > 0)
                                    defendantLawyer.append(";").append(name);
                                else
                                    defendantLawyer.append(name);
                            }
                        }
                    }
                }
            }
            analysis.setDocId(docId);
            analysis.setHtml(html);
            analysis.setDefendant(defendant.toString());
            analysis.setProsecutor(prosecutor.toString());
            analysis.setProsecutorLawyer(prosecutorLawyer.toString());
            analysis.setDefendantLawyer(defendantLawyer.toString());
            analysis.setUpdateTime(new Date());
//            wenshuHtmlSaveUntil.resetWord(resetWordMap);
            return analysis;
        }
        return null;
    }

    private boolean nameExit(String nameString, String name)
    {
        if (nameString != null)
        {
            String[] split = nameString.split(";");
            for (String string : split)
            {
                if (string.equals(name))
                {
                    return true;
                }
            }
        }
        return false;
    }
}
