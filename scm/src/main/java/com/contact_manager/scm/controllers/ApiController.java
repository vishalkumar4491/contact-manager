package com.contact_manager.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.contact_manager.scm.entities.Contact;
import com.contact_manager.scm.services.ContactService;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private ContactService contactService;

    @GetMapping("/contact/{id}")
    // public String getContactDetails(@PathVariable String id, Model model) {
    public Contact getContactDetails(@PathVariable String id) {
        // Contact contact = contactService.getById(id);
        System.out.println("phuch gya");
        System.out.println("ID " + id);
         return contactService.getById(id);
        // model.addAttribute("contact", contact);
        // return "user/contact_details";
    }
}
