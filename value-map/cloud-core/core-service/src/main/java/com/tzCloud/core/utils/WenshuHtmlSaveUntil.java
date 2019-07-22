package com.tzCloud.core.utils;

import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.suggest.Suggester;
import com.tzCloud.core.hanLP.Config;
import com.tzCloud.core.hanLP.Container;
import com.tzCloud.core.hanLP.HanLPFactory;
import com.tzCloud.core.hanLP.TextSegment;
import com.tzCloud.core.vo.Dsrxx;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author xiaolei
 * @create 2019-02-20 15:10
 */
@Slf4j
public class WenshuHtmlSaveUntil {

    private static final int MAX_LENGTH   = 75;// 干扰句子的长度
    private static final Segment   segment   = HanLPFactory.builder().segment(true);
    private static final Suggester suggester = HanLPFactory.builder().suggester();

    public void testYiSHEN(List<Object> objects, String filePath)
    {
        long begin = System.currentTimeMillis();
        List<String> 撤诉 = new LinkedList<>();
        List<String> 驳回 = new LinkedList<>();
        List<String> 并案 = new LinkedList<>();
        List<String> 不予立案 = new LinkedList<>();
        List<String> 移送 = new LinkedList<>();
        List<String> 其他 = new LinkedList<>();
        for (int i = 0, size = objects.size(); i < size; i++)
        {
            Object[] oArray =(Object[]) objects.get(i);
            Integer id  = (Integer) oArray[0];
            String html = (String) oArray[1];
            if (StringUtils.isNotBlank(html))
            {

                String s = parseDocJson(html, "PJJG");
                String[] split = s.split("。");
                StringBuilder sb = new StringBuilder();
                textPreProcess(split, sb);
                if (StringUtils.isNotBlank(sb)) {
                    String s1 = sb.toString();
                    String pathTemp ;
                    if (s1.contains("驳回") && s1.length() < 60) {
                        pathTemp = filePath + "驳回\\";
                        filePathProcess(pathTemp);
                        System.out.println("驳回 = " + id + ": " + sb);
                        驳回.add(s1);
                    } else if (s1.contains( "撤回") || s1.contains("撤诉") && s1.length() < 60) {
                        pathTemp = filePath + "撤诉\\";
                        filePathProcess(pathTemp);
                        System.out.println("撤诉 = " + id + ": " + sb);
                        撤诉.add(s1);
                    } else if (s1.contains( "不予立案") || s1.contains( "不予受理")) {
                        pathTemp = filePath + "不予立案\\";
                        filePathProcess(pathTemp);
                        System.out.println("不予立案 = " + id + ": " + sb);
                        不予立案.add(s1);
                    } else if (s1.contains( "法院审理") || s1.contains("移送") && s1.length() < 60) {
                        pathTemp = filePath + "移送\\";
                        filePathProcess(pathTemp);
                        System.out.println("移送 = " + id + ": " + sb);
                        移送.add(s1);
                    } else if (s1.contains( "并入")) {
                        pathTemp = filePath + "并入\\";
                        filePathProcess(pathTemp);
                        System.out.println("并入 = " + id + ": " + sb);
                        并案.add(s1);
                    } else {
                        pathTemp = filePath + "其他\\";
                        filePathProcess(pathTemp);
                        System.out.println("其他 = " + id + ": " + sb);
                        其他.add(s1);
                    }
                    String path = pathTemp + id + ".txt";
                    saveObject(sb.toString(), path);
                }
            }
        }
        System.out.println("驳回 ===============");
        驳回.forEach(t -> System.out.println(t));
        System.out.println("撤诉 ===============");
        撤诉.forEach(t -> System.out.println(t));
        System.out.println("不予立案 ===============");
        不予立案.forEach(t -> System.out.println(t));
        System.out.println("移送 ===============");
        移送.forEach(t -> System.out.println(t));
        System.out.println("并案 ===============");
        并案.forEach(t -> System.out.println(t));
        System.out.println("其他 ===============");
        其他.forEach(t -> System.out.println(t));
        System.out.println( filePath + "数据保存完成, 耗时: " + (System.currentTimeMillis() - begin)/1000 );
    }

    public String PJJGExtract(String html) {
        String s = parseDocJson(html, "PJJG");
        if (StringUtils.isBlank(s) ) s = html;
        if (s.contains("如下")) s = s.substring(s.lastIndexOf("如下")+3);
        String[] split = s.split("。");
        StringBuilder sb = new StringBuilder();
        textPreProcess(split, sb);
        return sb.toString();
    }

    private void filePathProcess(String folder) {
        File file = new File(folder);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public void saveHtmlPJJG(List<Object> objects, String filePath)
    {
        long begin = System.currentTimeMillis();
        for (int i = 0, size = objects.size(); i < size; i++)
        {
            Object[] oArray =(Object[]) objects.get(i);
            Integer id  = (Integer) oArray[0];
            String html = (String) oArray[1];
            if (StringUtils.isNotBlank(html))
            {
                String path = filePath + id + ".txt";
                String s = parseDocJson(html, "PJJG");
                String[] split = s.split("。");
                StringBuilder sb = new StringBuilder();
                textPreProcess(split, sb);
                if (StringUtils.isNotBlank(sb)) {
                    saveObject(sb.toString(), path);
                }
            }
        }
        System.out.println( filePath + "数据保存完成, 耗时: " + (System.currentTimeMillis() - begin)/1000 );
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

    /**
     * 提取当事人信息
     * @param html
     * @return
     */
    public static String parseDocJson5(String html, Segment segment)
    {
        StringBuffer dsrxx = new StringBuffer();
        Document parse = Jsoup.parse(html);
        Elements div = parse.getElementsByTag("div");
//        Map<String, String> identityMap = new HashMap<>();
        Set<Dsrxx> identitySet = new HashSet<>();
        for (Element element : div)
        {
            String symbol = element.html();
            if (symbol.contains("。"))
            {
                String[] split = symbol.split("。");
                for (String sentence : split)
                {
                    segPreProcessWithoutRestMap(sentence, segment, dsrxx, identitySet);
                }
            }
            else
            {
                List<Term> seg = segment.seg(symbol);
                if (!seg.isEmpty() && seg.get(0).nature.equals(Nature.fromString("date"))) break;
                segPreProcessWithoutRestMap(symbol, segment, dsrxx, identitySet);
            }
        }
        return dsrxx.toString();
    }

    // 提取句子
    private static void segPreProcessWithoutRestMap(String sentence, Segment segment, StringBuffer dsrxx, Set<Dsrxx> identitySet)
    {
        sentence = HtmlRegexUtils.removeAllHtml(sentence);
        if (StringUtils.isBlank(sentence)) return;
        // 去除括号内容M
        String f1= sentence.replaceAll("\\(.*?\\)|\\{.*?}|\\[.*?]|（.*?）", "");
        List<Term> seg = segment.seg(f1);
        List<String> natureList = seg.stream().map(t -> t.nature.toString()).collect(Collectors.toList());
        List<String> wordList = seg.stream().map(t -> t.word).collect(Collectors.toList());
        // 处理第一句格式不规范
        if (identitySet.size() == 0
                && ((natureList.contains("identity_p") && !natureList.get(0).equals("identity_p"))
                || ( natureList.contains("identity_d") && !natureList.get(0).equals("identity_d")))){
            for (String str : natureList) {
                if (str.contains("identity_")) {
                    int i = natureList.indexOf(str);
                    int size = seg.size();
                    seg   = seg.subList(i, size);
                    natureList = natureList.subList(i, size);
                    wordList   = wordList.subList(i, size);
                    int index  = sentence.indexOf(wordList.get(0));
                    if (index >= 0) {
                        sentence   = sentence.substring(index);
                    }
                    break;
                }
            }
        }
        if (f1.length() <= MAX_LENGTH
                || natureList.contains("expandKey"))
        {
            stopWordProcess(seg, natureList, wordList, f1, segment);
            boolean b = TextSegment.onlyDsrxx(seg);
            if (((b && seg.get(0).nature.startsWith("identity_")) || f1.contains("共同委托") || f1.contains("二上诉人") || f1.contains("二起诉人") || natureList.contains("identity_l") || f1.contains("原审"))
                    && !natureList.contains("stop")
                    && (natureList.contains("nr") || natureList.contains("ntc")|| natureList.contains("nto")
                    || natureList.contains("companyKey")))
            {
                // TODO: 2019/6/28 提取名字 和 提取词性的接口
                List<String> nameList = new LinkedList<>();
                String identity = segProcessWithoutRestMap(natureList, wordList, nameList, segment);
                if (nameHasExit(identity, nameList, identitySet)) return;
                for (String name : nameList)
                {
//                    identityMap.put(name, identity);
                    identitySet.add(Dsrxx.instance(identity, name));
                }
                if (!nameList.isEmpty())
                {
                    // 后续操作会以句号分隔处理语句
                    if (sentence.length() > 0 && !sentence.substring(sentence.length()-1).equals("。"))
                    {
                        sentence = sentence.concat("。");
                    }
                    dsrxx.append(sentence);
                }
            }
        }
    }

    private static void stopWordProcess(List<Term> seg, List<String> natureList, List<String> wordList, String sentence, Segment segment)
    {
        if (natureList.contains("stop") && wordList.contains("；"))
        {
            String s = sentence.split("；")[0];
            seg.clear();
            natureList.clear();
            wordList.clear();
            seg.addAll(segment.seg(s));
            natureList.addAll(seg.stream().map(t -> t.nature.toString()).collect(Collectors.toList()));
            wordList.addAll(seg.stream().map(t -> t.word).collect(Collectors.toList()));
        }
    }


    public static String segProcessWithoutRestMap(List<String> natureList, List<String> wordList, List<String> nameList, Segment segment)
    {
        boolean start = false;
        String name = "";
        int brackets = 0;
        String identity = "";
        for (int i=0,size=natureList.size();i<size;i++)
        {
            String nature = natureList.get(i);
            String word  = wordList.get(i);
            if (start)
            {
                // 出现逗号结束
                if (nature.equals("comma") && brackets % 2 ==0)
                {
                    if (!name.isEmpty() && notAllow(name, segment)) nameList.add(name);
                    break;
                }
                // 出现冒号  则跳过
                if (nature.equals("colon"))
                {
                    if (!name.isEmpty() && notAllow(name, segment))
                    {
                        nameList.add(name);
                        break;
                    } else continue;
                }
                // 出现顿号  跳过
                if (nature.equals("dun") && brackets % 2 ==0)
                {
                    if (!name.isEmpty() && notAllow(name, segment)) nameList.add(name);
                    name = "";
                    continue;
                }
                // 出现逗号结束
                if (nature.equals("semicolon") && brackets % 2 ==0)
                {
                    if (!name.isEmpty() && notAllow(name, segment)) nameList.add(name);
                    break;
                }
                if (nature.endsWith("_brackets"))
                {
                    ++brackets;
                    if (name.length() > 0 ) {
                        name = name.concat(word);
//                        if (brackets % 2 == 0) {
//                            // 如果括号中只包含身份信息则去掉括号
//                        }
                        continue;
                    }
                    continue;
                }
//                if (nature.contains("identity_")) {
//                    continue;
//                }
                if (brackets % 2 == 0) {
                    name = name.concat(word);
                } else if (name.length() > 0){
                    name = name.concat(word);
                }

            }
            else
            {
                if (natureList.contains(Config.IDENTITY.identity_l.name()))
                {
                    if (nature.equals(Config.IDENTITY.identity_l.name()))
                    {
                        start = true;
                        identity = nature;
                    }
                }
                else if (nature.startsWith("identity_"))
                {
                    start = true;
                    identity = nature;
                }
            }
            if (i == size-1)
            {
                if (!name.isEmpty() && notAllow(name, segment)) nameList.add(name);
            }
        }
        if (nameList.isEmpty() && !name.isEmpty() && notAllow(name, segment)) nameList.add(name);
        return identity;
    }

    public static boolean notAllow(String name, Segment segment)
    {
        // 去除括号
        name= name.replaceAll("\\(.*?\\)|\\{.*?}|\\[.*?]|（.*?）", "");
        List<Term> seg = segment.seg(name);
        List<String> natureList = seg.stream().map(t -> t.nature.toString()).collect(Collectors.toList());
        if (name.length() >= 7 && !name.matches(".*[a-zA-z].*"))
        {
            return (natureList.get(natureList.size()-1).equals("companyKey")
                    || natureList.contains("ntc")
                    || natureList.contains("nto")
                    || (natureList.size()==1 && natureList.get(0).equals("nr")));
        }
        return natureList.stream().noneMatch(t -> t.contains("identity_"));
    }

    /**
     * 提取当事人信息
     * @param html
     * @return
     */
    public String parseDocJson4(String html) {
        StringBuffer pjjg = new StringBuffer();
        if (StringUtils.isBlank(html)) return pjjg.toString();
        Document parse = Jsoup.parse(html);
        Elements div = parse.getElementsByTag("div");
        TextSegment.parseDsrxx(div, pjjg);
        return pjjg.toString();
    }



    /**
     * 提取当事人信息
     * @param html
     * @return
     */
    public String parseDocJson3(String html) {
        StringBuffer pjjg = new StringBuffer();
        if (StringUtils.isBlank(html)) return pjjg.toString();
        Document parse = Jsoup.parse(html);
        Elements select = parse.select("a[type='dir']");
        if (select.size() == 0) {
            TextSegment.parseWithoutAtag(parse, pjjg);
            return pjjg.toString();
        }
        Map<String, String> map = new LinkedHashMap<>();
        try {
            croverData(select, map);
        } catch (Exception e) {
            e.printStackTrace();
            return pjjg.toString();
        }
        TextSegment.parseChain(map, pjjg);
        return pjjg.toString();
    }


    public String parseDocJson2(String html)
    {
        if (StringUtils.isBlank(html)) {
            return "";
        }
        String pjjg = "";
        Document parse = Jsoup.parse(html);
        Elements select = parse.select("a[type='dir']");

        Map<String, String> map = new LinkedHashMap<>();

        try {
            croverData(select, map);
        } catch (Exception e) {
            return pjjg;
        }

        if (map.containsKey("DSRXX")) {
            pjjg = pjjgAppend("DSRXX", pjjg, map);
        }
        else if (map.containsKey("WBSB")) {
            pjjg = pjjgAppend("WBSB", pjjg, map);
            if (!pjjg.contains("原告") && !pjjg.contains("被告") && !pjjg.contains("律师")) {
                if (map.containsKey("SSJL")) {
                    pjjg = pjjgAppend("SSJL", "", map);
                }
            }
        } else if (map.containsKey("SSJL")) {
            pjjg = pjjgAppend("SSJL", pjjg, map);
        }

        return pjjg;
    }

    public String parseDocJson(String html, String key)
    {
        if (StringUtils.isBlank(html))
        {
            return "";
        }
        String pjjg = "";
        Document parse = Jsoup.parse(html);
        Elements select = parse.select("a[type='dir']");

        Map<String, String> map = new LinkedHashMap<>();

        try
        {
            croverData(select, map);

            if (map.containsKey(key))
            {
                pjjg = pjjgAppend2(key, pjjg, map);
            }
            else
            {
                // TODO: 2019/3/15
                if ("PJJG".equals(key))
                {
                    ListIterator<Map.Entry<String,String>> i = new ArrayList<>(map.entrySet()).listIterator(map.size());
                    while(i.hasPrevious())
                    {
                        Map.Entry<String, String> previous = i.previous();
                        String key1 = previous.getKey();
                        String value = previous.getValue();
                        if (value.contains("如下"))
                        {
                            pjjg = pjjgAppend2(key1, pjjg, map);
                            break;
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            System.out.printf("错误文本: %s \n", html);
            e.printStackTrace();
            return pjjg;
        }

        return pjjg;
    }

    private String pjjgAppend2(String key, String pjjg , Map<String, String> map) {
        String s1 = map.get(key);
        Document parse1 = Jsoup.parse(s1);
        Elements div = parse1.getElementsByTag("div");
        for (Element element : div) {
            String s = HtmlRegexUtils.removeAllHtml(element.html());
            if (StringUtils.isNotBlank(s)
                    &&!s.substring(s.length()-1).equals("。")) {
                s += "。";
            }
            pjjg = pjjg.concat(s) ;
        }
        return pjjg;
    }


    private String pjjgAppend(String key, String pjjg , Map<String, String> map) {
        String s1 = map.get(key);
        Document parse1 = Jsoup.parse(s1);
        Elements div = parse1.getElementsByTag("div");
        for (Element element : div) {
            String s = HtmlRegexUtils.removeAllHtml(element.html());
            if (!s.substring(s.length()-1).equals("。")) {
                s += "。";
            }
            if (s.contains("律师") || s.contains("上诉") || s.contains("被告")
                    || s.contains("原告") || s.contains("代表人") || s.contains("责任人")) {
                pjjg = pjjg.concat(s) ;
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
                    if (element == null ||element.equals(select.get(i + 1))) {
                        break;
                    }
                    dataString += element.toString();
                }

            }
            map.put(select.get(i).attr("name"), dataString);
        }
    }


    public static void textPreProcess(String[] split, StringBuilder sb)
    {
        for (String var: split)
        {
            if (StringUtils.isNotBlank(var)) {
                if (!var.contains("受理费")
                        && !var.contains("终审判决")
                        && !var.contains("终审裁定")
                        && !var.contains("本裁定")
                        && !var.contains("递交上诉状")
                        && !var.contains("预交上诉费"))  {
                    sb.append(var);
                    sb.append("。");
                }
            }
        }
    }

    public static String appendDateATag(String html)
    {
        Document parse = Jsoup.parse(html);
        Elements div = parse.getElementsByTag("div");
        ahAdd();
        // 正序设NOT_SHOW
        outer:
        for (int i=0,size=div.size();i <size;i++)
        {
            Element element = div.get(i);
            String s = HtmlRegexUtils.removeAllHtml(element.html());
            // 分词处理方法  需要更新词典
//            List<Term> segment = HanLP.segment(s);
//            for (Term term : segment)
//            {
//                if (term.nature.equals(Nature.fromString("ah")))
//                {
//                    element.after("<a type='dir' name='NOT_SHOW'></a>");
//                    break outer;
//                }
//            }
            // 推荐器方法
            List<String> suggest = suggester.suggest(s, 1);
            if (suggest.size() > 0 && !parse.html().contains("NOT_SHOW"))
            {
                element.after("<a type='dir' name='NOT_SHOW'></a>");
            }
            List<Term> list = segment.seg(s);
            for (Term term : list)
            {
                if (term.nature.equals(Nature.fromString("date")))
                {
                    if (i != div.size()) element.nextElementSibling().before("<a type='dir' name='OTHER'></a>");
                    element.before("<a type='dir' name='JUDGE_DATE'></a>");
                    return parse.html();
                }
            }
        }
        // 反序设JUDGE_DATE和OTHER
//        for (int i=div.size();i >= 1;i--)
//        {
//            Element element = div.get(i-1);
//            String s = HtmlRegexUtils.removeAllHtml(element.html());
//            List<Term> list = segment.seg(s);
//            for (Term term : list)
//            {
//                if (term.nature.equals(Nature.fromString("date")))
//                {
//                    if (i != div.size()) element.nextElementSibling().before("<a type='dir' name='OTHER'></a>");
//                    element.before("<a type='dir' name='JUDGE_DATE'></a>");
//                    return parse.html();
//                }
//            }
//        }
        return html;
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
                                "（2018）沪01执异6号\n"
                ).split("\\n");
        for (String title : titleArray)
        {
            suggester.addSentence(title);
        }
    }


    public static Map<String, List<String>> segParse(String html)
    {
        try {
            Document parse = Jsoup.parse(html);
            Elements div = parse.getElementsByTag("div");
            Segment segment = HanLPFactory.builder().segment(true);
            Map<String, String> resetWordMap = new HashMap<>();
            Container container = new Container();
            putSB(div, container);
            // 身份信息预处理
            putIdentity(div, segment, container, resetWordMap);
            // putContainer
            putContainer(div, segment, container);
            // 词性删除
            resetWord(resetWordMap);
            return container.content();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new LinkedHashMap<>();
    }

    public static void resetWord(Map<String, String> resetWordMap)
    {
        for (String key : resetWordMap.keySet())
        {
            if (resetWordMap.get(key) == null)
                CustomDictionary.remove(key);
            else
                CustomDictionary.insert(key, resetWordMap.get(key));
        }
    }

    // 首部设置
    private static void putSB(Elements div, Container container)
    {
        for (Element element : div )
        {
            if (element.children().size()==0 || element.children().hasText())
            {
                String nospace = StringUtils.deleteWhitespace(element.html());
                if (!container.content().containsKey(Container.name.SB_COURT.name()) && (nospace.endsWith("法院") || nospace.endsWith("委员会")))
                {
                    container.put(Container.name.SB_COURT.name(), element.html());
                    continue;
                }
                if (container.content().containsKey(Container.name.SB_COURT.name())
                        && !container.content().containsKey(Container.name.SB_TYPE.name()) && nospace.endsWith("书"))
                {
                    container.put(Container.name.SB_TYPE.name(), element.html());
                    continue;
                }
                if (container.content().containsKey(Container.name.SB_COURT.name())
                        && container.content().containsKey(Container.name.SB_TYPE.name())
                        && !container.content().containsKey(Container.name.SB_AH.name()) && nospace.endsWith("号"))
                {
                    container.put(Container.name.SB_AH.name(), element.html());
                    break;
                }
            }
        }
    }

    private static void putContainer(Elements div, Segment segment, Container container)
    {
        int currentFlag = 0;
        int nextFlag = 0;
        boolean nextPJJG = false, nextPJJG2 = false;
        boolean date = false;
        List<Term> seg;
        for (int k=div.size()-1;k>2;k--)
        {
            String html = div.get(k).html();
            seg = segment.seg(html);
            List<String> natureList = seg.stream().map(t -> t.nature.toString()).collect(Collectors.toList());
            List<String> wordList = seg.stream().map(t -> t.word).collect(Collectors.toList());
            if (date && natureList.contains("pjjgKey"))
            {
                String pjjgKey = wordList.get(natureList.indexOf("pjjgKey"));
                String pjjg = html.substring(html.lastIndexOf(pjjgKey)+ pjjgKey.length());
                if (StringUtils.deleteWhitespace(pjjg).length()>5)
                {
                    // 应该标识判决结果是否在当前段落
                    currentFlag = k ;
                }
                else
                {
                    // 判决结果在下一段
                    nextFlag = k;
                }
                break;
            }
            if (!date && !seg.isEmpty() && seg.get(0).nature.equals(Nature.fromString("date")))
            {
                date = true;
            }
        }
        for (int i=3,size=div.size();i<size;i++)
        {
            if (i == nextFlag || i == currentFlag)
            {
                nextPJJG = true;
            }
            String html = div.get(i).html();
            if (html.contains("。"))
            {
                // 句号分隔
                String[] split = html.split("。(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                for (String sentence : split)
                {
                    if (nextPJJG)
                    {
                        List<Term> seg1 = segment.seg(sentence);
                        List<String> natureList = seg1.stream().map(t -> t.nature.toString()).collect(Collectors.toList());
                        if (natureList.contains("pjjgKey") && !sentence.contains("原审法院"))
                        {
                            nextPJJG2 = true;
                        }
                    }
                    behaviorProcess(sentence, segment, container, nextPJJG2);
                }
            }
            else
            {
                if (nextPJJG)
                {
                    List<Term> seg1 = segment.seg(html);
                    List<String> natureList = seg1.stream().map(t -> t.nature.toString()).collect(Collectors.toList());
                    if (natureList.contains("pjjgKey") && !html.contains("原审法院"))
                    {
                        nextPJJG2 = true;
                    }
                }
                behaviorProcess(html, segment, container, nextPJJG2);
            }
        }
    }

    private static void behaviorProcess(String sentence, Segment segment, Container container, boolean nextPJJG)
    {
        sentence = HtmlRegexUtils.removeAllHtml(sentence);
        String f1 ;
        if (sentence.contains("，")) f1 = sentence.split("，")[0];
        else f1 = sentence;
        f1 = StringUtils.deleteWhitespace(f1);
        putKeyWordsJudge(f1,segment,sentence,container,nextPJJG);
    }

    private static void putKeyWordsJudge(String f1, Segment segment, String sentence, Container container, boolean nextPJJG)
    {
        if (f1.contains("：")) f1 = f1.substring(0, f1.indexOf("："));
        List<Term> seg = segment.seg(f1);
        List<String> natureList = seg.stream().map(t -> t.nature.toString()).collect(Collectors.toList());
        // 依存句法分析， 需要核心关系是指定动作   指定身份与核心关系直接关联
//        CoNLLSentence coNLLWords = HanLP.parseDependency(f1);
        List<String> natureList2 = segment.seg(sentence).stream().map(t -> t.nature.toString()).collect(Collectors.toList());
        if (sentence.length() > 0 && !sentence.substring(sentence.length()-1).equals("。")
                        && !natureList2.get(natureList2.size()-1).equals("comma")
                        && !natureList2.get(natureList2.size()-1).equals("colon")
                        && !natureList2.get(natureList2.size()-1).equals("w")
                        && !natureList2.get(natureList2.size()-1).equals("dun"))
        {
            sentence = sentence.concat("。");
        }
        // 其他
        if (container.content().containsKey(Container.name.PJSJ.name()))
        {
            container.put(Container.name.OTHER.name(), sentence);
            return;
        }
        // 判决日期
        if (natureList.contains("date") && natureList.size() == 1)
        {
            container.put(Container.name.PJSJ.name(), sentence);
            return;
        }
        // 合议庭
        if (container.content().containsKey(Container.name.PJJG.name())
                && (f1.contains("审判长") || f1.contains("审判员") || f1.contains("执行长") || f1.contains("人民陪审员") || f1.contains("法官助理")))
        {
            container.put(Container.name.HYTXX.name(), sentence);
            return;
        }
        // 审理经过
        if (natureList.contains(Config.IDENTITY.identity_d.name())
                && (f1.contains("公诉机关") || f1.contains("检察院"))
                && ( f1.contains("指控")))
        {
            container.put(Container.name.SSJL.name(), sentence);
            return;
        }
        // 审理经过
        if (natureList.contains(Config.IDENTITY.identity_p.name())
                && (f1.contains("本院"))
                && ( f1.contains("起诉状") || f1.contains("申请") ))
        {
            container.put(Container.name.SSJL.name(), sentence);
            return;
        }
        // 审理经过
        if (natureList.contains(Config.IDENTITY.identity_p.name()) && (sentence.contains("提起上诉")
                || sentence.contains("提起公诉") || sentence.contains("向本院申请") || f1.contains("申请财产保全")))
        {
            container.put(Container.name.SSJL.name(), sentence);
            return;
        }
        // 审理经过
        if (natureList.contains(Config.IDENTITY.identity_d.name())
                && natureList.contains(Config.IDENTITY.identity_p.name())
                && (f1.contains("一案") || f1.contains("案中") || f1.contains("指控") || f1.contains("申请执行") || f1.contains("合同纠纷中")))
        {
            container.put(Container.name.SSJL.name(), sentence);
            return;
        }
        // 上诉人请求
        if (!container.content().containsKey(Container.name.CPYZ.name())
                &&(natureList.contains(Config.IDENTITY.identity_p.name())
                || natureList.contains("abbr_p") )
                && ( f1.contains("诉称") || f1.contains("请求") || f1.contains("辩称") || f1.contains("称"))
                && !f1.contains("请求人")
                && natureList.size() != 1)
        {
            if (f1.contains("一审"))
                container.put(Container.name.YS_YGQQ.name(), sentence);
            else
                container.put(Container.name.SSRBC.name(), sentence);
            return;
        }
        // 被上诉人辩称
        if (!container.content().containsKey(Container.name.CPYZ.name())
                && (natureList.contains(Config.IDENTITY.identity_d.name())
                || natureList.contains("abbr_d") )
                && ( f1.contains("诉称") || f1.contains("请求") || f1.contains("辩称") || f1.contains("称"))
                && !f1.contains("请求人")
                && natureList.size() != 1)
        {
            if (f1.contains("一审"))
                container.put(Container.name.YS_BGBC.name(), sentence);
            else
                container.put(Container.name.BSSRBC.name(), sentence);
            return;
        }

        // 一审认为
        if ( (f1.contains("一审") || f1.contains("原审法院"))
                && ( f1.contains("认为") || sentence.contains("作出判决")))
        {
            container.put(Container.name.YS_RW.name(), sentence);
            return;
        }
        // 一审查明
        if ((f1.contains("一审") || f1.contains("原审法院")) && (f1.contains("审理查明") || f1.contains("查明") || f1.contains("事实")) )
        {
            container.put(Container.name.YS_RDSS.name(), sentence);
            return;
        }
        // 一审认定事实
        if (f1.contains("经审查查明") || f1.contains("审理查明"))
        {
            container.put(Container.name.RDSS.name(), sentence);
            return;
        }
        // 本院事实
        if (f1.contains("本院")
                && ( f1.contains("事实")) || sentence.contains("本院认定事实") || sentence.contains("查实"))
        {
            container.put(Container.name.BYRDSS.name(), sentence);
            return;
        }
        // 本院认为
        if (f1.contains("本院认为") || sentence.contains("本院认为") || f1.contains("本院经审查认为") || f1.contains("本院再审认为"))
        {
            container.put(Container.name.CPYZ.name(), sentence);
            return;
        }
        // 判决结果
        if (nextPJJG)
        {
            container.put(Container.name.PJJG.name(), sentence);
            return;
        }
        if (container.content().size() > 4 && notContainsValue(container, sentence))
        {
            container.put(sentence);
            return;
        }
        if (container.content().containsKey(Container.name.DSRXX.name()) && notContainsValue(container, sentence))
        {
            container.put(Container.name.SSJL.name(), sentence);
        }

    }

    private static boolean notContainsValue(Container container, String sentence)
    {
        sentence = StringUtils.deleteWhitespace(sentence);
        if (sentence.length() > 0 && sentence.substring(sentence.length()-1).equals("。"))
        {
            sentence = sentence.substring(0, sentence.length()-1);
        }
        for (Iterator<String> iterator = container.content().keySet().iterator();iterator.hasNext();)
        {
            String key = iterator.next();
            List<String> stringList = container.content().get(key);
            for (String string : stringList)
            {
                if (string.contains(sentence))
                {
                    return false;
                }
            }
        }
        return true;
    }


    private static void putIdentity(Elements div, Segment segment, Container container, Map<String, String> resetWordMap)
    {
        Map<String, String> identityMap = new HashMap<>();
        for (Element element : div)
        {
            String html = element.html();
            if (html.contains("。"))
            {
                String[] split = html.split("。");
                for (String sentence : split)
                {
                    if (sentence.startsWith("住所地") || sentence.startsWith("地址") )
                    {
                        List<String> dsrxx = container.content().get("DSRXX");
                        if (dsrxx != null)
                        {
                            dsrxx.set(dsrxx.size()-1, dsrxx.get(dsrxx.size()-1).concat(sentence));
                        }
                        continue;
                    }
                    segPreProcess(sentence, segment, container, resetWordMap, identityMap);
                }
            }
            else
            {
                segPreProcess(html, segment, container, resetWordMap, identityMap);
            }
        }
        for (Iterator<String> iterator = identityMap.keySet().iterator();iterator.hasNext();)
        {
            String key = iterator.next();
            String value = identityMap.get(key);
            CustomDictionary.insert(key, value);
        }
    }


    // 提取句子
    private static void segPreProcess(String sentence, Segment segment, Container container, Map<String, String> resetWordMap, Map<String, String> identityMap)
    {
        sentence = HtmlRegexUtils.removeAllHtml(sentence);
        // 去除括号内容M
        String f1= sentence.replaceAll("\\(.*?\\)|\\{.*?}|\\[.*?]|（.*?）", "");
        List<Term> seg = segment.seg(f1);
        List<String> natureList = seg.stream().map(t -> t.nature.toString()).collect(Collectors.toList());
        List<String> wordList = seg.stream().map(t -> t.word).collect(Collectors.toList());
        if (f1.length() <= MAX_LENGTH
                || natureList.contains("expandKey"))
        {
            stopWordProcess(seg, natureList, wordList, f1, segment);
            boolean b = TextSegment.onlyDsrxx(seg);
            if (((b && seg.get(0).nature.startsWith("identity_")) || f1.contains("共同委托") || f1.contains("二上诉人") || f1.contains("二起诉人") || natureList.contains("identity_l"))
                    && !natureList.contains("stop")
                    && (natureList.contains("nr") || natureList.contains("ntc")|| natureList.contains("nto")
                    || natureList.contains("companyKey")))
            {
                // 遍历identityMap 判读名称是否已存在
//                List<String> nameList = new LinkedList<>();
//                String identity = segProcessWithoutRestMap(natureList, wordList, nameList, segment);
//                if (nameHasExit(identity, nameList, identityMap)) return;
                if (nameHasExit(seg, identityMap, natureList, wordList)) return;
                List<String> strings = segPreProcessHasResetMap(natureList, wordList,  resetWordMap, identityMap, segment);
                if (!strings.isEmpty())
                {
                    if (sentence.length() > 0 && !sentence.substring(sentence.length()-1).equals("。")
                                    && !natureList.get(natureList.size()-1).equals("comma")
                                    && !natureList.get(natureList.size()-1).equals("colon")
                                    && !natureList.get(natureList.size()-1).equals("w")
                                    && !natureList.get(natureList.size()-1).equals("dun"))
                    {
                        sentence = sentence.concat("。");
                    }
                    container.put(Container.name.DSRXX.name(), sentence);
                }
            }

            // abbr 处理
            if (sentence.contains("简称"))
            {
                List<Term> seg1 = segment.seg(sentence);
                StringBuilder temporary = new StringBuilder();
                List<StringBuilder> list = new ArrayList<>();
                for (int i=0,size=seg1.size();i<size;i++)
                {
                    Term term = seg1.get(i);
                    if (term.nature.startsWith("identity_") && temporary.length() > 0)
                    {
                        list.add(temporary);
                        temporary = new StringBuilder();
                    }
                    temporary.append(term.word);
                    if (i == size-1) list.add(temporary);
                }
                for (StringBuilder stringBuilder : list)
                {
                    List<String> natureList2 = new ArrayList<>();
                    List<Term> segString = segment.seg(stringBuilder.toString());
                    for (Term term : segString)
                    {
                        natureList2.add(term.nature.toString());
                        addAbbr(term, stringBuilder.toString(), natureList2, resetWordMap);
                    }
                }
            }
        }
    }


    public static boolean nameHasExit(String identity, List<String> nameList,  Set<Dsrxx> identitySet) {
////        for (Iterator<String> iterator = identityMap.keySet().iterator(); iterator.hasNext();)
//        for (Iterator<Dsrxx> iterator = identitySet.iterator(); iterator.hasNext();)
//        {
////            String word = iterator.next();
////            String currentIdentity = identityMap.get(word);
//            Dsrxx dsrxx = iterator.next();
//            if (dsrxx.getIdentity().equals(identity))
//            {
//                Iterator<String> iterator1 = nameList.iterator();
//                for (;iterator1.hasNext();){
//                    String next = iterator1.next();
//                    if (next.equals(dsrxx.getName()))
//                    {
//                        iterator1.remove();
//                    }
//                }
//            }
//        }
//        return nameList.isEmpty();
        Iterator<String> iterator1 = nameList.iterator();
        for (;iterator1.hasNext();){
            String next = iterator1.next();
            Dsrxx instance = Dsrxx.instance(identity, next);
            for (Iterator<Dsrxx> iterator = identitySet.iterator(); iterator.hasNext();) {
                Dsrxx next1 = iterator.next();
                if (next1.equals(instance)) {
                    iterator1.remove();
                }
            }
//            if (identitySet.contains(Dsrxx.instance(identity, next))) {
//                iterator1.remove();
//            }
        }
        return nameList.isEmpty();
    }

    public static boolean nameHasExit(List<Term> seg, Map<String, String> identityMap, List<String> natureList, List<String> wordList)
    {
        Nature nature = seg.get(0).nature;
        // 只判断了上诉人和被上诉人是否重复
        if (nature.equals(Nature.fromString(Config.IDENTITY.identity_p.name())) || nature.equals(Nature.fromString(Config.IDENTITY.identity_d.name())))
        {
            for (Iterator<String> iterator = identityMap.keySet().iterator(); iterator.hasNext();)
            {
                String word = iterator.next();
                String identity = identityMap.get(word);
                if (nature.toString().equals(identity))
                {
                    String name = segProcessByLimit(natureList, wordList, word.length());
                    if (name.equals(word))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static String segProcessByLimit(List<String> natureList, List<String> wordList, int limit)
    {
        boolean start = false;
        String name = "";
        int brackets = 0;
        for (int i=0,size=natureList.size();i<size;i++)
        {
            String nature = natureList.get(i);
            String word  = wordList.get(i);
            if (start)
            {
                if (name.length()>=limit)
                {
                    return name;
                }
                // 出现逗号结束
                if (nature.equals("comma") && brackets % 2 ==0)
                {
                    if (!name.isEmpty()) return name;
                    break;
                }
                // 出现冒号  则跳过
                if (nature.equals("colon")) continue;
                // 出现顿号  跳过
                if (nature.equals("dun"))
                {
                    if (!name.isEmpty()) return name;
                    break;
                }
                if (nature.endsWith("_brackets"))
                {
                    ++brackets;
                    if (name.length() > 0 ) {
                        name = name.concat(word);
                        continue;
                    }
                    continue;
                }
//                if (nature.contains("identity_")) {
//                    continue;
//                }
                if (brackets % 2 == 0) {
                    name = name.concat(word);
                } else if (name.length() > 0){
                    name = name.concat(word);
                }
            }
            else
            {
                if (natureList.contains(Config.IDENTITY.identity_l.name()))
                {
                    if (nature.equals(Config.IDENTITY.identity_l.name()))
                    {
                        start = true;
                    }
                }
                else if (nature.startsWith("identity_"))
                {
                    start = true;
                }
            }
            if (i == size-1)
            {
                if (!name.isEmpty()) return name;
            }
        }
        return name;
    }

    public static List<String> segPreProcessHasResetMap(List<String> natureList, List<String> wordList, Map<String, String> resetWordMap,  Map<String, String> identityMap, Segment segment)
    {
        List<String> nameList = new LinkedList<>();
        String identity = segProcessWithoutRestMap(natureList, wordList, nameList, segment);
        for (String word : nameList)
        {
            resetWordMap.put(word, CustomDictionary.get(word) == null ? null : CustomDictionary.get(word).toString());
            identityMap.put(word, identity);
        }
        return nameList;
    }

    private static void addAbbr(Term term, String first, List<String> natureList, Map<String, String> resetWordMap)
    {
        if (term.word.equals("简称"))
        {
            String abbr;
            int begin, end;
            if (first.contains("(简称")&&first.contains(")"))
            {
                begin = first.indexOf("简称");
                end = first.substring(begin).indexOf(")");
                abbr = first.substring(begin+2, begin+end);
            }
            else if (first.contains("（简称")&&first.contains("）"))
            {
                begin = first.indexOf("简称");
                end = first.substring(begin).indexOf("）");
                abbr = first.substring(begin+2, begin+end);
            } else if (first.contains("(以下简称")&&first.contains(")"))
            {
                begin = first.indexOf("以下简称");
                end = first.substring(begin).indexOf(")");
                abbr = first.substring(begin+4, begin+end);
            }
            else if (first.contains("（以下简称")&&first.contains("）"))
            {
                begin = first.indexOf("以下简称");
                end = first.substring(begin).indexOf("）");
                abbr = first.substring(begin+4, begin+end);
            } else return;
            for (int x=natureList.size();x>0;x--)
            {
                String s1 = natureList.get(x-1);
                if (s1.equals(Config.IDENTITY.identity_p.name()))
                {
                    resetWordMap.put(abbr, CustomDictionary.get(abbr) == null ? null : CustomDictionary.get(abbr).toString());
                    CustomDictionary.add(abbr, "abbr_p 99");
                    break;
                }
                if (s1.equals(Config.IDENTITY.identity_d.name()))
                {
                    resetWordMap.put(abbr, CustomDictionary.get(abbr) == null ? null : CustomDictionary.get(abbr).toString());
                    CustomDictionary.add(abbr, "abbr_d 99");
                    break;
                }
            }
        }
    }


}
