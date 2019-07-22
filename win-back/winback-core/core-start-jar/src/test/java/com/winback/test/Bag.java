package com.winback.test;

import lombok.Getter;
import lombok.Setter;

/**
 * @author RuQ
 * @Title: Bag
 * @ProjectName winback
 * @Description:
 * @date 2019/3/4 10:15
 */
@Getter
@Setter
public class Bag {

    private int money;
    private int weight;

    public Bag(){

    }

    public Bag(int weight,int money){
        this.money = money;
        this.weight = weight;
    }


}
