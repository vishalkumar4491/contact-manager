package com.contact_manager.scm.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.contact_manager.scm.entities.Contact;
import com.contact_manager.scm.entities.User;

public interface ContactService {
    Contact save(Contact contact);

    Contact update(Contact contact);

    List<Contact> getAll();

    Contact getById(String id);

    void delete(String id);

    Page<Contact> searchByName(String name, int page, int size, String sortBy, String direction,  User user);
    Page<Contact> searchByEmail(String email, int page, int size, String sortBy, String direction,  User user);
    Page<Contact> searchByPhoneNumber(String phoneNumber, int page, int size, String sortBy, String direction,  User user);

    // get contacts by user id
    List<Contact> getByUserId(String userId);

    Page<Contact> getByUser(User user, int page, int size, String sortBy, String direction);
}
