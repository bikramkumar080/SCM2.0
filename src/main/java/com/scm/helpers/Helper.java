package com.scm.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {


    public static String getEmailOfLoggedInUser(Authentication authentication) {
        
        if(authentication instanceof OAuth2AuthenticationToken) {

            var oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            var clientId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

            if(clientId.equalsIgnoreCase("google")) {
                OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
                return oAuth2User.getAttribute("email").toString();
            }
            else if(clientId.equalsIgnoreCase("github")) {
                OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
                String email = oAuth2User.getAttribute("email") != null ? oAuth2User.getAttribute("email").toString() : oAuth2User.getAttribute("login").toString() + "@github.com";
                return email;
            }
        }else {
                return authentication.getName().toString();
            }
                    return "No email found";
        

    }

}
