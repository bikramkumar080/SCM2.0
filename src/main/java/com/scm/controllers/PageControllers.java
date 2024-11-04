package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.Services.UserService;
import com.scm.entities.User;
import com.scm.forms.UserForm;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class PageControllers {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(){
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(Model model) {
        System.out.println("home page handler");
        //sending data to view
        model.addAttribute("name","Welcome to SCM");
        return "home";
    }

    //about route
    @RequestMapping("/about")
    public String aboutPage(Model model) {
        System.out.println("about page handler");
        model.addAttribute("isLogin",false);
        return "about";
    }

    //services route
    @RequestMapping("/services")
    public String servicesPage(Model model) {
        System.out.println("services page handler");
        return "services";
    }

    //contact route
    @GetMapping("/contact")
    public String contactPage(Model model) {
        System.out.println("contact page handler");
        return "contact";
    }

    //login route
    @GetMapping("/login")
    public String loginPage(Model model) {
        System.out.println("login page handler");
        return "login";
    }

    //signup route
    @GetMapping("/register")
    public String signupPage(Model model) {
        System.out.println("signup page handler");

        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "register";
    }

    //success route
    @GetMapping("/success")
    public String successPage(Model model) {
        System.out.println("login page handler");
        return "success";
    }

    //processing register
    @RequestMapping(value = "/do-register",method = RequestMethod.POST)    
    public String processRegister(@Valid @ModelAttribute("userForm") UserForm userForm, BindingResult rBindingResult, HttpSession session) {
        System.out.println("processing register : " + userForm);

        if(rBindingResult.hasErrors()) {
            return "register";
        }
        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePic("static/images/telephone.png");

        userService.saveUser(user);
        System.out.println("Saved User");

        return "redirect:/success";
    }
}



