package com.tzCloud.core.hanLP;

import com.hankcs.hanlp.suggest.Suggester;
import com.hankcs.hanlp.suggest.scorer.BaseScorer;
import com.hankcs.hanlp.suggest.scorer.IScorer;
import com.hankcs.hanlp.suggest.scorer.lexeme.IdVectorScorer;

import java.util.*;

/**
 * @author xiaolei
 * @create 2019-03-19 09:16
 */
public class ScoreLimitSuggester extends Suggester
{
    double score = 7.0;
    List<BaseScorer> scorerList;

    public ScoreLimitSuggester()
    {
        scorerList = new ArrayList<>();
        scorerList.add(new IdVectorScorer());
    }

    @Override
    public void addSentence(String sentence) {
        for (IScorer scorer : scorerList)
        {
            scorer.addSentence(sentence);
        }
    }

    @Override
    public List<String> suggest(String key, int size)
    {
        List<String> resultList = new ArrayList<>(size);
        TreeMap<String, Double> scoreMap = new TreeMap<>();
        for (BaseScorer scorer : scorerList)
        {
            Map<String, Double> map = scorer.computeScore(key);
            for (Map.Entry<String, Double> entry : map.entrySet())
            {
                Double score = scoreMap.get(entry.getKey());
                if (score == null) score = 0.0;
                scoreMap.put(entry.getKey(), score + entry.getValue() * scorer.boost);
            }
        }
        for (Map.Entry<Double, Set<String>> entry : sortScoreMap(scoreMap).entrySet())
        {
            for (String sentence : entry.getValue())
            {
                if (resultList.size() >= size || entry.getKey() < score) return resultList;
                resultList.add(sentence);
            }
        }

        return resultList;
    }

    /**
     * 将分数map排序折叠
     * @param scoreMap
     * @return
     */
    private static TreeMap<Double ,Set<String>> sortScoreMap(TreeMap<String, Double> scoreMap)
    {
        TreeMap<Double, Set<String>> result = new TreeMap<>(Collections.reverseOrder());
        for (Map.Entry<String, Double> entry : scoreMap.entrySet())
        {
            Set<String> sentenceSet = new HashSet<>();
            result.putIfAbsent(entry.getValue(), sentenceSet);
            sentenceSet.add(entry.getKey());
        }

        return result;
    }


    /**
     * 从map的值中找出最大值，这个值是从0开始的
     * @param map
     * @return
     */
    private static Double max(Map<String, Double> map)
    {
        Double theMax = 0.0;
        for (Double v : map.values())
        {
            theMax = Math.max(theMax, v);
        }

        return theMax;
    }
}
