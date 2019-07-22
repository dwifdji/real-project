package com.tzCloud.core.model;


import com.tzCloud.core.hanLP.Config;
import com.tzCloud.core.model.caseMatching.TLawyerData;
import com.tzCloud.core.service.impl.LawyerDataServiceImpl;
import org.springframework.expression.Operation;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author xiaolei
 * @create 2019-02-20 10:02
 */
public class LawyerInfo
{
    private LawyerDataServiceImpl.key key;
    private Map<String, TLawyerData> docIds = new HashMap<>(16);
    private double sum  = 0, win  = 0, lose = 0, draw = 0;
    private String winRate, loseRate;

    public static LawyerInfo builder()
    {
        return new LawyerInfo();
    }


    public LawyerInfo key(LawyerDataServiceImpl.key key)
    {
        this.key = key;
        return this;
    }


    public LawyerInfo docAdd(String docId, TLawyerData lawyerData)
    {
        Objects.requireNonNull(lawyerData);
        this.docIds.put(docId, lawyerData);
        this.sum = docIds.size();
        if (Config.JUDGE_result.win.name().equals(lawyerData.getWinFlag()))
            this.win = ++win;
        if (Config.JUDGE_result.lose.name().equals(lawyerData.getWinFlag()))
            this.lose = ++lose;
        if (Config.JUDGE_result.draw.name().equals(lawyerData.getWinFlag()))
            this.draw = ++draw;
        winRate();
        loseRate();
        return this;
    }

    public Map<String, TLawyerData> docIds()
    {
        return docIds;
    }

    public void winRate()
    {
        DecimalFormat df = new DecimalFormat("0.00");
        this.winRate = df.format(( win ) / ( win + lose ));
    }

    public void loseRate()
    {
        DecimalFormat df = new DecimalFormat("0.00");
        this.loseRate = df.format(( lose ) / ( win + lose ));
    }

    public LawyerDataServiceImpl.key getKey() { return key; }

}
