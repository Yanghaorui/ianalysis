package indi.haorui.ianalysis.algorithm;

/**
 * Created by Yang Hao.rui on 2023/9/14
 */

public abstract class AbstractAlgorithm implements Algorithm {

    protected Algorithm next;

    public  AbstractAlgorithm(Algorithm next){
        this.next = next;
    }

    @Override
    public Algorithm next() {
        return next;
    }
}
