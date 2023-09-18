package indi.haorui.ianalysis.manager;

import indi.haorui.ianalysis.enums.WareType;
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
            CPU cpu = CPU.builder()
                    .cpu(Tag.of(metedata.getTag("cpu", String.class)))
                    .host(Tag.of(metedata.getTag("host", String.class)))
                    .usageIdle(Metric.of(metedata.getField("usage_idle", BigDecimal.class)))
                    .usageUser(Metric.of(metedata.getField("usage_user", BigDecimal.class)))
                    .usageSystem(Metric.of(metedata.getField("usage_system", BigDecimal.class)))
                    .timestamp(Instant.ofEpochSecond(metedata.getTimestamp()))
                    .build();
            Hub.hub(cpu);
        };
    }

}
