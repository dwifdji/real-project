package com.winback.arch.common.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zxiao
 * @Title: 循环和递归两种方式实现未知维度集合的笛卡尔积
 * @ProjectName winback-parent
 * @date 2018/8/13 16:46
 */
public class Descartes {

    /**
     * 递归实现dimValue中的笛卡尔积，结果放在result中
     *
     * @param dimValue 原始数据
     * @param result   结果数据
     * @param layer    dimValue的层数
     * @param curList  每次笛卡尔积的结果
     */
    private static void recursive(List<List<Integer>> dimValue, List<List<Integer>> result, int layer, List<Integer> curList) {
        if (layer < dimValue.size() - 1) {
            if (dimValue.get(layer).size() == 0) {
                recursive(dimValue, result, layer + 1, curList);
            } else {
                for (int i = 0; i < dimValue.get(layer).size(); i++) {
                    List<Integer> list = new ArrayList<Integer>(curList);
                    list.add(dimValue.get(layer).get(i));
                    recursive(dimValue, result, layer + 1, list);
                }
            }
        } else if (layer == dimValue.size() - 1) {
            if (dimValue.get(layer).size() == 0) {
                result.add(curList);
            } else {
                for (int i = 0; i < dimValue.get(layer).size(); i++) {
                    List<Integer> list = new ArrayList<Integer>(curList);
                    list.add(dimValue.get(layer).get(i));
                    result.add(list);
                }
            }
        }
    }

    /**
     * 循环实现dimValue中的笛卡尔积，结果放在result中
     *
     * @param dimValue 原始数据
     * @param result   结果数据
     */
    public static void circulate(List<List<Integer>> dimValue, List<List<String>> result) {
        int total = 1;
        for (List<Integer> list : dimValue) {
            total *= list.size();
        }
        String[] myResult = new String[total];

        int itemLoopNum = 1;
        int loopPerItem = 1;
        int now = 1;
        for (List<Integer> list : dimValue) {
            now *= list.size();

            int index = 0;
            int currentSize = list.size();

            itemLoopNum = total / now;
            loopPerItem = total / (itemLoopNum * currentSize);
            int myIndex = 0;

            for (Integer string : list) {
                for (int i = 0; i < loopPerItem; i++) {
                    if (myIndex == list.size()) {
                        myIndex = 0;
                    }

                    for (int j = 0; j < itemLoopNum; j++) {
                        myResult[index] = (myResult[index] == null ? "" : myResult[index] + ",") + list.get(myIndex);
                        index++;
                    }
                    myIndex++;
                }

            }
        }

        String[] stringResult = myResult;
        for (String string : stringResult) {
            String[] stringArray = string.split(",");
            result.add(Arrays.asList(stringArray));
        }
    }

    /**
     * 程序入口
     *
     * @param args
     */
    public static void main(String[] args) {
        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);

        List<Integer> list2 = new ArrayList<>();
        list2.add(3);
        list2.add(4);
        list2.add(5);

        List<Integer> list3 = new ArrayList<>();
        list3.add(6);
        list3.add(7);

        List<Integer> list4 = new ArrayList<>();
        list4.add(8);
        list4.add(9);

        List<List<Integer>> dimValue = new ArrayList<>();
        dimValue.add(list1);
        dimValue.add(list2);
        dimValue.add(list3);
        dimValue.add(list4);

        List<List<Integer>> recursiveResult = new ArrayList<>();
        // 递归实现笛卡尔积
        recursive(dimValue, recursiveResult, 0, new ArrayList<>());

        System.out.println("递归实现笛卡尔乘积: 共 " + recursiveResult.size() + " 个结果");
        for (List<Integer> list : recursiveResult) {
            for (Integer string : list) {
                System.out.print(string + ",");
            }
            System.out.println();
        }

        List<List<String>> circulateResult = new ArrayList<>();
        circulate(dimValue, circulateResult);
        System.out.println("循环实现笛卡尔乘积: 共 " + circulateResult.size() + " 个结果");
//        for (List<String> list : circulateResult) {
//            StringBuilder options1 = new StringBuilder();
//            for (int i = 0; i < list.size(); i++) {
//                if (i == list.size() - 1) {
//                    options1.append(list.get(i));
//                } else {
//                    options1.append(list.get(i)).append(",");
//                }
//            }
//            System.out.println(options1);
//        }
    }
}
