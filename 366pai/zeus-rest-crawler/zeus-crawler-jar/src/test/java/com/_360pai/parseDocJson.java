package com._360pai;

import com._360pai.seimi.util.HtmlRegexUtils;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zxiao
 * @Title: parseDocJson
 * @ProjectName zeus-rest-crawler
 * @Description:
 * @date 2019/1/7 14:27
 */
public class parseDocJson {


    @Test
    public void name() throws IOException {


//        String s = FileUtils.readFileToString(new File("D:\\workspace\\zeus\\wenshu\\wen\\zeus-rest-crawler\\zeus-crawler-jar\\src\\test\\lib\\docJSON"), "UTF-8");
//
//        Document parse = Jsoup.parse(s);
//        Elements select = parse.select("a[type='dir']");
//
//
//        String[] arr = new String[select.size()];
//
//        for (int i = 0; i < select.size(); i++) {
//            Element element = select.get(i).nextElementSibling();
//            String dataString = arr[i];
//            if (dataString == null) {
//                dataString = "";
//            }
//            dataString += element.toString();
//            GetMsg(element, select.get(i));
//            if (select.get(i).toString().contains("SSJL")) {
//                String removeAllHtml = HtmlRegexUtils.removeAllHtml(element.html());
//                一审判决 += removeAllHtml;
//            }
//            System.out.println("element = " + element);
//            boolean a = true;
//            if (i == select.size() - 1) {
//                if (element.equals(select.get(i))) {
//                    a = false;
//                }
//            } else {
//                if (element.equals(select.get(i + 1))) {
//                    a = false;
//                }
//            }
//
//            while (a) {
//                element = element.nextElementSibling();
//
//                if (i == select.size() - 1) {
//                    //尾部
//                    if (element == null || element.equals(select.get(i))) {
//                        break;
//                    }
//                    dataString += element.toString();
//                    GetMsg(element, select.get(i));
//                    System.out.println("element1 = " + element);
//                } else {
//                    if (element.equals(select.get(i + 1))) {
//                        break;
//                    }
//
//                    if (select.get(i).toString().contains("SSJL")) {
//                        二审判决 += HtmlRegexUtils.removeAllHtml(element.html());
//                    }
//                    if (select.get(i).toString().contains("PJJG")) {
//                        二审判决 += HtmlRegexUtils.removeAllHtml(element.html());
//                    }
//                    GetMsg(element, select.get(i));
//                    dataString += element.toString();
//                    System.out.println("element1 = " + element);
//                }
//
//            }
//            System.out.println("dataString = " + dataString);
//            System.out.println("==========================================");
//        }


    }

    String 审判长 = "";
    String 审判员 = "";
    String 书记员 = "";
    String 原公诉机关 = "";
    String 上诉人 = "";
    String 代理人 = "";
    String 委托诉讼代理人 = "";
    String 被告人 = "";
    String 审理经过 = "";
    String 二审判决 = "";
    String 当事人信息 = "";
    String 本院认为 = "";
    String 原告请求情况 = "";
    String 被告请求情况 = "";
    String 一审法院查明 = "";
    String 一审原告诉称 = "";
    String 一审法院认为 = "";
    String 一审被告辩称 = "";
    String 被上诉人辩称 = "";
    String 上诉人辩称 = "";
    String 上诉人诉称 = "";
    String 原审被告 = "";
    String 原审原告 = "";
    String 被上诉人 = "";
    String 本院查明 = "";

    @Test
    public void name1() throws IOException {


        String s = FileUtils.readFileToString(new File("C:\\Users\\360pai\\Desktop\\裁判文书\\2.html"), "UTF-8");

        Document parse = Jsoup.parse(s);
        Elements select = parse.select("a[type='dir']");

        Map<String, String> map = new LinkedHashMap<>();

        croverData(select, map);

//        for (String key : map.keySet()) {
//            System.out.println("key = " + key);
//            String s1 = map.get(key);
//            Document parse1 = Jsoup.parse(s1);
//            Elements div = parse1.getElementsByTag("div");
//            for (Element element : div) {
//
//                GetMsg(element, key);
//                System.out.println(HtmlRegexUtils.removeAllHtml(element.text()));
//            }
//            System.out.println("===========================");
//        }

        for (String key : map.keySet()) {
            String s1 = map.get(key);
            Document parse1 = Jsoup.parse(s1);
            Elements div = parse1.getElementsByTag("div");
            for (Element element : div) {
                if (key.contains("SSJL")) {
                    String removeAllHtml = HtmlRegexUtils.removeAllHtml(element.html());
                    if (removeAllHtml.startsWith("本院认为")) {
                        continue;
                    }
                    审理经过 += removeAllHtml;
                    审理经过.replace(一审法院认为, "");
                    审理经过.replace(上诉人诉称, "");
                }

                if (key.contains("PJJG")) {
                    二审判决 += HtmlRegexUtils.removeAllHtml(element.html());
                }

                if (key.contains("DSRXX")) {
                    当事人信息 += HtmlRegexUtils.removeAllHtml(element.html());
                }

                if (!map.containsKey("DSRXX") && map.containsKey("WBSB")) {
                    if (key.contains("WBSB")) {
                        Integer integer = element.elementSiblingIndex();
                        if (integer > 3) {
                            当事人信息 += HtmlRegexUtils.removeAllHtml(element.html());
                        }
                    }

                }
            }
        }

        System.out.println("审判长 = " + 审判长);
        System.out.println("审判员 = " + 审判员);
        System.out.println("书记员 = " + 书记员);
        System.out.println("原公诉机关 = " + 原公诉机关);
        System.out.println("上诉人 = " + 上诉人);
        System.out.println("代理人 = " + 代理人);
        System.out.println("委托诉讼代理人 = " + 委托诉讼代理人);
        System.out.println("被告人 = " + 被告人);
        System.out.println("被上诉人 = " + 被上诉人);
        System.out.println("审理经过 = " + 审理经过);
        System.out.println("二审判决 = " + 二审判决);
        System.out.println("当事人信息 = " + 当事人信息);
        System.out.println("本院认为 = " + 本院认为);
        System.out.println("原告请求情况 = " + 原告请求情况);
        System.out.println("被告请求情况 = " + 被告请求情况);
        System.out.println("一审法院查明 = " + 一审法院查明);
        System.out.println("一审原告诉称 = " + 一审原告诉称);
        System.out.println("一审法院认为 = " + 一审法院认为);
        System.out.println("一审被告辩称 = " + 一审被告辩称);
        System.out.println("被上诉人辩称 = " + 被上诉人辩称);
        System.out.println("上诉人辩称 = " + 上诉人辩称);
        System.out.println("上诉人诉称 = " + 上诉人诉称);
        System.out.println("原审被告 = " + 原审被告);
        System.out.println("原审原告 = " + 原审原告);
        System.out.println("本院查明 = " + 本院查明);
    }

//    @Test
//    public void name2() throws IOException {
//        // 文本推荐(句子级别，从一系列句子中挑出与输入句子最相似的那一个)
//        Suggester suggester = new Suggester();
//        //CRF词法分析器
//        CRFLexicalAnalyzer analyzer = new CRFLexicalAnalyzer();
//
//        PerceptronLexicalAnalyzer perceptronLexicalAnalyzer = new PerceptronLexicalAnalyzer("D:/workspace/zeus/HanLP/data/model/perceptron/pku199801/cws.bin",
//                HanLP.Config.PerceptronPOSModelPath,
//                HanLP.Config.PerceptronNERModelPath);
//
//        String s = FileUtils.readFileToString(new File("D:\\workspace\\zeus\\wenshu\\wen\\zeus-rest-crawler\\zeus-crawler-jar\\src\\test\\lib\\docJSON2"), "UTF-8");
//
//        Document parse = Jsoup.parse(s);
//        Elements select = parse.select("a[type='dir']");
//
//        Map<String, String> map = new LinkedHashMap<>();
//
//        croverData(select, map);
//
//
//        for (String key : map.keySet()) {
//            List<String> divList = new LinkedList<>();
//            System.out.println("key = " + key);
//            String s1 = map.get(key);
//            Document parse1 = Jsoup.parse(s1);
//            Elements divs = parse1.getElementsByTag("div");
//            for (Element element : divs) {
////                GetMsg(element, key);
//                System.out.println(HtmlRegexUtils.removeAllHtml(element.text()));
//                divList.add(HtmlRegexUtils.removeAllHtml(element.text()));
//            }
//            String[] arr = new String[divList.size()];
//            String[] array = divList.toArray(arr);
//            perceptronLexicalAnalyzer.learn("原/b 审互/vn 为/p 原/b 、/w 被告/n ）/w ：赵倩/nr ，/w 女/b ，/w 1983年/t 8月/t 2日/t 出生/v ，/w 汉族");
//            perceptronLexicalAnalyzer.learn("被/p 上诉人/n （/w 原/b 审互/vn 为/p 原/b 、/w 被告/n ）");
//            perceptronLexicalAnalyzer.learn("赵倩/nr 因与/c");
//            perceptronLexicalAnalyzer.learn("书记员/n 付鑫裕/nr");
//            perceptronLexicalAnalyzer.learn("法官助理/n 张倩/nr");
//            for (String div : array) {
//                suggester.addSentence(div);
//                Sentence analyze = perceptronLexicalAnalyzer.analyze(div.replace("　", ""));
//                System.out.println(analyze);
//            }
//            if (key.contains("WBSB")) {
//                System.out.println(suggester.suggest("代理人 律师", 1));
//                System.out.println(suggester.suggest("被上诉人", 1));
//                System.out.println(suggester.suggest("上诉人", 2));
//            }
//            System.out.println("===========================");
//        }
//
//
//    }

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

    private void GetMsg(Element element, String select) {

        String removeAllHtml = HtmlRegexUtils.removeAllHtml(element.html()).trim().replace("　", "");

        if (removeAllHtml.startsWith("审判长")) {
            审判长 += removeAllHtml + ";";
        } else if (removeAllHtml.startsWith("审判员")) {
            审判员 += removeAllHtml + ";";
        } else if (removeAllHtml.startsWith("书记员")) {
            书记员 += removeAllHtml + ";";
        } else if (removeAllHtml.startsWith("委托代理人")) {
            代理人 += removeAllHtml;
        } else if (removeAllHtml.startsWith("委托诉讼代理人")) {
            委托诉讼代理人 += removeAllHtml;
        } else if (removeAllHtml.startsWith("本院认为")) {
            本院认为 += removeAllHtml;
            Element element1 = element.nextElementSibling();
            String removeAllHtml1 = HtmlRegexUtils.removeAllHtml(element1.toString());
            Element element2 = element1;
            while (!removeAllHtml1.endsWith("判决如下：")) {
                element2 = element2.nextElementSibling();
                removeAllHtml1 = HtmlRegexUtils.removeAllHtml(element2.toString());
                本院认为 += removeAllHtml1;
            }

        } else if (removeAllHtml.contains("原审诉称")) {
            一审原告诉称 += removeAllHtml;
        } else if (removeAllHtml.startsWith("本院另查明")) {
            本院查明 += removeAllHtml;
        } else if (removeAllHtml.startsWith("原审法院认为") || removeAllHtml.startsWith("一审法院认为") || removeAllHtml.startsWith("一审法院经审查后认为")) {
            一审法院认为 += removeAllHtml;
            String s = element.nextElementSibling().toString();
            String removeAllHtml1 = HtmlRegexUtils.removeAllHtml(s);
            if (removeAllHtml1.contains("一审法院")) {
                一审法院认为 += removeAllHtml1;
            }
            if (removeAllHtml1.contains("法院") && removeAllHtml1.contains("应当按照") && removeAllHtml1.contains("规定")) {
                一审法院认为 += removeAllHtml1;
            }
        } else if (removeAllHtml.contains("原审辩称")) {
            一审被告辩称 += removeAllHtml;
        } else if (removeAllHtml.contains("上诉请求")) {
            String[] split = removeAllHtml.split("：");
            String name = split[0];
            String s1 = name.replace("上诉请求", "");
            String s2 = s1.replace("有限公司", "").replace("公司", "");
            if (原审被告.contains(s2)) {
                被告请求情况 += removeAllHtml;
            } else if (原审原告.contains(s2)) {
                原告请求情况 += removeAllHtml;
            }
        } else if (removeAllHtml.startsWith("原审法院经审理查明")) {
            Element element1 = element.nextElementSibling();
            String removeAllHtml1 = HtmlRegexUtils.removeAllHtml(element1.toString());
            一审法院查明 += removeAllHtml;
            if (!removeAllHtml1.startsWith("原审")) {
                一审法院查明 += removeAllHtml1;
            }

        } else if (removeAllHtml.startsWith("原公诉机关")) {
            原公诉机关 += removeAllHtml.replace("原公诉机关", "");
        } else if (removeAllHtml.contains("不服一审法院判决") || removeAllHtml.contains("不服一审法院裁定")) {
            String[] split = removeAllHtml.split("，");
            String name = split[0];
            String s1 = name.replace("不服一审法院判决", "");
            s1 = name.replace("不服一审法院裁定", "");
            String s2 = s1.replace("公司", "");
            if (原审被告.contains(s2)) {
                被上诉人辩称 += removeAllHtml;
            }
            if (原审原告.contains(s2)) {
                上诉人诉称 += removeAllHtml;
            }
            if (上诉人.contains(s2)) {
                上诉人诉称 += removeAllHtml;
            }
        } else if (removeAllHtml.startsWith("上诉人（")) {
            if (removeAllHtml.contains("原审被告")) {
                int i = removeAllHtml.indexOf("，");
                if (i < 0) {
                    原审被告 += removeAllHtml.substring(removeAllHtml.indexOf("）") + 1).replace("。", "") + ";";
                } else {
                    原审被告 += removeAllHtml.substring(removeAllHtml.indexOf("）") + 2, removeAllHtml.indexOf("，")).replace("。", "") + ";";
                }
            }
            if (removeAllHtml.contains("原审原告")) {
                int i = removeAllHtml.indexOf("，");
                if (i < 0) {
                    原审原告 += removeAllHtml.substring(removeAllHtml.indexOf("）") + 1).replace("。", "") + ";";
                    上诉人 = 原审原告;
                } else {
                    原审原告 += removeAllHtml.substring(removeAllHtml.indexOf("）") + 2, removeAllHtml.indexOf("，")).replace("。", "") + ";";
                    上诉人 = 原审原告;
                }
            }

            if (removeAllHtml.contains("原审互为原、被告")) {
                int i = removeAllHtml.indexOf("，");
                if (i < 0) {
                    if (removeAllHtml.startsWith("上诉人")) {
                        原审原告 += removeAllHtml.substring(removeAllHtml.indexOf("）") + 1).replace("。", "") + ";";
                        上诉人 = 原审原告;
                    } else if (removeAllHtml.startsWith("被上诉人")) {
                        原审被告 += removeAllHtml.substring(removeAllHtml.indexOf("）") + 1).replace("。", "") + ";";
                        被上诉人 = 原审被告;
                    }
                } else {
                    if (removeAllHtml.startsWith("上诉人")) {
                        原审原告 += removeAllHtml.substring(removeAllHtml.indexOf("）") + 2, removeAllHtml.indexOf("，")).replace("。", "") + ";";
                        上诉人 = 原审原告;
                    } else if (removeAllHtml.startsWith("被上诉人")) {
                        原审被告 += removeAllHtml.substring(removeAllHtml.indexOf("）") + 2, removeAllHtml.indexOf("，")).replace("。", "") + ";";
                        被上诉人 = 原审被告;
                    }

                }
            }
        } else if (removeAllHtml.startsWith("上诉人")) {
            if (select.contains("DSRXX") || select.contains("WBSB")) {
                try {
                    上诉人 += removeAllHtml.substring(removeAllHtml.indexOf("上诉人") + 3, removeAllHtml.indexOf("，"));
                } catch (Exception e) {
                    上诉人 += removeAllHtml;
                }
            }
        } else if (removeAllHtml.startsWith("被上诉人（")) {
            if (removeAllHtml.contains("原审被告")) {
                原审被告 += removeAllHtml.substring(removeAllHtml.indexOf("）") + 2, removeAllHtml.indexOf("，")).replace("。", "") + ";";
                被上诉人 = 原审被告;
            }


            if (removeAllHtml.contains("原审互为原、被告")) {
                int i = removeAllHtml.indexOf("，");
                if (i < 0) {
                    if (removeAllHtml.startsWith("上诉人")) {
                        原审原告 += removeAllHtml.substring(removeAllHtml.indexOf("）") + 1).replace("。", "") + ";";
                        上诉人 = 原审原告;
                    } else if (removeAllHtml.startsWith("被上诉人")) {
                        原审被告 += removeAllHtml.substring(removeAllHtml.indexOf("）") + 1).replace("。", "") + ";";
                        被上诉人 = 原审被告;
                    }
                } else {
                    if (removeAllHtml.startsWith("上诉人")) {
                        原审原告 += removeAllHtml.substring(removeAllHtml.indexOf("）") + 2, removeAllHtml.indexOf("，")).replace("。", "") + ";";
                        上诉人 = 原审原告;
                    } else if (removeAllHtml.startsWith("被上诉人")) {
                        原审被告 += removeAllHtml.substring(removeAllHtml.indexOf("）") + 2, removeAllHtml.indexOf("，")).replace("。", "") + ";";
                        被上诉人 = 原审被告;
                    }

                }
            }

        } else if (removeAllHtml.contains("被告人")) {
            if (select.contains("DSRXX") || select.contains("WBSB")) {
                被告人 += removeAllHtml.substring(removeAllHtml.indexOf("被告人"), removeAllHtml.indexOf("，")) + ";";
            }
        } else if (removeAllHtml.startsWith("被上诉人")) {
            element.remove();
            被上诉人 += removeAllHtml.substring(removeAllHtml.indexOf("，") - 3, removeAllHtml.indexOf("，")) + ";";
        }

        if (removeAllHtml.contains("辩称")) {
            if (!removeAllHtml.contains("原审辩称")) {
                被上诉人辩称 += removeAllHtml;
            }
            String[] split = removeAllHtml.split("，");
            String name = split[0];
            String s1 = name.replace("辩称", "");
            String s2 = s1.replace("公司", "");
            if (原审被告.contains(s2)) {
                被上诉人辩称 += removeAllHtml;
            } else if (原审原告.contains(s2)) {
                上诉人辩称 += removeAllHtml;
            }
        }
    }

//    @Test
//    public void name3() {
//
//        System.out.println("首次编译运行时，HanLP会自动构建词典缓存，请稍候……");
//        HanLP.Config.enableDebug();         // 为了避免你等得无聊，开启调试模式说点什么:-)
//        System.out.println(HanLP.segment("你好，欢迎使用HanLP汉语处理包！接下来请从其他Demo中体验HanLP丰富的功能~"));
//
//    }
}
