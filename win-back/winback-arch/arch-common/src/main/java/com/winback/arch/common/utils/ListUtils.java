package com.winback.arch.common.utils;

import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/12/4 09:34
 */
public class ListUtils {
    public static List getRandomList(List paramList, int count) {
        if (paramList.size() < count) {
            return paramList;
        }
        Random        random   = new Random();
        List<Integer> tempList = new ArrayList<>();
        List<Object>  newList  = new ArrayList<>();
        int           temp     = 0;
        for (int i = 0; i < count; i++) {
            //将产生的随机数作为被抽list的索引
            temp = random.nextInt(paramList.size());
            if (!tempList.contains(temp)) {
                tempList.add(temp);
                newList.add(paramList.get(temp));
            } else {
                i--;
            }
        }
        return newList;
    }

    public static void main(String[] args) {
        getRandomNumList(20,"100-150");
        getRandomNumList(21,"120-150");
    }

    public static List<Integer> getRandomNumList(int num,String viewCountIncrementTotal) {
        List<Integer> result = new ArrayList<>();
        String[] split                   = viewCountIncrementTotal.split("-");
        int      start                   = Integer.valueOf(split[0]) / num;
        if (start <= 0) {
            start = 1;
        }
        int end = Integer.valueOf(split[1]) / num;
        for (int i = 0; i < num; i++) {
            int aaa = RandomUtils.nextInt(start, end);
            result.add(aaa);
        }
        return result;
    }

}
