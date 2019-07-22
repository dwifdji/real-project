package com.tzCloud.core.hanLP;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.classification.classifiers.IClassifier;
import com.hankcs.hanlp.classification.classifiers.NaiveBayesClassifier;
import com.hankcs.hanlp.classification.models.NaiveBayesModel;
import com.hankcs.hanlp.corpus.io.IOUtil;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.suggest.Suggester;

/**
 * @author xiaolei
 * @create 2019-03-11 16:26
 */
public class HanLPFactory
{
    private static HanLPFactory hanLPFactory = new HanLPFactory();

    private HanLPFactory() {
    }

    public static HanLPFactory builder()
    {
        return hanLPFactory;
    }

    /**
     * 分词器
     * @param enable 是否强制生效用户词典
     * @return
     */
    public Segment segment(boolean enable)
    {
        return HanLP.newSegment().enableCustomDictionaryForcing(enable);
    }

    /**
     * 分类器
     * @param path 模型地址
     * @return
     */
    public IClassifier classifier(String path)
    {
        return new NaiveBayesClassifier((NaiveBayesModel) IOUtil.readObjectFrom(path));
    }

    /**
     * 推荐器
     * @return
     */
    public Suggester suggester()
    {
        return new ScoreLimitSuggester();
    }
}
