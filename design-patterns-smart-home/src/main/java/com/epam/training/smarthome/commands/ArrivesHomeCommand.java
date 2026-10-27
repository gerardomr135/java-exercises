package com.epam.training.smarthome.commands;

import com.epam.training.smarthome.controller.HomeController;

public class ArrivesHomeCommand extends EventCommand {
    protected ArrivesHomeCommand(HomeController homeController) {
        super(homeController);
    }

    @Override
    public void execute() {
        homeController.onArriveHome();
    }
}
