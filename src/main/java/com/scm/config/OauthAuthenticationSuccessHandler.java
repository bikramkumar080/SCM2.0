package com.scm.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.entities.Providers;
import com.scm.entities.User;
import com.scm.helpers.AppConstants;
import com.scm.repositories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OauthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    Logger logger = LoggerFactory.getLogger(OauthAuthenticationSuccessHandler.class);

    @Autowired
    private UserRepo userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        logger.info("OauthAuthenticationSuccessHandler");
        

        //verify provider

        var oAuth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;
        String authorizedClientRegistrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

        logger.info(authorizedClientRegistrationId);

        var oauth2User = (DefaultOAuth2User)authentication.getPrincipal();
        User dbuser = new User();

        dbuser.setUserId(UUID.randomUUID().toString());
        dbuser.setRoleList(List.of(AppConstants.ROLE_USER));
        dbuser.setEmailVerified(true);
        dbuser.setEnabled(true);



        if(authorizedClientRegistrationId.equalsIgnoreCase("google")) {
            dbuser.setEmail(oauth2User.getAttribute("email").toString());
            dbuser.setProfilePic(oauth2User.getAttribute("picture").toString());
            dbuser.setName(oauth2User.getAttribute("name").toString());
            dbuser.setProviderUserId(oauth2User.getName());
            dbuser.setProvider(Providers.GOOGLE);
            dbuser.setAbout("I am a new goggle user");

        }
        else if(authorizedClientRegistrationId.equalsIgnoreCase("github")) {
            String email = oauth2User.getAttribute("email") != null ? oauth2User.getAttribute("email").toString() : oauth2User.getAttribute("login").toString() + "@github.com";
            String picture = oauth2User.getAttribute("avatar_url").toString();
            String name = oauth2User.getAttribute("login").toString();
            String providerUserId = oauth2User.getName();

            dbuser.setEmail(email);
            dbuser.setProfilePic(picture);
            dbuser.setName(name);
            dbuser.setProviderUserId(providerUserId);
            dbuser.setProvider(Providers.GITHUB);
            dbuser.setAbout("I am a github user");
        }
        else {
            logger.info("Provider not supported");
        }

        // DefaultOAuth2User user = (DefaultOAuth2User)authentication.getPrincipal();
        

        // String email = user.getAttribute("email").toString();
        // String name = user.getAttribute("name").toString();
        // String picture = user.getAttribute("picture").toString();

        // User dbuser = new User();
        // dbuser.setEmail(email);
        // dbuser.setName(name);
        // dbuser.setProfilePic(picture);
        // dbuser.setEmailVerified(true);
        // dbuser.setPassword("password");
        // dbuser.setUserId(UUID.randomUUID().toString());
        // dbuser.setProvider(Providers.GOOGLE);
        // dbuser.setEnabled(true);
        // dbuser.setProviderUserId(user.getName());
        // dbuser.setRoleList(List.of(AppConstants.ROLE_USER));
        // dbuser.setAbout("I am a new goggle user");

        User user2 = userRepo.findByEmail(dbuser.getEmail()).orElse(null);
        if ((user2==null)) {
            userRepo.save(dbuser);
            logger.info("User saved in the DB successfully" + dbuser.getEmail()); 
        }

        response.sendRedirect("/user/profile");
    }

}
