package indi.haorui.ianalysis.manager;

import indi.haorui.ianalysis.enums.WareType;
import indi.haorui.ianalysis.hub.Event;
import indi.haorui.ianalysis.hub.Hub;
import indi.haorui.ianalysis.hub.Subscriber;
import indi.haorui.ianalysis.model.Metric;
import indi.haorui.ianalysis.model.Tag;
import indi.haorui.ianalysis.model.cpu.CPU;
import indi.haorui.ianalysis.shell.collector.Metedata;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Created by Yang Hao.rui on 2023/9/11
 */
@Service
public class ConverterManager implements Subscriber<Metedata> {

    private final Map<String, Consumer<Metedata>> converter = Map.of(WareType.CPU.name(), this.cpuConverter());

    @PostConstruct
    public void init(){
        Hub.register(this);
    }

    @Override
    public void subscribe(Metedata metedata) {
        Consumer<Metedata> metedataConsumer = converter.get(metedata.getName().toUpperCase());
        if (Objects.isNull(metedataConsumer)){
            return;
        }
        metedataConsumer.accept(metedata);
    }

    private Consumer<Metedata> cpuConverter() {
        return metedata -> {
            String cpuStr = metedata.getTag("cpu", String.class);
            String host = metedata.getTag("host", String.class);
            String key = cpuStr + host;
            BigDecimal usageIdle = metedata.getField("usage_idle", BigDecimal.class);
            BigDecimal usageSystem = metedata.getField("usage_system", BigDecimal.class);
            BigDecimal usageUser = metedata.getField("usage_user", BigDecimal.class);

            CPU cpu = CPU.builder()
                    .cpu(Tag.of(cpuStr))
                    .host(Tag.of(host))
                    .usageIdle(Metric.of(usageIdle, key))
                    .usageUser(Metric.of(usageUser, key))
                    .usageSystem(Metric.of(usageSystem, key))
                    .timestamp(Instant.ofEpochSecond(metedata.getTimestamp()))
                    .build();
            Hub.hub(cpu);
        };
    }

}
