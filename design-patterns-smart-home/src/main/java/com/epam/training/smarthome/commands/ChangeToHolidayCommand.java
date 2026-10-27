package com.epam.training.smarthome.commands;

import com.epam.training.smarthome.controller.HomeController;

public class ChangeToHolidayCommand extends EventCommand {
    protected ChangeToHolidayCommand(HomeController homeController) {
        super(homeController);
    }

    @Override
    public void execute() {
        homeController.onChangeToHoliday();
    }
}
