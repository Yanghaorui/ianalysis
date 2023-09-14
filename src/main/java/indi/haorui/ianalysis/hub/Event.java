package indi.haorui.ianalysis.hub;

import lombok.Data;

import java.time.Instant;

/**
 * Created by Yang Hao.rui on 2023/8/14
 */
@Data
public class Event<T> {

    private T data;

    private Instant timestamp;

    public Event(T t, Instant timestamp){
        this.data = t;
        this.timestamp = timestamp;
    }

//    @Override
//    public String toString() {
//        return "Event{" +
//                ", timestamp=" + timestamp +
//                '}';
//    }
}
