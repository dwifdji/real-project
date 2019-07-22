package com._360pai.seimi.service.Impl;

import com._360pai.seimi.dao.CaseHtmlDataDao;
import com._360pai.seimi.model.CaseHtmlData;
import com._360pai.seimi.util.HtmlRegexUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaolei
 * @create 2019-01-29 13:38
 */
@Service
@Slf4j
public class CaseHtmlDataService {
    @Resource
    private CaseHtmlDataDao caseHtmlDataDao;

    List<CaseHtmlData> all;

//    final String filePath = "D:\\code\\gitworkspace\\HanLP\\data\\test\\wenshu\\民事\\";
    final String filePath = "C:\\Users\\360pai\\Desktop\\审判程序";


    public void findCaseHtmlDataByCaseType(int size) {
        this.all = caseHtmlDataDao.findCaseHtmlDataByCaseType(size);
        saveHtmlPJJG(null);
    }

    public void findCaseHtmlDataByTrailRound(String trailRound, int size) {
        this.all = caseHtmlDataDao.findCaseHtmlDataByTrailRound(trailRound, size);
        String tmp = filePath + "\\" + trailRound;
        File file = new File(tmp);
        if (!file.exists()) {
            file.mkdir();
        }
        saveHtmlData(tmp + "\\");
    }


    public void saveHtmlData(String filePath) {
        for (CaseHtmlData data : all)
        {
            if (data.getId() <= 1000 && StringUtils.isNotBlank(data.getHtml()))
            {
                String path = filePath + data.getId() + ".html";
                if (StringUtils.isNotBlank(data.getHtml())) {
                    saveObject(data.getHtml(), path);
                }
            }
        }
    }

    public void saveHtmlPJJG(String filePath)
    {
        for (CaseHtmlData data : all)
        {
            if (data.getId() <= 1000 && StringUtils.isNotBlank(data.getHtml()))
            {
                String path = filePath + data.getId() + ".txt";
                String s = parseDocJson(data.getHtml());
                String[] split = s.split("。");
                StringBuilder sb = new StringBuilder();
                for (String var: split)
                {
                    if (StringUtils.isNotBlank(var)) {
                        if (!var.contains("受理费")
                                && !var.contains("终审判决")
                                && !var.contains("终审裁定")
                                && !var.contains("本裁定"))  {
                            sb.append(var);
                            sb.append("。");
                        }
                    }
                }
                if (StringUtils.isNotBlank(sb)) {
                    saveObject(sb.toString(), path);
                }
            }
        }
    }

    private boolean saveObject(String o, String path)
    {
        try
        {
            OutputStreamWriter fos = new OutputStreamWriter(new FileOutputStream(path));
            fos.write(o);
            fos.close();
        }
        catch (IOException e)
        {
            log.error("在保存对象" + o + "到" + path + "时发生异常" + e);
            return false;
        }

        return true;
    }

    private String parseDocJson(String html)
    {
        String pjjg = "";
        Document parse = Jsoup.parse(html);
        Elements select = parse.select("a[type='dir']");

        Map<String, String> map = new LinkedHashMap<>();

        try {
            croverData(select, map);
        } catch (Exception e) {
            return pjjg;
        }

        for (String key : map.keySet()) {
            String s1 = map.get(key);
            Document parse1 = Jsoup.parse(s1);
            Elements div = parse1.getElementsByTag("div");
            for (Element element : div) {

                if (key.contains("PJJG")) {
                    pjjg += HtmlRegexUtils.removeAllHtml(element.html());
                }
            }
        }

        return pjjg;
    }

    private void croverData(Elements select, Map<String, String> map) {
        for (int i = 0; i < select.size(); i++) {
            Element element = select.get(i).nextElementSibling();
            String dataString;
            dataString = select.get(i).toString() + element.toString();

            boolean a = true;
            if (i == select.size() - 1) {
                if (element.equals(select.get(i))) {
                    a = false;
                }
            } else {
                if (element.equals(select.get(i + 1))) {
                    a = false;
                }
            }

            while (a) {
                element = element.nextElementSibling();

                if (i == select.size() - 1) {
                    //尾部
                    if (element == null || element.equals(select.get(i))) {
                        break;
                    }
                    dataString += element.toString();
                } else {
                    if (element.equals(select.get(i + 1))) {
                        break;
                    }
                    dataString += element.toString();
                }

            }
            map.put(select.get(i).attr("name"), dataString);
        }
    }
}
