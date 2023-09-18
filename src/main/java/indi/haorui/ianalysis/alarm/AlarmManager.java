package indi.haorui.ianalysis.alarm;

import indi.haorui.ianalysis.hub.Hub;
import indi.haorui.ianalysis.hub.Subscriber;
import indi.haorui.ianalysis.model.Parameter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yang Hao.rui on 2023/9/14
 */

public class AlarmManager implements Subscriber<Parameter> {

    private final String name;
    private final List<String> matchKeys;

    private final Map<String, BigDecimal> parameters;

    public AlarmManager(String name, List<String> matchKeys){
        this.name = name;
        this.matchKeys = matchKeys;
        parameters = new HashMap<>(matchKeys.size() << 1);

        Hub.register(this);
    }

    @Override
    public void subscribe(Parameter event) {
        parameters.put(event.getKey(), event.getParam());
        if (parameters.size() == matchKeys.size()){
            //TODO Alarm
        }
    }

    @Override
    public boolean match(Parameter event) {
        return matchKeys.contains(event.getKey());
    }
}
