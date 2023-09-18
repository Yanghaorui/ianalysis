package indi.haorui.ianalysis.model;

import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;

/**
 * Created by Yang Hao.rui on 2023/8/29
 */
@Data
public class Metric implements ValueObject<Metric>{

    private BigDecimal metric;

    public Metric(@NonNull BigDecimal metric) {
        this.metric = metric;
    }

    public static Metric of(@NonNull BigDecimal t){
        return new Metric(t);
    }


    @Override
    public boolean sameValueAs(Metric other) {
        return metric.equals(other.getMetric());
    }
}
