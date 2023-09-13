package indi.haorui.ianalysis.actor;

import indi.haorui.ianalysis.enums.ActorStatus;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.quartz.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Yang Hao.rui on 2023/8/28
 */
@Slf4j
public abstract class SchedulerActor extends QuartzJobBean implements Actor {

    protected final AtomicReference<ActorStatus> status = new AtomicReference<>(ActorStatus.RUNNABLE);

//    private Trigger trigger;

    protected int interval;

    protected  String cronExpression;

    private final String key = UUID.randomUUID().toString();

    public JobKey jobKey(){
        return JobKey.jobKey(key);
    }

    JobDetail job(){
        return JobBuilder.newJob(this.getClass()).withIdentity(this.jobKey()).build();
    }

    Trigger trigger(){
        ScheduleBuilder<? extends Trigger> scheduleBuilder = Strings.isBlank(cronExpression) ? simpleRule(interval) : cronRule();
        return TriggerBuilder.newTrigger()
                .withIdentity("trigger@" + key)
                .withSchedule(scheduleBuilder)
                .build();
    }

    private ScheduleBuilder<CronTrigger> cronRule(){
        return CronScheduleBuilder.cronSchedule(cronExpression);
    }


    @Override
    protected void executeInternal(@NonNull JobExecutionContext context) {
        try {
            if (this.status.compareAndSet(ActorStatus.RUNNABLE, ActorStatus.RUNNING)){
                this.act();
            }
            status.compareAndSet(ActorStatus.RUNNING, ActorStatus.RUNNABLE);
        } catch (Exception e){
            log.error("unexpected error", e);
        }
    }


    private ScheduleBuilder<SimpleTrigger> simpleRule(int i){
        if (i < 10){
            i = 10;
        }
        return SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMilliseconds(i)
                .repeatForever();
    }

    @Override
    public void peek() {

    }

    @Override
    public void terminal() {
        ActorSystem.dispose(this);
    }

    @Override
    public void pause() {
        status.set(ActorStatus.PAUSE);
    }

    @Override
    public void resume() {
        status.compareAndSet(ActorStatus.PAUSE, ActorStatus.RUNNABLE);
    }


    @Override
    public boolean isTerminated() {
        return status.get().equals(ActorStatus.DEAD);
    }


    @Override
    public void ready() {
    }

}
