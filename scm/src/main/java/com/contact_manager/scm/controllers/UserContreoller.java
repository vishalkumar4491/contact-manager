package com.contact_manager.scm.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.contact_manager.scm.helper.Helper;


@Controller
@RequestMapping("/user")
public class UserContreoller {

    private Logger logger = LoggerFactory.getLogger(UserContreoller.class);

    //user dashboard page
    @RequestMapping(value = "/dashboard", method=RequestMethod.GET)
    public String userDashboard() {
        return "user/dashboard";        // It will return dashboard html page inside user folder
    }
    
    // user Profile page
    @RequestMapping(value = "/profile", method=RequestMethod.GET)
    public String userProfile(Authentication authentication) {

        String username = Helper.getEmailOfLoggedInUser(authentication);
        
        logger.info("User Logged in: {}", username);

        return "user/profile";        // It will return dashboard html page inside user folder
    }
    // user add contact page

    // user view contact 

    // user edit contacts

    // user delete contact
}
