package com.contact_manager.scm.services;

import java.util.List;

import com.contact_manager.scm.entities.Contact;

public interface ContactService {
    Contact save(Contact contact);

    Contact update(Contact contact);

    List<Contact> getAll();

    Contact getById(String id);

    void delete(String id);

    List<Contact> search(String name, String email, String phoneNumber);

    // get contacts by user id
    List<Contact> getByUserId(String userId);
}
