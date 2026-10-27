package com.epam.training.sport.service;

import com.epam.training.sport.configuration.SportEventExtractor;
import com.epam.training.sport.domain.SportEvent;
import org.springframework.stereotype.Service;

@Service
public class EventExtractionService {
    private final SportEventExtractor sportEventExtractor;
    public EventExtractionService(SportEventExtractor sportEventExtractor) {
        this.sportEventExtractor = sportEventExtractor;
    }

    public SportEvent extract(String eventText) throws Exception {
        SportEvent sportEvent =  sportEventExtractor.extractPersonFrom(eventText);

        if (!sportEvent.isValid()) {
             throw new Exception("Invalid sport event");
        }

        return sportEvent;
    }
}
