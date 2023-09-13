package indi.haorui.ianalysis.actor;

import indi.haorui.ianalysis.enums.ActorStatus;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Yang Hao.rui on 2023/8/16
 */
@Slf4j
public abstract class BaseActor implements Actor {

    protected final AtomicReference<ActorStatus> status = new AtomicReference<>(ActorStatus.NEW);

    public Runnable runnable(){
        return () -> {
            if (status.compareAndSet(ActorStatus.RUNNABLE, ActorStatus.RUNNING)){
                this.act();
            }
            status.compareAndSet(ActorStatus.RUNNING, ActorStatus.RUNNABLE);
        };
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
    public void terminal() {
        status.set(ActorStatus.DEAD);
    }

    @Override
    public void peek() {

    }

    @Override
    public void ready(){
        status.compareAndSet(ActorStatus.NEW, ActorStatus.RUNNABLE); // 新建的就绪
    }
    
    public boolean isRunnable(){
        return status.get().equals(ActorStatus.RUNNABLE);
    }

    @Override
    public boolean isTerminated() {
        return status.get().equals(ActorStatus.DEAD);
    }

}
