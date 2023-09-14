package indi.haorui.ianalysis.model;

import lombok.NonNull;

/**
 * Created by Yang Hao.rui on 2023/8/29
 */
public record Tag<T>(@NonNull T tag) implements ValueObject<Tag<T>> {


    public static <T> Tag<T> of(@NonNull T t) {
        return new Tag<>(t);
    }

    @Override
    public boolean sameValueAs(Tag<T> other) {
        return tag.equals(other.tag);
    }
}
