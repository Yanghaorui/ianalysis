package indi.haorui.ianalysis;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import static reactor.core.publisher.Sinks.EmitFailureHandler.FAIL_FAST;

/**
 * Created by Yang Hao.rui on 2023/8/16
 */

public class FluxTest {


    @Test
    public void fluxTest(){
        Sinks.Many<String> sink = Sinks.many().unicast().onBackpressureBuffer();

        // Create a Flux from the Sinks.Many instance
        Flux<String> flux = sink.asFlux();

        // Subscribe to the Flux
        flux.subscribe(value -> System.out.println("Received: " + value));

        // Emit new events dynamically using the FluxSink
        sink.emitNext("Event 1", FAIL_FAST);
        sink.emitNext("Event 2", FAIL_FAST);
        sink.emitNext("Event 3", FAIL_FAST);
        sink.emitNext("Event 4", FAIL_FAST);
    }


}
