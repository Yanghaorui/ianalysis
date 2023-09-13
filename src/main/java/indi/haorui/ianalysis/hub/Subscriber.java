package indi.haorui.ianalysis.hub;

import org.springframework.core.ResolvableType;

import java.util.Objects;

/**
 * Created by Yang Hao.rui on 2023/8/14
 */

public interface Subscriber<T extends Event> {

    default String id() {
        return this.getTypeName();
    }

    void subscribe(T event);

    default boolean match(T event) {
        return true;
    }

    default String getTypeName() {
        ResolvableType generic = ResolvableType.forClass(this.getClass()).as(Subscriber.class).getGeneric();
        return Objects.requireNonNull(generic.getRawClass()).getTypeName();
    }

}
