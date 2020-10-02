package com.MySpringApplication.service;

import com.MySpringApplication.model.User;

public interface UserService {
    Iterable<User> getAllUser();
    void saveUser(User user);
    User getUserByUsername(String username);
}
