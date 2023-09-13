package indi.haorui.ianalysis.shell.collector;

import cn.hutool.json.JSONObject;
import indi.haorui.ianalysis.hub.Event;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Yang Hao.rui on 2023/8/31
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Output extends Event {

    private JSONObject fields;

    private String name;

    private JSONObject tags;

    private long timestamp; //s

    public <T> T getField(String name, Class<T> clazz){
        return this.getFields().get(name, clazz);
    }

    public <T> T getTag(String name, Class<T> clazz){
        return this.getTags().get(name, clazz);
    }


}
