package indi.haorui.ianalysis.actor;

/**
 * Created by Yang Hao.rui on 2023/8/28
 */

public interface Actor {

    void act();

    void ready();

    void peek();

    void pause();

    void resume();

    void terminal();

    boolean isTerminated();

}
