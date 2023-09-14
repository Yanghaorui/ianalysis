package indi.haorui.ianalysis.hub;

import indi.haorui.ianalysis.actor.ActorSystem;
import indi.haorui.ianalysis.actor.BaseActor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Yang Hao.rui on 2023/8/14
 */
@Slf4j
public class Hub extends BaseActor {


    private final static Map<String, Subscriber<?>> SUBSCRIBERS = new ConcurrentHashMap<>();

    private final static Queue<Object> QUEUE = new LinkedBlockingQueue<>();

    private final static int MAX = 1 << 20; // the maximum bearing capacity.

//    private final static Sinks.Many<Object> SINK = Sinks.many().multicast().onBackpressureBuffer();
//    private final static Flux<Object> FLUX = SINK.asFlux().publishOn(Schedulers.newBoundedElastic(10, 100, "thread-flux-"));

    // Create a Flux from the Sinks.Many instance

    static {
        new Hub();
    }

    public Hub(){
        ActorSystem.register(this);
    }

    /**
     * 订阅消息
     *
     * @param subscriber 订阅器
     */
    public static void register(Subscriber<?> subscriber) {
        SUBSCRIBERS.put(subscriber.id(), subscriber);
    }

    public static void unregister(Subscriber<?> subscriber) {
//        Disposable disposable = SUBSCRIBERS.get(subscriber.id());
//        if (!disposable.isDisposed()){
//            disposable.dispose();
//        }
    }

    public static void hub(Object e) {
        QUEUE.add(e);
        if (QUEUE.size() > MAX){
            QUEUE.poll(); // throw the head element.
        }
//        SINK.emitNext(e, Sinks.EmitFailureHandler.FAIL_FAST);
    }


    @Override
    public void act() {
        Object e = QUEUE.poll();
        if (Objects.isNull(e)){
            return;
        }
        Collection<Subscriber<?>> values = SUBSCRIBERS.values();
        values.forEach(subscriber -> Hub.send(subscriber, e));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void send(@NonNull Subscriber subscriber, Object e) {
        // TODO cache the type mapping.
        if (e.getClass().equals(subscriber.getTypeName()) && subscriber.match(e)) {
            subscriber.subscribe(e);
        }
    }

}
