package com.example.vms.service;

import com.example.vms.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    Optional<User> findUserById(Long id);
    Optional<User> findUserByUsername(String username);
    List<User> findAllUsers();
    User updateUser(User user);
    void deleteUserById(Long id);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}