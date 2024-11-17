package com.contact_manager.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.contact_manager.scm.entities.User;
import com.contact_manager.scm.forms.UserForm;
import com.contact_manager.scm.helper.Message;
import com.contact_manager.scm.helper.MessageType;
import com.contact_manager.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;



@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"","/","/home"})
    public String requestMethodName() {
        return "Home";
    }

    @RequestMapping("/about")
    public String aboutPage() {
        return "About";
    }

    @RequestMapping("/services")
    public String servicesPage() {
        return "services";
    }

    @RequestMapping("/contact")
    public String contactPage() {
        return "contact";
    }

    // This is for view the login page
    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }

    // This is for registration page
    @RequestMapping("/register")
    public String signupPage(Model model) {

        UserForm userForm = new UserForm();
        // userForm.setName("VK");
        model.addAttribute("userForm", userForm);
        return "register";
    }

    // processing register
    @RequestMapping(value="/do-register", method=RequestMethod.POST)
    public String processregister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult, HttpSession session){
        // fetch form data
        System.out.println(userForm);
        // validate form data
        // save to db

        // User user = User.builder()
        // .name(userForm.getName())
        // .email(userForm.getEmail())
        // .password(userForm.getEmail())
        // .about(userForm.getAbout())
        // .phoneNumber(userForm.getPhoneNumber())
        // .profilePic("https://www.google.com/url?sa=i&url=https%3A%2F%2Fstock.adobe.com%2Fsearch%3Fk%3Dpic&psig=AOvVaw3DqSCABBYScvwhLtkuYJND&ust=1729407144470000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCNDth-btmYkDFQAAAAAdAAAAABAO")
        // .build();

        if(rBindingResult.hasErrors()){
            return "register";
        }

        try {
            
            User user = new User();
            user.setName(userForm.getName());
            user.setEmail(userForm.getEmail());
            user.setPassword(userForm.getPassword());
            user.setAbout(userForm.getAbout());
            user.setPhoneNumber(userForm.getPhoneNumber());
            user.setProfilePic("https://www.google.com/url?sa=i&url=https%3A%2F%2Fstock.adobe.com%2Fsearch%3Fk%3Dpic&psig=AOvVaw3DqSCABBYScvwhLtkuYJND&ust=1729407144470000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCNDth-btmYkDFQAAAAAdAAAAABAO");
    
            user.setEnabled(false);
    
            User savedUser = userService.saveUser(user);
    
            System.out.println(savedUser);
    
            // message = "Registration Successfull"
    
            Message message = Message.builder().content("Registration Successful").type(MessageType.green).build();
    
            System.out.println("User Saved");
    
            session.setAttribute("message", message);
    
            // redirect
            return "redirect:/register";
        } catch (IllegalStateException e) {
            // Email exists but not verified
            Message message = Message.builder()
                    .content("Email already exists but is not verified. Please verify your email.")
                    .type(MessageType.yellow)
                    .build();
            session.setAttribute("message", message);

            return "redirect:/register";

        } catch (IllegalArgumentException e) {
            // Email exists and verified
            Message message = Message.builder()
                    .content("User with this email already exists.")
                    .type(MessageType.red)
                    .build();
            session.setAttribute("message", message);

            return "redirect:/register";
        }
        

    }
    
}
