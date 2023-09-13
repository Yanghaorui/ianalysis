package indi.haorui.ianalysis.model.cpu;

import indi.haorui.ianalysis.actor.ActorSystem;
import indi.haorui.ianalysis.actor.BaseActor;
import indi.haorui.ianalysis.hub.Hub;
import indi.haorui.ianalysis.hub.Subscriber;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Yang Hao.rui on 2023/8/31
 */
@Service
public class CPUManager implements Subscriber<CPU> {

    private static final Map<String, CPU> CPU_MAP = new ConcurrentHashMap<>();

    @PostConstruct
    public void init(){
        Hub.subscribe(this);
        ActorSystem.register(new BaseActor() {
            @Override
            public void act() {
                if (!CPU_MAP.isEmpty()){
                    System.out.println(CPU_MAP);
                }
            }
        });
    }

    @Override
    public void subscribe(CPU event) {
        System.out.println(event);
    }
}
