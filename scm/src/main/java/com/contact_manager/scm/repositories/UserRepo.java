package com.contact_manager.scm.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contact_manager.scm.entities.User;

@Repository
public interface UserRepo extends  JpaRepository<User, String>{
    // extra methods db related operations
    // custome query methods
    // custom finder methods

    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndPassword(String email, String password);
}
