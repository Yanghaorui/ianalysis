package indi.haorui.ianalysis.core;

import ch.qos.logback.core.util.SystemInfo;
import lombok.extern.slf4j.Slf4j;
import reactor.core.scheduler.Scheduler;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Yang Hao.rui on 2023/8/16
 */
@Slf4j
public class ActorSystem {

    private static ThreadPoolExecutor executor;
    private static Queue<BaseActor> actors = new ConcurrentLinkedQueue<>();
    private static Scheduler scheduler;
    private static final int INSPECT_PERIOD = 10; //ms
    private static Queue<Runnable> taskQueue = new ConcurrentLinkedQueue<>();

//    private static SystemInfo systemInfo;

    static {
//        systemInfo = new SystemInfo();

        executor = new ThreadPoolExecutor(
                40, 200, 2L, TimeUnit.MINUTES, new LinkedBlockingQueue<>(10_000),
                r -> new Thread(r, ""),
                (r, executor) -> {
                }
        );
    }
}
