package indi.haorui.ianalysis.model;

import org.springframework.util.Assert;

/**
 * Created by Yang Hao.rui on 2023/8/29
 */
public record Tag<T>(T tag) implements ValueObject<Tag<T>> {

    public Tag {
        Assert.notNull(tag, "tag is null");
    }

    public  static <T> Tag<T> of(T t){
        return new Tag<>(t);
    }

    @Override
    public boolean sameValueAs(Tag<T> other) {
        return tag.equals(other.tag);
    }
}
