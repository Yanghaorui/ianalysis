package indi.haorui.ianalysis.algorithm;

import indi.haorui.ianalysis.hub.Hub;
import indi.haorui.ianalysis.hub.Subscriber;
import indi.haorui.ianalysis.model.Parameter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Yang Hao.rui on 2023/9/13
 */
@Slf4j
public class AlgorithmManager implements Subscriber<Parameter> {

    private final static Map<String, AlgorithmManager> ALGORITHM_MANAGER_MAP = new ConcurrentHashMap<>();

    private final String name;

    private final String matchKey; // 计算的指标 e.g. CPU.usageUser
    private final Algorithm algorithm;


    public AlgorithmManager(String name, String matchKey, Algorithm algorithm){
        this.name = name;
        this.algorithm = algorithm;
        this.matchKey = matchKey;
        Hub.register(this);
        ALGORITHM_MANAGER_MAP.put(name, this);
    }

    @Override
    public void subscribe(Parameter parameter) {
        BigDecimal calculate = this.calculate(algorithm, parameter.getParam());
        if (Objects.isNull(calculate)){
            return;
        }
        Hub.hub(new Parameter(this.name, calculate));
    }

    @Override
    public boolean match(Parameter parameter) {
        return parameter.getKey().equals(this.matchKey);
    }

    private BigDecimal calculate(Algorithm algorithm, BigDecimal input){
        if (Objects.isNull(algorithm)){
            return input;
        }
        if (!algorithm.condition(input)){
            return null;
        }
        BigDecimal output = algorithm.calculate(input);
        return calculate(algorithm.next(), output);
    }

}
