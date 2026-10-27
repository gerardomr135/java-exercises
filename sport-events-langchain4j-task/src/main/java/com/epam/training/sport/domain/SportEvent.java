package com.epam.training.sport.domain;

import dev.langchain4j.model.output.structured.Description;
import lombok.Data;

@Data
public class SportEvent {
    private String event;
    @Description("Sport type of the event")
    private String sportType;
    @Description("Date of the event")
    private String date;
    @Description("Player who won the event")
    private String player;
    @Description("Result of the event")
    private String result;
    @Description("Location of the event")
    private String location;

    public boolean isValid() {
        return event != null && !event.isBlank()
                && sportType != null && !sportType.isBlank()
                && date != null && !date.isBlank()
                && player != null && !player.isBlank()
                && result != null && !result.isBlank()
                && location != null && !location.isBlank();
    }
}
