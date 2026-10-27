package com.epam.training.spring;

import com.epam.training.spring.beans.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application.properties")
@ComponentScan
@Configuration
public class SpringConfig {
    @Bean(name = "AlarmWriter")
    public FileWriterAlarmHandler AlarmWriter() {
        return new FileWriterAlarmHandler();
    }

    @Bean
    public BuildingEventReader  buildingEventReader() {
        return new BuildingEventReader();
    }

    @Bean
    public BeanListerBeanPostProcessor beanListerBeanPostProcessor() {
        return new BeanListerBeanPostProcessor();
    }

    @Bean
    public Application application() {
        return new Application(buildingEventReader());
    }

    @Bean
    public BuildingEventHandler buildingEventHandler() {
        return new BuildingEventHandler();
    }
}
