package com.contact_manager.scm.services;

import java.util.List;
import java.util.Optional;

import com.contact_manager.scm.entities.User;

public interface UserService {
    User saveUser(User user);
    Optional<User> getUserById(String Id);
    User getUserByEmail(String email);
    Optional<User> updateUser(User user);
    void deleteUser(String id);
    boolean isUserExist(String userId);
    boolean isUserExistByEmail(String email);
    List<User> getAllUsers();
}
