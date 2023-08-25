package indi.haorui.ianalysis.hub;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by Yang Hao.rui on 2023/8/14
 */

public interface Subscriber extends Consumer<Event<?>> {

    String id();

    void subscribe(Event<?> event);

    Predicate<Event<?>> match();

}
