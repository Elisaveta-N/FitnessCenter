package org.example.service;


import lombok.extern.slf4j.Slf4j;
import org.example.model.User;
import org.example.repo.UserRepository;
import org.example.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public Optional<User> findByLogin(String currentUserName) {

        return userRepository.findByLogin(currentUserName);
    }

    public Optional<User> findById(long id) {

        return userRepository.findById(id);
    }

    @Transactional
    public boolean update(User user) {
        try {
            log.info("Update user by id = {}", user.getId());
            userRepository.save(user);
            return true;
        }
        catch (Exception e)  {
            log.error("Failed to update user by id: {}, {}", user.getId(),e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean update(User user, long id) {
        try {
            log.info("Update user by id = {}", id);
            user.setId(id);
            userRepository.save(user);
            return true;
        }
        catch (Exception e)  {
            log.error("Failed to update group user by id: " + e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean create(User user) {
        try {
            log.info("Create user login: {}", user.getLogin());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(Role.USER);

            userRepository.save(user);
        }
        catch (Exception e)  {
            log.error("Failed to create user: " + e.getMessage());
            return false;
        }
        return true;
    }
}
