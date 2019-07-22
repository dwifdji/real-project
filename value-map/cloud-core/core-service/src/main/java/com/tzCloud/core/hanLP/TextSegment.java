package com.tzCloud.core.hanLP;

import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.model.perceptron.PerceptronNERecognizer;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.suggest.Suggester;
import com.tzCloud.core.utils.HtmlRegexUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.Map;

/**
 * @author xiaolei
 * @create 2019-03-07 10:14
 * 文本分词
 */
public class TextSegment {

    /**
     * WBSB   首部
     * DSRXX  当事人信息
     * SSJL   审理经过
     * AJJBQK 当事人辩称
     * CPYZ   本院认为
     * PJJG   判决结果
     * WBWB   尾部（审判长信息）
     * JUDGE_DATE  判决日期
     * OTHER  其他
     */
    public enum TAG
    {
        TAG_DSRXX("DSRXX"),
        TAG_WBSB("WBSB"),
        TAG_SSJL("SSJL"),
        TAG_PJJG("PJJG"),
        TAG_AJJBQK("AJJBQK"),
        TAG_CPYZ("CPYZ"),
        TAG_WBWB("WBWB");
        String desc;
        TAG(String desc) {
            this.desc = desc;
        }
    }
    private static final int MAX_LENGTH   = 60;// 干扰句子的长度，有误差  54
    private boolean flag = false;
    private static final Segment segment = HanLPFactory.builder().segment(true);//分词器
    private static final Suggester suggester = HanLPFactory.builder().suggester();

    static
    {
        ahAdd();
    }

    /**
     * 标签+关键字判别
     *
     * 文书结构问题
     * 1.正常的格式  当事人信息都在 DSRXX 标签中
     * 2.当事人信息都在 WBSB 标签中
     * 3.当事人信息都在 SSJL 标签中
     * 4.当事人信息都在 PJJG 标签中
     * 5.当事人信息都在 AJJBQK 标签中
     * 6.当事人信息不在任何标签中（需要知道哪些信息是有用的）
     * 7.存在DSRXX标签，但是数据不在该标签中
     * 8.当事人信息在多个标签中存在
     * 9.存在标签，全部信息都在一个div中（大部分在pjjg中）
     *10.乱码（只见过1份）
     *
     * 文书类型不同问题
     * 7.民事诉讼（存在原告被告）
     * 8.刑事诉讼（存在公诉机关）（公诉机关名称在DSRXX标签）（被告人在PJJG标签中）
     * 9.离婚诉讼
     * 10.错别字纠正
     * 11.没有详情（涉及国家机密、涉及未成年人犯罪）
     * 13.减刑判决
     * 14.案外人
     * 15.复议人
     * 16.债券确认（破产清算）
     * 17.破产清算（可能没有a标签）
     */

    /**
     * 句子预处理：把包含关键信息的句子组合、剔除干扰句子
     *     句子长度、句子关键字
     * 身份匹配：对句子预处理的结果进行身份判断，需要能够区分多种身份和脏数据
     */

    public static TextSegment getInstance() {
        return new TextSegment();
    }

    public static void parseChain(Map<String, String> map, StringBuffer buffer)
    {
        TextSegment instance = getInstance();
        instance.parse(TAG.TAG_DSRXX.desc , map, buffer);
        instance.parse(TAG.TAG_WBSB.desc  , map, buffer);
        instance.parse(TAG.TAG_SSJL.desc  , map, buffer);
        instance.parse(TAG.TAG_AJJBQK.desc, map, buffer);
        instance.parse(TAG.TAG_CPYZ.desc, map, buffer);
        instance.parse(TAG.TAG_PJJG.desc  , map, buffer);
        instance.parse(TAG.TAG_WBWB.desc, map, buffer);
    }

    private void parse(String tag, Map<String, String> map, StringBuffer buffer)
    {
        if (flag) return;
        if (map.containsKey(tag))
        {
            Elements div = Jsoup.parse(map.get(tag)).getElementsByTag("div");
            loopDiv(div, buffer, true);
        }
    }


    public static void parseDsrxx(Elements div, StringBuffer buffer)
    {
        boolean date_flag = false;
        for (Element element : div)
        {
            String s = HtmlRegexUtils.removeAllHtml(element.html());
            String symbol;
            if (s.contains("。")) symbol = s.split("。")[0];
            else  symbol = s;
            String replace = symbol.replaceAll("\\(.*?\\)|\\{.*?}|\\[.*?]|（.*?）", "");
            List<Term> seg = segment.seg(replace);
            if (symbol.length()>10 && ( symbol.contains("一案")
                    || (symbol.contains("判决") )
                    || (symbol.contains("减刑"))
                    || (symbol.contains("诉称")))
                    || symbol.length() > 60)
            {
                break;
            }
            if (date_flag)
            {
                if (symbol.length() > 0 && !symbol.substring(symbol.length()-1).equals("。"))
                {
                    symbol = symbol.concat("。");
                }
                buffer.append(symbol);
                continue;
            }
            if (onlyDsrxx(seg))
            {
                date_flag = true;
                if (symbol.length() > 0 && !symbol.substring(symbol.length()-1).equals("。"))
                {
                    symbol = symbol.concat("。");
                }
                buffer.append(symbol);
            }
//            if (!date_flag)
//            {
//                // 推荐器方法
//                List<String> suggest = suggester.suggest(s, 1);
//                if (suggest.size()>0)
//                {
//                    date_flag = true;
//                }
//            }
//            else
//            {
////                String classify = HanLPFactory.builder().classifier(Config.Model.IDEN_Other).classify(symbol);
//                List<Term> seg = segment.seg(symbol);
//
//                if (!onlyOne(seg) || symbol.contains("一案") || symbol.contains("判决"))
//                {
//                    break;
//                }
//                if (symbol.length() > 0 && !symbol.substring(symbol.length()-1).equals("。"))
//                {
//                    symbol = symbol.concat("。");
//                }
//                buffer.append(symbol);
//            }
        }
    }

    /*
        存在identity 信息 且 上诉人、被上诉人身份只出现一次
     */
    public static boolean onlyDsrxx(List<Term> seg)
    {
        int count = 0;
        boolean has = false;
        Config.IDENTITY[] values = Config.IDENTITY.values();
        outer:
        for (Config.IDENTITY identity : values)
        {
            String name = identity.name();
            for (Term term : seg)
            {
                if (name.equals(term.nature.toString()))
                {
                    has = true;
                    if (name.equals(Config.IDENTITY.identity_p.name()) || name.equals(Config.IDENTITY.identity_d.name()) )
                    {
                        count++;
                    }
                    continue outer;
                }
            }
        }
        return has && count<2;
    }


    // 只出现一次身份信息
    public static int onlyOne(List<Term> seg)
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
                    continue outer;
                }
            }
        }
        return count;
    }


    private void loopDiv(Elements div, StringBuffer buffer, boolean hasAtag)
    {
        for (Element element : div)
        {
            String s = HtmlRegexUtils.removeAllHtml(element.html());
            if (s.contains("。"))
            {
                String[] split = s.split("。");
                for (String str : split)
                {
                    if (str.length() >= MAX_LENGTH)
                    {
                        if (hasAtag) this.flag = true;
                        return;
                    }
                    str = str.concat("。");
                    keyWordDetect(str, buffer);
                }
            }
            else
            {
                if (s.length() >= MAX_LENGTH)
                {
                    if (hasAtag) flag = true;
                    return;
                }
                if (s.length() > 0 && !s.substring(s.length()-1).equals("。"))
                {
                    s = s.concat("。");
                }
                keyWordDetect(s, buffer);
            }
        }
    }

    public static void parseWithoutAtag(Document parse, StringBuffer buffer)
    {
        TextSegment instance = getInstance();
        Elements div = parse.getElementsByTag("div");
        instance.loopDiv(div, buffer, false);
    }

    private static void keyWordDetect(String s, StringBuffer buffer)
    {
        if (s.contains("律师") || s.contains("上诉") || s.contains("被告")
                || s.contains("原告") || s.contains("代表人") || s.contains("责任人")
                || s.contains("申请人") || s.contains("执行人") || s.contains("起诉人")
                || s.contains("代理人") || s.contains("罪犯") || s.contains("公诉")
                || s.contains("案外人") || s.contains("复议人"))
        {
            buffer.append(s);
        }
    }

    public static String getName(String str, PerceptronNERecognizer recognizer) {
        String name ;
        List<Term> seg = segment.seg(str);
        String[] nameArr   = new String[seg.size()];
        String[] natureArr = new String[seg.size()];
        // 原告、被告 可能是个人、公司、政府
        if (str.contains("公司")) {
            StringBuilder sb = new StringBuilder();
            String company = getCompany(nameArr, natureArr, recognizer, seg, sb);
            if (!StringUtils.isBlank(company) && company.contains("公司"))
                return company;
        }
        for (Term term : seg) {
            if (term.nature.equals(Nature.nr)) {
                name = term.word;
                return name;
            }
            if (term.nature.equals(Nature.ntc)) {
                name = term.word;
                return name;
            }
            if (term.nature.equals(Nature.nto)) {
                name = term.word;
                return name;
            }
        }
        return null;
    }

    private static String getCompany(String[] nameArr, String[] natureArr, PerceptronNERecognizer recognizer, List<Term> seg, StringBuilder sb) {
        Term term;
        for (int i=0;i<seg.size();i++)
        {
            term         = seg.get(i);
            nameArr[i]   = term.word;
            natureArr[i] = term.nature.toString();
        }
        String[] recognize = recognizer.recognize(nameArr, natureArr);
        String r;
        for (int j=0;j<recognize.length;j++)
        {
            r = recognize[j];
            if (r.startsWith("B-") || r.startsWith("M-")) sb.append(nameArr[j]);
            if (r.startsWith("E-"))
            {
                sb.append(nameArr[j]);
                break;
            }
        }
        return sb.toString();
    }

    public static Term getIdentity(Segment segment, String str)
    {
        List<Term> seg = segment.seg(str);
        // todo 优化
        for (Term term : seg)
        {
            if (term.nature.equals(Nature.fromString(Config.IDENTITY.identity_l.name())))
            {
                return term;
            }
        }
        for (Term term : seg)
        {
            if (term.nature.startsWith("identity_"))
            {
                return term;
            }
        }
        return null;
    }

    public static Term getIdentity(String str)
    {
        List<Term> seg = segment.seg(str);
        // todo 优化
        for (Term term : seg)
        {
            if (term.nature.equals(Nature.fromString(Config.IDENTITY.identity_l.name())))
            {
                return term;
            }
        }
        for (Term term : seg)
        {
            if (term.nature.startsWith("identity_"))
            {
                return term;
            }
        }
        return null;
    }

    public static Term getIdentityDsrxx(String str)
    {
        List<Term> seg = segment.seg(str);
        // todo 优化
        for (Term term : seg)
        {
            if (term.nature.startsWith("identity_p") || term.nature.startsWith("identity_d"))
            {
                return term;
            }
        }
        return null;
    }

    private static void ahAdd() {
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
                                "（2018）沪01执异6号\n"+
                                "（2018）沪72财保116号\n"+
                                "（2018）沪72证保2号\n"+
                                "(2018)沪03行赔终15号\n"
                ).split("\\n");
        for (String title : titleArray)
        {
            suggester.addSentence(title);
        }
    }

}
