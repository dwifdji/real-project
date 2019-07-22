package com.tzCloud.test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.io.IOUtil;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.suggest.Suggester;
import com.tzCloud.core.dao.caseMatching.CaseHtmlDataDao;
import com.tzCloud.core.hanLP.Config;
import com.tzCloud.core.hanLP.HanLPFactory;
import com.tzCloud.core.hanLP.ScoreLimitSuggester;
import com.tzCloud.core.hanLP.TextClassify;
import com.tzCloud.core.model.caseMatching.CaseHtmlData;
import com.tzCloud.core.utils.HtmlRegexUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author xiaolei
 * @create 2019-03-18 15:12
 */
public class WenshuTest extends TestBase{

    @Autowired
    private CaseHtmlDataDao caseHtmlDataDao;

    Segment segment = HanLP.newSegment().enableCustomDictionary(true);
    static Suggester suggester = new ScoreLimitSuggester();
    static
    {
        String path = HanLP.Config.CustomDictionaryPath[0];
        HanLP.Config.CustomDictionaryPath = new String[]
                {
                        "D:\\code\\360pai\\branch\\supportcloud\\cloud-core\\core-service\\src\\main\\data\\dictionary\\customize\\customDictionary.txt",
                        "D:\\code\\360pai\\branch\\supportcloud\\cloud-core\\core-service\\src\\main\\data\\dictionary\\customize\\identity.txt",
                        "D:\\code\\360pai\\branch\\supportcloud\\cloud-core\\core-service\\src\\main\\data\\dictionary\\customize\\date.txt date",
                        path
                };
    }

    @Test
    public void otherSave()
    {
        PageHelper.startPage(1, 2000);
        List<CaseHtmlData> htmlData = caseHtmlDataDao.selectAll();
        PageInfo<CaseHtmlData> pageInfo = new PageInfo<>(htmlData);

        List<CaseHtmlData> list = pageInfo.getList();
        for (CaseHtmlData caseHtmlData : list )
        {
            String html = caseHtmlData .getHtml();
            Document parse = Jsoup.parse(html);
            Elements select = parse.select("a[type='dir']");
            Map<String, String> map = new LinkedHashMap<>();
            try {
                croverData(select, map);
            } catch (Exception e) {
                e.printStackTrace();
            }


//            if (map.containsKey("DSRXX"))
//            {
//                Elements div = Jsoup.parse(map.get("DSRXX")).getElementsByTag("div");
//                for (int j=0,size1=div.size();j<size1 ;j++)
//                {
//                    String html1 = div.get(j) .html();
//                    String s;
//                    if (html1.contains("。'")) s = html1.split("。")[0];
//                    else  s = html1;
//                    if (s.length() < 54)
//                    {
//                        List<Term> seg = HanLPFactory.builder().segment(true).seg(s);
//                        if (onlyOne(seg) && !s.contains("诉讼法") && !s.contains("如下"))
//                        {
//                            System.out.println(s);
//                            IOUtil.saveTxt("D:\\code\\gitworkspace\\HanLP\\data\\test\\iden&judge\\iden\\" + caseHtmlData.getId() +"_"+j + ".txt", s);
//                        }
//                    }
//
//                }
//            }

            if (map.size() > 0)
            {
                Set<String> strings = map.keySet();
                for (Iterator<String> iterator = strings.iterator(); iterator.hasNext();)
                {
                    String key = iterator.next();
                    if (!key.equals("DSRXX"))
                    {
                        Elements div = Jsoup.parse(map.get(key)).getElementsByTag("div");

                        for (int j=0,size1=div.size();j<size1 ;j++)
                        {
                            String html1 = div.get(j) .html();
                            String s;
                            if (html1.contains("。'")) s = html1.split("。")[0];
                            else  s = html1;
                            TextClassify builder = TextClassify.builder("D:\\code\\360pai\\branch\\supportcloud\\cloud-core\\core-service\\src\\main\\data\\model\\iden&judge.ser");
                            String classify = builder.classify(s);
                            List<Term> seg = HanLPFactory.builder().segment(true).seg(s);

                            if ((!onlyOne(seg) && classify.equals("judge")) || (onlyOne(seg) && s.contains("撤诉") ))
                            {
                                System.out.println(s);
                                IOUtil.saveTxt("D:\\code\\gitworkspace\\HanLP\\data\\test\\iden&judge\\other\\" + caseHtmlData.getId() +"_"+j + ".txt", s);
                            }
                        }
                    }
                }
            }



        }
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
                    if (element == null ||element.equals(select.get(i + 1))) {
                        break;
                    }
                    dataString += element.toString();
                }

            }
            map.put(select.get(i).attr("name"), dataString);
        }
    }

    @Test
    public void txtClassifyTest()
    {
        List<Term> seg = HanLPFactory.builder().segment(true).seg("徐州市中级人民法院");
        //List<Term> seg = HanLPFactory.builder().segment(true).seg("<中华人民共和国民事诉讼法>");
        System.out.println("||||"+onlyOne(seg));
        System.out.println("--");
    }

    // 只出现一次身份信息
    private boolean onlyOne(List<Term> seg)
    {
        int count = 0;
        Config.IDENTITY[] values = Config.IDENTITY.values();
        outer:
        for (Config.IDENTITY identity : values)
        {
            String name = identity.name();
            for (Term term : seg)
            {
                if (name.equals(term.nature.toString()))
                {
                    count++;
                    break outer;
                }
            }
        }
        return count == 1;
    }


    @Test
    public void suggestTest()
    {
        String[] titleArray =
                (
                        "（2018）沪01民申577号\n" +
                                "（2018）沪01民终9760号\n" +
                                "（2018）沪01民辖终1797号\n"+
                                "（2018）沪01民特582号\n"+
                                "（2018）沪01刑终2002号\n"+
                                "（2017）沪01认台3号之一\n"+
                                "（2018）沪01行初100号\n"+
                                "（2018）沪01行终1253号\n"+
                                "（2018）沪01协外认16号\n"+
                                "（2015）沪一中民保字第7号之一\n"+
                                "（2018）沪01民初937号之三\n"+
                                "（2018）沪01民再77号\n"+
                                "（2018）沪01刑更920号\n"+
                                "（2018）沪02执复23号\n"+
                                "（2014）民一终字第174号\n"+
                                "（2018）沪01民终2868号之一\n"+
                                "（2018）沪01执异6号\n"
                ).split("\\n");
        for (String title : titleArray)
        {
            suggester.addSentence(title);
        }

        System.out.println("|||"+suggester.suggest("（2018）沪01民终9760号", 1));       // 语义
    }

    @Test
    public void set()
    {
        String str = "（2018）沪01民终9971号";
        List<Term> seg = segment.seg(str);
        System.out.println(seg.toString());
    }

    @Test
    public void parseTest()
    {
        String html = "<a type='dir' name='WBSB'></a><div style='TEXT-ALIGN: center; LINE-HEIGHT: 25pt; MARGIN: 0.5pt 0cm; FONT-FAMILY: 宋体; FONT-SIZE: 22pt;'>北京市第二中级人民法院</div><div style='TEXT-ALIGN: center; LINE-HEIGHT: 30pt; MARGIN: 0.5pt 0cm; FONT-FAMILY: 仿宋; FONT-SIZE: 26pt;'>行 政 裁 定 书</div><div style='TEXT-ALIGN: right; LINE-HEIGHT: 30pt; MARGIN: 0.5pt 0cm;  FONT-FAMILY: 仿宋;FONT-SIZE: 16pt; '>（2018）京02行初255号</div><a type='dir' name='DSRXX'></a><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>起诉人李帮君，男，1962年12月29日出生，汉族。</div><a type='dir' name='SSJL'></a><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>起诉人李帮君以中华人民共和国公安部（以下简称公安部）为被告，向本院提起行政诉讼，请求法院判决公安部“不履行法定职责、不作为”违法。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>李帮君起诉称，其于2018年4月7日向公安部邮寄XA31116367413《行政复查申请书》，公安部收到后转北京市公安局处理。北京市公安局于2018年4月28日作出《北京市公安局信访请求的回复》，称“按照相关规定已明确告知你工作意见并多次进行回复，不再重复答复”。李帮君认为，公安部收到其邮寄的《行政复查申请书》后转北京市公安局是不履行法定职责，是不作为的、违法的，给李帮君造成危害后果及经济损失，故起诉至法院，请求法院依法判决。</div><a type='dir' name='CPYZ'></a><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>本院经审查认为：起诉人提起行政诉讼，应当符合法律规定的起诉条件。本案中，李帮君向公安部邮寄《行政复查申请书》，系公民采用书信形式，向行政机关反映情况，提出建议、意见或者投诉请求，依法由行政机关处理的活动，在性质上属于《信访条例》所界定的信访活动的范畴。由此产生的争议，不属于人民法院行政诉讼的受案范围。故李帮君所提“判决公安部不履行法定职责、不作为”的起诉，不符合法定起诉条件，对其起诉，依法应当不予立案。依照《中华人民共和国行政诉讼法》第四十九条第（四）项之规定，裁定如下：</div><a type='dir' name='PJJG'></a><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>对李帮君的起诉，本院不予立案。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>如不服本裁定，可于本裁定书送达之日起十日内，向本院递交上诉状，上诉于北京市高级人民法院。</div><a type='dir' name='WBWB'></a><div style='TEXT-ALIGN: right; LINE-HEIGHT: 25pt; MARGIN: 0.5pt 72pt 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>审 判 长　张昆仑</div><div style='TEXT-ALIGN: right; LINE-HEIGHT: 25pt; MARGIN: 0.5pt 72pt 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>审 判 员　卫　华</div><div style='TEXT-ALIGN: right; LINE-HEIGHT: 25pt; MARGIN: 0.5pt 72pt 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>审 判 员　时　霈</div><br/><div style='TEXT-ALIGN: right; LINE-HEIGHT: 25pt; MARGIN: 0.5pt 72pt 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>二〇一八年六月二十八日</div><div style='TEXT-ALIGN: right; LINE-HEIGHT: 25pt; MARGIN: 0.5pt 72pt 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>法官助理　孙玉宁</div><div style='TEXT-ALIGN: right; LINE-HEIGHT: 25pt; MARGIN: 0.5pt 72pt 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>书 记 员　郝琪琪</div>";
        Document parse = Jsoup.parse(html);
        Elements div = parse.getElementsByTag("div");
        for (Element element : div)
        {
            String str = HtmlRegexUtils.removeAllHtml(element.html());
            if (str.contains("。")) str = str.substring(0, str.indexOf("。"));
            List<Term> seg = segment.seg(str);
            /**
             * 判断每个句的首句，首句无法确定即将当前div归为上个分类,
             * 如果当句的分类与上个div一致则归为上个分类
             * 识别出当事人后，将出现的当事人姓名替换成当事人身份
             */
            System.out.println(seg.toString());
        }
    }
}
