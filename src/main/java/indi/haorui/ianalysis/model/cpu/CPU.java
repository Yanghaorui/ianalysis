package indi.haorui.ianalysis.model.cpu;

import indi.haorui.ianalysis.model.Entity;
import indi.haorui.ianalysis.model.Metric;
import indi.haorui.ianalysis.model.Tag;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

/**
 * Created by Yang Hao.rui on 2023/8/28
 */
@Builder
@Getter
public class CPU implements Entity<CPU> {

    private Tag<String> host;

    private Tag<String> cpu;

    // metrics
    private Metric usageUser;

    private Metric usageSystem;

    private Metric usageIdle;

    private Instant timestamp;

    @Override
    public boolean sameIdentityAs(CPU other) {
        return this.getCpu().sameValueAs(other.getCpu()) &&
                this.getHost().sameValueAs(other.getHost());
    }

    public String key(){
        return this.getCpu().tag() + this.getHost().tag();
    }

    public List<Metric> getMetric(){
        return List.of(usageUser, usageIdle, usageSystem);
    }

}
