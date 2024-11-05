package com.scm.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.Services.UserService;
import com.scm.config.OauthAuthenticationSuccessHandler;
import com.scm.entities.User;
import com.scm.helpers.Helper;

@ControllerAdvice
public class RootController {

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void loggedInUser(Model model, Authentication authentication){
        if(authentication == null) return;
        String email = Helper.getEmailOfLoggedInUser(authentication);
        Logger logger = LoggerFactory.getLogger(OauthAuthenticationSuccessHandler.class);
        logger.info("This is logged in user " + email);

        User user = userService.getUserByEmail(email);
        logger.info("This is logged in user " + user.getName());

        model.addAttribute("loggedInUser", user);
    }

}
