package com.tzCloud.core.hanLP;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.tzCloud.arch.core.file.FilePathUtils;

/**
 * @author xiaolei
 * @create 2019-03-04 18:33
 */
public class Config {

    static
    {
        String path = HanLP.Config.CustomDictionaryPath[0];
        HanLP.Config.CustomDictionaryPath = new String[]
                {
                        Dictionary.CUSTOM_DICTIONARY,
                        Dictionary.IDENTITY_DICTIONARY,
                        Dictionary.DATE_DICTIONARY+" date",
                        Dictionary.STOP,
                        Dictionary.KEY,
                        Dictionary.CITY,
                        Dictionary.PROVINCE,
                        Dictionary.AREA,
//                        Config.Dictionary.AH_DICTIONARY+" ah",
                        path
                };
        CustomDictionary.reload();
        CustomDictionary.insert("，","comma");
        CustomDictionary.insert(",","comma");
        CustomDictionary.insert("：","colon");
        CustomDictionary.insert(":","colon");
        CustomDictionary.insert("、","dun");
        CustomDictionary.insert("(","left_brackets");
        CustomDictionary.insert("（","left_brackets");
        CustomDictionary.insert("〔","left_brackets");
        CustomDictionary.insert(")","right_brackets");
        CustomDictionary.insert("）","right_brackets");
        CustomDictionary.insert("〕","right_brackets");
        CustomDictionary.insert("；","semicolon");
        CustomDictionary.insert(";","semicolon");
        System.out.println("CustomDictionary test " + CustomDictionary.get("一九九〇年一月九日"));
        System.out.println("CustomDictionary test " + CustomDictionary.get("上诉人"));
        System.out.println("CustomDictionary test " + CustomDictionary.get("承担"));
    }

    public static class Model
    {
        public static final String 一审_IDENTITY = FilePathUtils.getModelPath() + "/一审/identity";
        public static final String 一审_JUDGE    = FilePathUtils.getModelPath() + "/一审/judege.ser";
        public static final String 二审_IDENTITY = FilePathUtils.getModelPath() + "/二审/identity";
        public static final String NER           = FilePathUtils.getModelPath() + "/ner.bin";
        public static final String IDEN_Other    = FilePathUtils.getModelPath() +"/iden&other.ser";
        public static final String PerceptronCWSModelPath  = FilePathUtils.getModelPath() +"/PerceptronCWS.bin";
        public static final String PerceptronPOSModelPath  = FilePathUtils.getModelPath() +"/PerceptronPOS.bin";
        public static final String PerceptronNERModelPath  = FilePathUtils.getModelPath() +"/PerceptronNER.bin";
        public static final String NNParserModelPath  = FilePathUtils.getModelPath() +"/NNParserModel.txt";
        public static final String LARGECWS  = FilePathUtils.getModelPath() +"/cws.bin";
    }

    public static class Dictionary
    {
        public static final String CUSTOM_DICTIONARY   = FilePathUtils.getDictionaryPath() +  "/customDictionary.txt";
        public static final String IDENTITY_DICTIONARY = FilePathUtils.getDictionaryPath() + "/identity.txt";
        public static final String DATE_DICTIONARY     = FilePathUtils.getDictionaryPath() + "/date.txt";
        public static final String AH_DICTIONARY       = FilePathUtils.getDictionaryPath() + "/ah.txt";
        public static final String STOP                = FilePathUtils.getDictionaryPath() + "/stop.txt";
        public static final String KEY                = FilePathUtils.getDictionaryPath() + "/key.txt";
        public static final String CITY                = FilePathUtils.getDictionaryPath() + "/city.txt";
        public static final String PROVINCE                = FilePathUtils.getDictionaryPath() + "/province.txt";
        public static final String AREA                = FilePathUtils.getDictionaryPath() + "/area.txt";
    }

    public static class Constant
    {
        public static final String ACTIVE_LAWYER  = "上诉人代理律师";
        public static final String PASSIVE_LAWYER = "被上诉人代理律师";
    }

    public enum PJJG_result
    {
        撤销, 维持, 驳回, 撤诉, 移送, 不予立案, 并入, 其他, 中止诉讼, 解除, 冻结
    }

    public enum JUDGE_result
    {
        win,lose,draw
    }

    public enum IDENTITY
    {
        identity_p("上诉人"), identity_d("被上诉人"), identity_l("代理律师"), identity_k("关键人"), identity_p1("当事人");
        String desc;
        IDENTITY(String desc) {
            this.desc = desc;
        }
    }

    public enum UPDATE
    {
        FULL, INCREMENT
    }




}
