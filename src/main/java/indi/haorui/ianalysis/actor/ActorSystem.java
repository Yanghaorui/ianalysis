package indi.haorui.ianalysis.actor;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Yang Hao.rui on 2023/8/16
 */
@Slf4j
public class ActorSystem {

    private static final ThreadPoolExecutor EXECUTOR;

    private static final Queue<BaseActor> ACTORS = new ConcurrentLinkedQueue<>();
    private static final Scheduler SCHEDULER;
    private static final int INSPECT_PERIOD = 10; //ms
    private static final Queue<Runnable> RUNNABLES = new ConcurrentLinkedQueue<>();

    private static final AtomicInteger ACTOR_INDEX = new AtomicInteger();


    static {

        EXECUTOR = new ThreadPoolExecutor(
                1 << 4, 1 << 7, 2L, TimeUnit.MINUTES, new LinkedBlockingQueue<>(1 << 10),
                r -> new Thread(r, "actor-" + ACTOR_INDEX.getAndIncrement() + "-"),
                (r, executor) -> {
                    log.error("reject" + r.toString());
                }
        );
        try {
            SCHEDULER = StdSchedulerFactory.getDefaultScheduler();
            SCHEDULER.start();

            SimpleScheduleBuilder rule = SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInMilliseconds(INSPECT_PERIOD)
                    .repeatForever();
            SCHEDULER.scheduleJob(JobBuilder.newJob(Looper.class).withIdentity("inspector").build(), TriggerBuilder.newTrigger()
                    .withIdentity("trigger@inspector")
                    .withSchedule(rule)
                    .build());
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }


    public static void register(Actor actor) {
        if (actor instanceof SchedulerActor schedulerActor){
            try {
                SCHEDULER.unscheduleJob(schedulerActor.trigger().getKey());
                SCHEDULER.scheduleJob(schedulerActor.job(), schedulerActor.trigger());
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            }
        } else if (actor instanceof BaseActor baseActor){
            ACTORS.removeIf(baseActor::equals);
            ACTORS.add(baseActor);
        }

    }

    public static void pause(Actor actor){
        if (actor instanceof SchedulerActor schedulerActor){
            try {
                SCHEDULER.pauseJob(schedulerActor.jobKey());
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            }
        } else {
            actor.pause();
        }
    }

    public static void resume(Actor actor){
        if (actor instanceof SchedulerActor schedulerActor){
            try {
                SCHEDULER.resumeJob(schedulerActor.jobKey());
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            }
        } else {
            actor.resume();
        }
    }

    public static void dispose(Actor actor){
        if (actor instanceof SchedulerActor schedulerActor){
            try {
                SCHEDULER.deleteJob(schedulerActor.jobKey());
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            }
        } else {
            actor.terminal();
        }
    }

    public static class Looper implements Job{

        void reload(){
            ACTORS.stream().peek(BaseActor::peek)
                    .peek(BaseActor::ready)
                    .filter(BaseActor::isRunnable)
                    .forEach(a -> RUNNABLES.add(a.runnable()));
        }

        @Override
        public void execute(JobExecutionContext context) {
            ACTORS.removeIf(BaseActor::isTerminated);
            this.reload();
            while (!RUNNABLES.isEmpty()){
                Runnable runnable = RUNNABLES.poll();
                if (Objects.nonNull(runnable)){
                    EXECUTOR.execute(runnable);
                }
            }

        }
    }

}
