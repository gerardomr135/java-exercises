package com.epam.training.smarthome.commands;

import com.epam.training.smarthome.controller.HomeController;

public class ChangeToWorkingDayCommand extends EventCommand {
    protected ChangeToWorkingDayCommand(HomeController homeController) {
        super(homeController);
    }

    @Override
    public void execute() {
        homeController.onChangeToWorkingDay();
    }
}
