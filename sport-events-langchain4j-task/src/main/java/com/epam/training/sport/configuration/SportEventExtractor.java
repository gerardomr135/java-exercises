package com.epam.training.sport.configuration;

import com.epam.training.sport.domain.SportEvent;

public interface SportEventExtractor {
    SportEvent extractPersonFrom(String text);
}
