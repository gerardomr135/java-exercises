package com.epam.training.smarthome.commands;

import com.epam.training.smarthome.controller.HomeController;

public class GoingHomeCommand extends EventCommand {
    protected GoingHomeCommand(HomeController homeController) {
        super(homeController);
    }

    @Override
    public void execute() {
        homeController.onGoingHome();
    }
}
