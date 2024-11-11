package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.Services.ContactService;
import com.scm.Services.UserService;
import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.helpers.Helper;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/user/contacts")
public class ContactController {


    @Autowired
    private ContactService contactService;
    @Autowired
    private UserService userService;
    
    @RequestMapping("/add")
    public String addContacts(Model model) {
        ContactForm contactForm = new ContactForm();
        model.addAttribute("contactForm", contactForm);
        contactForm.setFavorite(true);
        return "user/add_contacts";
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String saveContacts(@Valid @ModelAttribute ContactForm contactForm, BindingResult result, Authentication authentication) {

        if(result.hasErrors()) {
            return "user/add_contacts";
        }

        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);

        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setFavorite(contactForm.isFavorite());
        contact.setUser(user);
        contactService.save(contact);
        System.err.println(contactForm);
        return "redirect:/user/contacts/add";
    }

    @RequestMapping
    public String viewContact(@RequestParam(value = "page", defaultValue= "0")int page,@RequestParam(value = "size", defaultValue = "2")int size,@RequestParam(value = "sortBy", defaultValue = "name")String sortBy,@RequestParam(value = "direction", defaultValue = "asc")String direction,  Model model, Authentication authentication) {

        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);

        Page<Contact> pageContacts = contactService.getByUser(user,page,size,sortBy,direction);

        model.addAttribute("pageContacts", pageContacts);
                return "/user/contacts";
            }
    

}
