package indi.haorui.ianalysis.algorithm;

import indi.haorui.ianalysis.model.Metric;
import indi.haorui.ianalysis.model.Tag;
import lombok.Data;

/**
 * Created by Yang Hao.rui on 2023/9/13
 */
@Data
public class Input {

    private Tag<String> metricName;

    private Metric metricValue;

}
