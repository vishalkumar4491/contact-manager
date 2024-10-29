package com.contact_manager.scm.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.contact_manager.scm.entities.User;
import com.contact_manager.scm.helper.Helper;
import com.contact_manager.scm.services.UserService;

// This controller's APIs will be common for all the controllers
// This will run for all requests

@ControllerAdvice
public class RootController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userservice;

    // Model attribute is used for we get user information on all APIs of uesr
    @ModelAttribute
    public void addLoggedInUserInformation(Model model, Authentication authentication){
        if(authentication == null) return;
        logger.info("Adding model attribute");
        String username = Helper.getEmailOfLoggedInUser(authentication);
        logger.info("User Logged in: {}", username);
        // fetchinfg data from db
        User user = userservice.getUserByEmail(username);

        System.out.println(user.getName());
        System.out.println(user.getEmail());
        // if user came then user add other iwse null will add
        model.addAttribute("loggedInUser", user);

    }
}
