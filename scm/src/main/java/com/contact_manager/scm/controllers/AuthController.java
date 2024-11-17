package com.contact_manager.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.contact_manager.scm.entities.User;
import com.contact_manager.scm.repositories.UserRepo;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepo userRepo;
    // verify email
    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam("token") String token) {
        User user =userRepo.findByEmailVerificationToken(token).orElse(null);
        if(user != null) {
            if(user.getEmailVerificationToken().equals(token)){

                user.setEmailVerificationToken(null);
                user.setEmailVerified(true);
                user.setEnabled(true);
                userRepo.save(user);
                return "redirect:/login?verified=true";
            }
            return "redirect:/login?verified=false";

        }
        return "redirect:/login?verified=false";
    }
}
