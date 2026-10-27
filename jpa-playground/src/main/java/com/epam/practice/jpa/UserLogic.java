package com.epam.practice.jpa;

import com.epam.practice.jpa.domain.Address;
import com.epam.practice.jpa.domain.Genre;
import com.epam.practice.jpa.domain.User;
import com.epam.practice.jpa.services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
@RequiredArgsConstructor
public class UserLogic implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLogic.class);

    public static final String USER_NAME = "Arnold Schwarzenegger";
    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        //testCreateUserAndAddress();
        //testFindUserByName();
        //testQueryUsersNameOnly();
        test();
    }

    private static User createTestUser() {
        User user = new User();
        user.setName(USER_NAME);
        user.setEmail("arni@gmail.com");

        Address address = new Address();
        address.setAddressLine("Hollywood Blvd");

        user.getAddresses().add(address);
        // TODO: test if the following line is necessary
        address.setOwner(user);

        return user;
    }

    private void testCreateUserAndAddress() {
        User user = createTestUser();

        LOGGER.info("Creating user: {}", user);
        user = userService.create(user);
        LOGGER.info("User has been created: {}", user);
    }

    private void testFindUserByName() {
        LOGGER.info("Finding user by name: {}", USER_NAME);
        User user = userService.findByName(USER_NAME);
        LOGGER.info("User has been found. Id: {}, name: {}", user.getId(), user.getName());

        // Try to uncomment the following line
        printAddress(user);
    }

    private void testQueryUsersNameOnly() {
        userService.queryUsersNameOnly()
                .forEach(userName -> LOGGER.info("User name: {}", userName));
    }

    private void test() {
        User user = new User();
        user.setName(USER_NAME);
        user.setEmail("arni@gmail.com");
        user.setGenre(Genre.MALE);

        Address address = new Address();
        address.setAddressLine("Hollywood Blvd");

        user.getAddresses().add(address);
        // TODO: test if the following line is necessary
        address.setOwner(user);

        user = userService.create(user);

        user.setEmail("email@email.com");
        user.getAddresses().get(0).setAddressLine("Hollywood Blvd 2");
        userService.updateById(user);

        test2(user);

        address =  userService.getAddressById(1L);
        System.out.println(address);
    }

    private void test2(User user) {
        user = userService.findByName(USER_NAME);

        printAddress(user);
    }

    private static void printAddress(User user) {
        if (!user.getAddresses().isEmpty()) {
            LOGGER.info("First address: {}", user.getAddresses().get(0));
        }
    }
}
