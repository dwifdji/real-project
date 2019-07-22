package com.tzCloud.test;

import org.junit.Test;

import java.text.NumberFormat;
import java.util.*;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2019/3/6 15:59
 */
public class JavaBaseTest {

    @Test
    public void testYanghuiTriangle() {
        int     n        = 5;
        int[][] triangle = new int[n][];
        for (int i = 0; i < n; i++) {
            triangle[i] = new int[i + 1];
            triangle[i][0] = 1;
            triangle[i][i] = 1;
            for (int j = 1; j < i; j++) {
                triangle[i][j] = triangle[i - 1][j - 1] + triangle[i - 1][j];
            }
        }
        System.out.println(Arrays.deepToString(triangle));

//        for (int[] i : triangle) {
//            System.out.println(Arrays.toString(i));
//        }

    }

    @Test
    public void mapRemoveTest() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "value 1");
        map.put(2, "value 2");
        map.put(3, "value 3");
        map.put(4, "value 4");
        map.put(5, "value 5");

        for (Iterator<Integer> iterator = map.keySet().iterator(); iterator.hasNext(); ) {
            Integer key = iterator.next();
            if (key != 1) {
                iterator.remove();
            }
        }

        // remove by value
        map.values().removeIf(value -> !value.contains("1"));
        // remove by key
        map.keySet().removeIf(key -> key != 1);
        // remove by entry / combination of key + value
        map.entrySet().removeIf(entry -> entry.getKey() != 1);

        System.out.println(map);
    }

    @Test
    public void testCollections() {
        List<String> list1 = Collections.emptyList();
//        list1.add("11");  //java.lang.UnsupportedOperationException
        List<String> list2 = Collections.emptyList();
        System.out.println(list1==list2); //true
    }

    @Test
    public void testNumberFormats() {
        NumberFormat currencyInstance = NumberFormat.getCurrencyInstance();
        double i = 0.1;
        System.out.println(currencyInstance.format(i));
    }
}
