package com.epam.training.smarthome.commands;

import com.epam.training.smarthome.controller.HomeController;

public class MovementCommand extends EventCommand {

    protected MovementCommand(HomeController homeController) {
        super(homeController);
    }

    @Override
    public void execute() {
        homeController.onMovement();
    }
}
