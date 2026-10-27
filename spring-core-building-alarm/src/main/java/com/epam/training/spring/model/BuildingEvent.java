package com.epam.training.spring.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class BuildingEvent extends ApplicationEvent {
    private EventType eventType;
    private OffsetDateTime time;
    private String location;
    private String message;

    public BuildingEvent(Object source) {
        super(source);
    }
}
