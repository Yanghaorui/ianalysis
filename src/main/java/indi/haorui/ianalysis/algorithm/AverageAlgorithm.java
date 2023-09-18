package indi.haorui.ianalysis.algorithm;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;

/**
 * Created by Yang Hao.rui on 2023/9/13
 */

public class AverageAlgorithm extends AbstractAlgorithm{

    private final LinkedList<BigDecimal> list;

    private final int windows;

    public AverageAlgorithm(int window, Algorithm next){
        super(next);
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

    @Override
    public boolean condition(BigDecimal in) {
        list.addFirst(in);
        if (list.size() > windows){
            list.removeLast();
            return true;
        } else {
            return list.size() == windows;
        }
    }

}
