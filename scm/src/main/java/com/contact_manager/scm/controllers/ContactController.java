package com.contact_manager.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.contact_manager.scm.entities.Contact;
import com.contact_manager.scm.entities.User;
import com.contact_manager.scm.forms.ContactForm;
import com.contact_manager.scm.helper.Helper;
import com.contact_manager.scm.helper.Message;
import com.contact_manager.scm.helper.MessageType;
import com.contact_manager.scm.services.ContactService;
import com.contact_manager.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;



@Controller
@RequestMapping("/user/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    // add contact 
    @GetMapping("/add")
    public String getContact(Model model) {
        ContactForm contactForm = new ContactForm();
        model.addAttribute("contactForm", contactForm);
        return "user/add_contact";
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String addContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result, Authentication authentication, HttpSession session) {

        // Validate the form
        if(result.hasErrors()){
            return "user/add_contact";
        }
        String username = Helper.getEmailOfLoggedInUser(authentication);

        User user = userService.getUserByEmail(username);

        // converting contactForm --> contact
        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setFavorite(contactForm.isFavorite());
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setWebsiteLink(contactForm.getWebsiteLink());

        contact.setUser(user);

        contactService.save(contact);

        session.setAttribute("message", 
        Message.builder().content("Successfully added a new contact").type(MessageType.green).build());
        
        return "redirect:/user/contact/add";
    }
    
    

}
