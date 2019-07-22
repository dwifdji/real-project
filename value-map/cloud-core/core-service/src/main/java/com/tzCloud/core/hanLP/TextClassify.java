package com.tzCloud.core.hanLP;

import com.hankcs.hanlp.classification.classifiers.IClassifier;
import com.tzCloud.core.utils.WenshuHtmlSaveUntil;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author xiaolei
 * @create 2019-03-04 18:34
 * 文本分类
 */
public class TextClassify
{

    private String path;
    private IClassifier classifier;//分类器
    private static TextClassify textClassify;
    private static Map<String, TextClassify> map = new HashMap<>();
    private static WenshuHtmlSaveUntil wenshuHtmlSaveUntil = new WenshuHtmlSaveUntil();

    public static TextClassify builder() { return new TextClassify(); }

    public static TextClassify builder(String path)
    {
        if (map.containsKey(path))
        {
            return map.get(path);
        }

        if (textClassify == null)
        {
            textClassify = new TextClassify(path);
        }
        map.put(path, textClassify);
        return textClassify;
    }

    private TextClassify() {}

    private TextClassify(String path)
    {
        this.path = path;
        this.classifier = HanLPFactory.builder().classifier(path);
    }

    /**
     * 文本分类接口 (分类器匹配)
     * @param text
     * @return
     */
    public String classify(String text)
    {
        Objects.requireNonNull(classifier);
        return this.classifier.classify(text);
    }

    /**
     * 文本分类接口 (关键字匹配)
     * @param text
     * @return
     */
    public String keyWordsJudge(String text)
    {
        return this.testPJJG(text);
    }


    /**
     * 关键字结果判定
     */
    private String testPJJG(String s1)
    {
        if (StringUtils.isNotBlank(s1))
        {
            String key ;
            if (s1.startsWith("一、") || s1.startsWith("1、"))
                key = s1.substring(2, 4);
            else if (s1.startsWith("（一）"))
                key = s1.substring(0, 3);
            else
                key = s1.substring(0, 2);

            if (key.equals("撤销"))
                return Config.PJJG_result.撤销.name();
            else if (key.equals("维持"))
                return Config.PJJG_result.维持.name();
            else if (key.equals("解除") || s1.contains("申请解除"))
                return Config.PJJG_result.解除.name();
            else if (key.equals("冻结") || s1.contains("申请冻结"))
                return Config.PJJG_result.冻结.name();
            else if ((s1.contains("驳回") && s1.length() < 60) || key.equals("驳回"))
                return Config.PJJG_result.驳回.name();
            else if (s1.contains( "撤回") || s1.contains("撤诉") && s1.length() < 60)
                return Config.PJJG_result.撤诉.name();
            else if (s1.contains("不予立案") || s1.contains("不予受理"))
                return Config.PJJG_result.不予立案.name();
            else if (s1.contains( "法院审理") || s1.contains("移送") || s1.contains("转为普通程序") || s1.contains("普通程序审理") && s1.length() < 60)
                return Config.PJJG_result.移送.name();
            else if (s1.contains("并入"))
                return Config.PJJG_result.并入.name();
            else if (s1.contains("中止诉讼") || s1.contains("终结"))
                return Config.PJJG_result.中止诉讼.name();
            else
                return Config.PJJG_result.其他.name();
        }
        return "";
    }

//    public static String keyWordsMatch(String str)
//    {
//        // 被告文件夹
//        if (str.startsWith(KeyWords.被告[0]) || str.startsWith(KeyWords.被告[1]) || str.startsWith(KeyWords.被告[2]))
//        {
//            return "被告";
//        }
//        // 代理人文件夹
//        else if (str.contains(KeyWords.代理人[0]) || str.contains(KeyWords.代理人[1]) || str.contains(KeyWords.代理人[2]))
//        {
//            return "代理人";
//        }
//        // 原告文件夹
//        else if (str.contains(KeyWords.原告[0]) || str.contains(KeyWords.原告[1]) || str.contains(KeyWords.原告[2])
//                || str.contains(KeyWords.原告[3]))
//        {
//            return "原告";
//        }
//        // 关键人文件夹
//        else if (str.contains(KeyWords.关键人[0]) || str.contains(KeyWords.关键人[1]) || str.contains(KeyWords.关键人[2]))
//        {
//            return "关键人";
//        }
//        return "未匹配到";
//    }

//    static class KeyWords
//    {
//        static final String[] 被告  = new String[]{"被", "原审被告", "一审被告"};
//        static final String[] 原告  = new String[]{"原告", "申请人", "上诉人", "起诉人"};
//        static final String[] 代理人 = new String[]{"代理人", "辩护人", "律师"};
//        static final String[] 关键人 = new String[]{"负责人", "代表人", "法人"};
//    }
}
