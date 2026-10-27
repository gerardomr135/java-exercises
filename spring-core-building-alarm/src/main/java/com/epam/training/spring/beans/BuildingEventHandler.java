package com.epam.training.spring.beans;

import com.epam.training.spring.model.Alarm;
import com.epam.training.spring.model.BuildingEvent;
import com.epam.training.spring.model.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

// It's possible to only add this annotation @Component and spring will load this bean too.
public class BuildingEventHandler implements ApplicationListener<BuildingEvent> {
    private AlarmHandler alarmHandler;

    @Autowired
    public void setAlarmHandler(AlarmHandler alarmHandler) {
        this.alarmHandler = alarmHandler;
    }

    @Override
    public void onApplicationEvent(BuildingEvent event) {
        EventType eventType = event.getEventType();
        if (eventType == EventType.FIRE_DETECTION || eventType == EventType.TEMPERATURE_TOO_HIGH) {
            Alarm alarm = new Alarm("Call to Maintenance Technician", event.getLocation(),
                    event.getEventType().toString());
            alarmHandler.handleAlarm(alarm);
        }
    }
}
