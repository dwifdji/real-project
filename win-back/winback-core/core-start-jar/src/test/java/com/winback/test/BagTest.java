package com.winback.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author RuQ
 * @Title: BagTest
 * @ProjectName winback
 * @Description:
 * @date 2019/3/4 10:16
 */
public class BagTest extends TestBase {

    @Test
    public void testBag() {

        List<Bag> bagList = new ArrayList<>();
        bagList.add(new Bag(15, 1500));
        bagList.add(new Bag(30, 3000));
        bagList.add(new Bag(20, 2000));
        bagList.add(new Bag(29, 2900));
        bagList.add(new Bag(1, 200));

        for (int i = 0; i < bagList.size(); i++) {

        }

    }

}
