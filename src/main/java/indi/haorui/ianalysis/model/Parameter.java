package indi.haorui.ianalysis.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Created by Yang Hao.rui on 2023/9/14
 */
@Getter
@Builder
public class Parameter implements ValueObject<Parameter>{

    private String key;

    private BigDecimal param;

    private Instant timestamp;

    public Parameter(String key, BigDecimal param){
        this(key, param, Instant.now());
    }

    public Parameter(String key, BigDecimal param, Instant timestamp){
        this.key = key;
        this.param = param;
        this.timestamp = timestamp;
    }

    @Override
    public boolean sameValueAs(Parameter other) {
        return false;
    }
}
