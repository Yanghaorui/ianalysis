package indi.haorui.ianalysis;

import lombok.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;

@SpringBootApplication
public class IanalysisApplication implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(IanalysisApplication.class, args);
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        IanalysisApplication.applicationContext = applicationContext;
    }

    public static void publishEvent(ApplicationEvent event){
        applicationContext.publishEvent(event);
    }

}
