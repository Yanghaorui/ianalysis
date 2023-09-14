package indi.haorui.ianalysis.manager;

import indi.haorui.ianalysis.algorithm.Algorithm;
import indi.haorui.ianalysis.hub.Hub;
import indi.haorui.ianalysis.hub.Subscriber;
import indi.haorui.ianalysis.model.Metric;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * Created by Yang Hao.rui on 2023/9/13
 */
@Slf4j
public class AlgorithmManager implements Subscriber<Metric> {

    private final String name;

    private final String matchKey; // 计算的指标 e.g. CPU.usageUser
    private final Algorithm algorithm;


    public AlgorithmManager(String name, String matchKey, Algorithm algorithm){
        this.name = name;
        this.algorithm = algorithm;
        this.matchKey = matchKey;
        Hub.register(this);
    }

    @Override
    public void subscribe(Metric metric) {
        BigDecimal calculate = algorithm.calculate(metric.getMetric());
        Hub.hub(new Metric(calculate, this.name));
    }

    @Override
    public boolean match(Metric metric) {
        return metric.getKey().equals(this.matchKey);
    }


}
