package com.contact_manager.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/user")
public class UserContreoller {
    //user dashboard page
    @RequestMapping(value = "/dashboard", method=RequestMethod.GET)
    public String userDashboard() {
        return "user/dashboard";        // It will return dashboard html page inside user folder
    }
    
    // user Profile page
    @RequestMapping(value = "/profile", method=RequestMethod.GET)
    public String userProfile() {
        return "user/profile";        // It will return dashboard html page inside user folder
    }
    // user add contact page

    // user view contact 

    // user edit contacts

    // user delete contact
}
