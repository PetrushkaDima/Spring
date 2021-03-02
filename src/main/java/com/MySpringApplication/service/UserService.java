package com.MySpringApplication.service;

import com.MySpringApplication.model.User;

public interface UserService {
    void updateProfile(User user, String email, String password);

    Iterable<User> getAllUser();
    boolean addUser(User user);
    User getUserByUsername(String username);

    boolean activateUser(String code);

    void subscribe(User currentUser, User user);

    void unsubscribe(User currentUser, User user);
}
