package indi.haorui.ianalysis.algorithm;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;

/**
 * Created by Yang Hao.rui on 2023/9/13
 */

public class AverageAlgorithm implements Algorithm{

    private final LinkedList<BigDecimal> list;

    private final int windows;

    public AverageAlgorithm(int window){
        list = new LinkedList<>();
        this.windows = window;
    }

    @Override
    public BigDecimal calculate(BigDecimal in){
        if (list.size() < windows){
            list.addFirst(in);
        }
        list.removeLast();
        list.addFirst(in);
        BigDecimal sum = list.stream().reduce(BigDecimal::add).orElse(BigDecimal.valueOf(0f));
        return sum.divide(new BigDecimal(list.size()), RoundingMode.HALF_DOWN);
    }

}
