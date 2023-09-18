package indi.haorui.ianalysis.algorithm;

import java.math.BigDecimal;

/**
 * Created by Yang Hao.rui on 2023/9/13
 */

public interface Algorithm {

    /**
     *
     * @param in 计算
     * @return 计算结果
     */
    BigDecimal calculate(BigDecimal in);

    /**
     * 是否满足计算条件
     * @param in 入参
     */
    boolean condition(BigDecimal in);

    Algorithm next();


}
