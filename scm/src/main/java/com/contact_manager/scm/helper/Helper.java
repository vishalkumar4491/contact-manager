package com.contact_manager.scm.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {
    public static String getEmailOfLoggedInUser(Authentication authentication){

        // checking from which user is logged in i.e. email, gmail or github

        if(authentication instanceof OAuth2AuthenticationToken){
            // it means user is logged in via gmail or github

            var oAuth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;
            var clientId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

            var oauth2User = (OAuth2User)authentication.getPrincipal();
            String username = "";

            // checking is client Id from google or github
            if(clientId.equalsIgnoreCase("google")){
                // google
                System.out.println("Logged in with google");
                username = oauth2User.getAttribute("email").toString();

            }else{
                // github
                System.out.println("Logged in with github");

                username = oauth2User.getAttribute("email") != null ? oauth2User.getAttribute("email") : oauth2User.getAttribute("login").toString() + "@gmail.com";


            }
            return username;
        }else{
            // it means user logged in with gmail and password
            // in this case we get directly gmail via getName

            System.out.println("Logged in with gmail and password");

            return authentication.getName();
        }

    }
}
