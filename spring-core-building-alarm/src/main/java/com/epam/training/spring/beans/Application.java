package com.epam.training.spring.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    private final BuildingEventReader buildingEventReader;
    public Application(BuildingEventReader buildingEventReader) {
        this.buildingEventReader = buildingEventReader;
    }

    public void run() {
        LOG.info("run() method has been called");
        buildingEventReader.readEvents();
        System.exit(0);
    }
}
