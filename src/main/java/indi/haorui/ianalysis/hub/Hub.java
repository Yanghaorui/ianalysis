package indi.haorui.ianalysis.hub;

import lombok.extern.slf4j.Slf4j;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import static reactor.core.publisher.Sinks.EmitFailureHandler.FAIL_FAST;

/**
 * Created by Yang Hao.rui on 2023/8/14
 */
@Slf4j
public class Hub {


    private final static Map<String, Disposable> SUBSCRIBERS = new ConcurrentHashMap<>();

    private final static Queue<Event<?>> QUEUE = new LinkedBlockingQueue<>();

    private final static Sinks.Many<Event<?>> SINK = Sinks.many().unicast().onBackpressureBuffer();
    private final static Flux<Event<?>> FLUX = SINK.asFlux().publishOn(Schedulers.newBoundedElastic(100, 100, "thread-flux-"));

    // Create a Flux from the Sinks.Many instance


    /**
     * 订阅消息
     *
     * @param subscriber 订阅器
     */
    public static void subscribe(Subscriber subscriber) {
        Disposable subscribe = FLUX.subscribe(subscriber::subscribe, throwable -> log.error(subscriber.id() + "occurred error", throwable));
        SUBSCRIBERS.put(subscriber.id(), subscribe);
    }

    public static void unsubscribe(Subscriber subscriber) {
        Disposable disposable = SUBSCRIBERS.get(subscriber.id());
        if (!disposable.isDisposed()){
            disposable.dispose();
        }
    }

    public static void hub(Event<?> e) {
        SINK.emitNext(e, FAIL_FAST);
    }

    public void act() {

    }


}
