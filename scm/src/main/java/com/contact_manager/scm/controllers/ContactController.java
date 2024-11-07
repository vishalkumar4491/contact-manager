package com.contact_manager.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/user/contact")
public class ContactController {

    // add contact 
    @GetMapping("/add")
    public String getContact() {
        return "user/add_contact";
    }
    

}
