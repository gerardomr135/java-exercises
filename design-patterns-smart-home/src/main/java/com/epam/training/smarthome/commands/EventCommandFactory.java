package com.epam.training.smarthome.commands;

import com.epam.training.smarthome.controller.HomeController;

public class EventCommandFactory {
    private final HomeController homeController;

    public EventCommandFactory(HomeController homeController) {
        this.homeController = homeController;
    }

    public EventCommand createEventCommand(EventCommandType type) {
        return switch (type) {
            case ARRIVES_HOME -> new ArrivesHomeCommand(homeController);
            case GOING_HOME -> new GoingHomeCommand(homeController);
            case MOVEMENT -> new MovementCommand(homeController);
            case CHANGE_TO_HOLIDAY -> new ChangeToHolidayCommand(homeController);
            case CHANGE_TO_WORKING_DAY -> new ChangeToWorkingDayCommand(homeController);
        };
    }

}
