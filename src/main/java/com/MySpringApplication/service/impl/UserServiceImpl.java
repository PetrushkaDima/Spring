package com.MySpringApplication.service.impl;

import com.MySpringApplication.model.Role;
import com.MySpringApplication.model.User;
import com.MySpringApplication.repository.UserRepository;
import com.MySpringApplication.service.MailSender;
import com.MySpringApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private MailSender mailSender;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override
    public void updateProfile(User user, String email, String password) {
        boolean emailChange = email != null && !email.equals(user.getEmail()) || user.getEmail() != null && !user.getEmail().equals(email);
        if (emailChange)
            if (!password.isEmpty()) {
                user.setPassword(passwordEncoder.encode(password));
                user.setEmail(email);
                user.setActivationCode(UUID.randomUUID().toString());
            }
        if (emailChange) {
            send(user);
        }
        userRepository.save(user);
    }

    @Override
    public Iterable<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public boolean addUser(User user) {

        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null) {
            return false;
        }
        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        send(user);
        return true;
    }

    private void send(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format("Привет, %s. \n" +
                    "Клицкни сюда: http://localhost:8080/activate/%s, чтобы стать активным", user.getUsername(), user.getActivationCode());
            mailSender.send(user.getEmail(), "Код активации", message);
        }
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActivationCode(null);
        user.setActive(true);
        userRepository.save(user);

        return true;
    }

    @Override
    public void subscribe(User currentUser, User user) {
        user.getSubscribers().add(currentUser);
        userRepository.save(user);
    }

    @Override
    public void unsubscribe(User currentUser, User user) {
        user.getSubscribers().remove(currentUser);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

}
