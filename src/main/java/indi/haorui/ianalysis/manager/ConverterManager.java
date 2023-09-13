package indi.haorui.ianalysis.manager;

import indi.haorui.ianalysis.enums.WareType;
import indi.haorui.ianalysis.hub.Hub;
import indi.haorui.ianalysis.hub.Subscriber;
import indi.haorui.ianalysis.model.Metric;
import indi.haorui.ianalysis.model.Tag;
import indi.haorui.ianalysis.model.cpu.CPU;
import indi.haorui.ianalysis.shell.collector.Output;

import java.time.Instant;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by Yang Hao.rui on 2023/9/11
 */

public class ConverterManager implements Subscriber<Output> {

    private final Map<String, Consumer<Output>> converter = Map.of(WareType.CPU.name(), this.cpuConverter());

    @Override
    public void subscribe(Output output) {
        converter.get(output.getName()).accept(output);
    }

    private Consumer<Output> cpuConverter() {
        return output -> {
            CPU cpu = CPU.builder()
                    .cpu(Tag.of(output.getTag("cpu", String.class)))
                    .name(Tag.of(output.getTag("name", String.class)))
                    .host(Tag.of(output.getTag("host", String.class)))
                    .usageIdle(Metric.of(output.getField("usage_idle", Float.class)))
                    .timestamp(Instant.ofEpochSecond(output.getTimestamp()))
                    .build();
            Hub.hub(cpu);
        };
    }

}
