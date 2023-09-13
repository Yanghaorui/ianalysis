package indi.haorui.ianalysis.model;

import lombok.Data;
import org.springframework.util.Assert;

/**
 * Created by Yang Hao.rui on 2023/8/29
 */
@Data
public class Metric <M> implements ValueObject<Metric<M>>{

    private M metric;

    public Metric(M metric) {
        Assert.notNull(metric, "metric is null");
        this.metric = metric;
    }

    public  static <M> Metric<M> of(M t){
        return new Metric<>(t);
    }


    @Override
    public boolean sameValueAs(Metric<M> other) {
        return metric.equals(other);
    }
}
