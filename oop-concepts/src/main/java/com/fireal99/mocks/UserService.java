package com.fireal99.mocks;

import java.util.List;

public class UserService {
    private final UserRepo userRepo;
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<String> getUsers() {
        return userRepo.getUsers();
    }

    public String find(String user) {
        return userRepo.find(user);
    }
}
