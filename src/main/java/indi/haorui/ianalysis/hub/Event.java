package indi.haorui.ianalysis.hub;

import lombok.Data;

import java.time.Instant;

/**
 * Created by Yang Hao.rui on 2023/8/14
 */
@Data
public class Event<T> {

    private T t;

    private Instant timestamp;

}
