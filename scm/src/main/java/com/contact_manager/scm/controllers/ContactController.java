package com.contact_manager.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result,
            Authentication authentication, HttpSession session) {

        // Validate the form
        if (result.hasErrors()) {
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

    // view contacts
    // since we are not passing any value so it will directly run user/contact url
    @RequestMapping
    public String viewContacts(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "3") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model, Authentication authentication) {
        // Load all the user contacts
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);

        Page<Contact> pageContacts = contactService.getByUser(user, page, size, sortBy, direction);
        model.addAttribute("pageContacts", pageContacts);

        return "user/contacts";
    }

    // search handler

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String searchHandler(
            @RequestParam("searchType") String searchType,
            @RequestParam("searchWord") String searchWord,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "3") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model, Authentication authentication) {

        var user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));

        Page<Contact> pageContacts = contactService.searchByName(searchWord, page, size, sortBy, direction, user);
        if (searchType.equalsIgnoreCase("name")) {
            pageContacts = contactService.searchByName(searchWord, page, size, sortBy, direction, user);
        } else if (searchType.equalsIgnoreCase("email")) {
            pageContacts = contactService.searchByEmail(searchWord, page, size, sortBy, direction, user);
        } else if (searchType.equalsIgnoreCase("phoneNumber")) {
            pageContacts = contactService.searchByPhoneNumber(searchWord, page, size, sortBy, direction, user);
        }
        System.out.println("pageContacts" + pageContacts.toString());

        model.addAttribute("searchType", searchType);
        model.addAttribute("searchWord", searchWord);
        model.addAttribute("pageContacts", pageContacts);
        return "user/search";
    }

    // for view details of particular contact on clicking of eye button
    @GetMapping("/api/{id}")
    @ResponseBody
    // public String getContactDetails(@PathVariable String id, Model model) {
    public Contact getContactDetails(@PathVariable String id) {
        // Contact contact = contactService.getById(id);
        System.out.println("phuch gya");
        System.out.println("ID " + id);
        return contactService.getById(id);
        // model.addAttribute("contact", contact);
        // return "user/contact_details";
    }

    // delete contact
    @RequestMapping("/delete/{id}")
    public String deleteContact(@PathVariable String id) {
        contactService.delete(id);
        return "redirect:/user/contact";
    }

    // update contact form view
    @RequestMapping("/view/{id}")
    public String updateContactFormView(@PathVariable String id, Model model) {

        var contact = contactService.getById(id);
        ContactForm contactForm = new ContactForm();
        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setFavorite(contact.isFavorite());
        contactForm.setWebsiteLink(contact.getWebsiteLink());
        contactForm.setLinkedInLink(contact.getLinkedInLink());

        model.addAttribute("contactForm", contactForm);
        model.addAttribute("id", id);

        return "user/update_contact";
    }

    // update the contact details
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String requestMethodName(@PathVariable String id, @Valid @ModelAttribute ContactForm contactForm,  BindingResult result, Model model) {

        // Validate the form
        if (result.hasErrors()) {
            return "user/update_contact";
        }

        var contact2 = new Contact();

        contact2.setId(id);

        contact2.setName(contactForm.getName());
        contact2.setEmail(contactForm.getEmail());
        contact2.setPhoneNumber(contactForm.getPhoneNumber());
        contact2.setAddress(contactForm.getAddress());
        contact2.setDescription(contactForm.getDescription());
        contact2.setFavorite(contactForm.isFavorite());
        contact2.setWebsiteLink(contactForm.getWebsiteLink());
        contact2.setLinkedInLink(contactForm.getLinkedInLink());

        contactService.update(contact2);



        return "redirect:/user/contact/view/" + id;
    }

}
