package indi.haorui.ianalysis.model;

import lombok.Data;
import lombok.NonNull;
import org.springframework.util.Assert;

import java.math.BigDecimal;

/**
 * Created by Yang Hao.rui on 2023/8/29
 */
@Data
public class Metric implements ValueObject<Metric>{

    private String key;

    private BigDecimal metric;

    public Metric(@NonNull BigDecimal metric, @NonNull String key) {
        this.key = key;
        this.metric = metric;
    }

    public static Metric of(@NonNull BigDecimal t, String key){
        return new Metric(t, key);
    }


    @Override
    public boolean sameValueAs(Metric other) {
        return key.equals(other.key) && metric.equals(other);
    }
}
