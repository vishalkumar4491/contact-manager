package com.contact_manager.scm.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.contact_manager.scm.entities.Contact;
import com.contact_manager.scm.entities.User;

@Repository
public interface ContactRepo extends JpaRepository<Contact, String> {

    // for pagination
    Page<Contact> findByUser(User user, Pageable pageable);

    // find all the contact of given user
    
    // List<Contact> findByUser(User user);
    // it is also same as of above
    @Query("SELECT c FROM Contact c WHERE c.user.id = :userId")
    List<Contact> findByUserId(@Param("userId") String userId);

    // for searching according to Name with pagination too
    Page<Contact> findByUserAndNameContaining(User user, String name, Pageable pageable);

    // for searching according to Email with pagination too
    Page<Contact> findByUserAndEmailContaining(User user, String email, Pageable pageable);

    // for searching according to Phone Number with pagination too
    Page<Contact> findByUserAndPhoneNumberContaining(User user, String phone, Pageable pageable);

}
