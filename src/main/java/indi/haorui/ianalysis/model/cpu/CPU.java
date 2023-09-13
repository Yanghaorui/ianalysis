package indi.haorui.ianalysis.model.cpu;

import indi.haorui.ianalysis.hub.Event;
import indi.haorui.ianalysis.model.Entity;
import indi.haorui.ianalysis.model.Metric;
import indi.haorui.ianalysis.model.Tag;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

/**
 * Created by Yang Hao.rui on 2023/8/28
 */
@Builder
@Getter
public class CPU extends Event implements Entity<CPU> {

    private Tag<String> name;

    private Tag<String> host;

    private Tag<String> cpu;

    // metrics
    private Metric<Float> usageUser;

    private Metric<Float> usageSystem;

    private Metric<Float> usageIdle;

    private Instant timestamp;

    @Override
    public boolean sameIdentityAs(CPU other) {
        return this.getName().sameValueAs(other.getName()) &&
                this.getHost().sameValueAs(other.getHost());
    }

    public String key(){
        return this.getHost().tag() + this.getName().tag();
    }


}
