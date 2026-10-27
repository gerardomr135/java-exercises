package com.fireal99.mocks;

import java.util.ArrayList;
import java.util.List;

public class UserRepo {
    private static final List<String> users = new ArrayList<>();
    static {
        users.add("User1");
        users.add("User2");
    }

    public List<String> getUsers() {
        return users;
    }

    public String find(String user) {
        return users.get(users.indexOf(user));
    }
}
