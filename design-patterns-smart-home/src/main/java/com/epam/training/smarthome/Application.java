package com.epam.training.smarthome;

import com.epam.training.smarthome.controller.HomeController;
import com.epam.training.smarthome.domain.observer.MessageObserver;

public class Application {

    public static void main(String[] args) {
        Application application = new Application();
        application.run();
    }

    private void run() {
        var messageObserver = new MessageObserver();
        var homeController = new HomeController.HomeControllerBuilder(messageObserver).build();

        System.out.println("--> Change to holiday event");
        var offset = messageObserver.getMessages().size();
        homeController.onChangeToHoliday();
        messageObserver.printOffset(offset);
        System.out.println();

        System.out.println("--> Going home event");
        offset = messageObserver.getMessages().size();
        homeController.onGoingHome();
        messageObserver.printOffset(offset);
        System.out.println();

        System.out.println("--> Movement event");
        offset = messageObserver.getMessages().size();
        homeController.onMovement();
        messageObserver.printOffset(offset);
        System.out.println();

        System.out.println("--> Arrive home event");
        offset = messageObserver.getMessages().size();
        homeController.onArriveHome();
        messageObserver.printOffset(offset);
        System.out.println();

        System.out.println("--> Movement event");
        offset = messageObserver.getMessages().size();
        homeController.onMovement();
        messageObserver.printOffset(offset);
        System.out.println();

        System.out.println("--> Change to working day event");
        offset = messageObserver.getMessages().size();
        homeController.onChangeToWorkingDay();
        messageObserver.printOffset(offset);
        System.out.println();

        System.out.println("--> Going home event");
        offset = messageObserver.getMessages().size();
        homeController.onGoingHome();
        messageObserver.printOffset(offset);
        System.out.println();

        System.out.println("--> Arrive home event");
        offset = messageObserver.getMessages().size();
        homeController.onArriveHome();
        messageObserver.printOffset(offset);
        System.out.println();

        System.out.println("--> Movement event");
        offset = messageObserver.getMessages().size();
        homeController.onMovement();
        messageObserver.printOffset(offset);
        System.out.println();

        System.out.println("all messages dispatched by the devices:");
        messageObserver.getMessages().forEach(System.out::println);
    }
}
