package com.contact_manager.scm.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.contact_manager.scm.entities.Contact;
import com.contact_manager.scm.entities.User;

@Repository
public interface ContactRepo extends JpaRepository<Contact, String> {

    // find all the contact of given user
    List<Contact> findByUser(User user);

    // it is also same as of above
    @Query("SELECT c FROM Contact c WHERE c.user.id = :userId")
    List<Contact> findByUserId(@Param("userId") String userId);

}
