package com.example.demo.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class LoginController {
    @GetMapping("/")//http://127.0.0.1:55304
    public String loginYTYT(){
        return "HELLO";
    }
    @GetMapping("/user")//http://127.0.0.1:55304/user
    public Map<String, Object> getUser(OAuth2AuthenticationToken oAuth2AuthenticationToken){
        return oAuth2AuthenticationToken.getPrincipal().getAttributes();
    }
    @GetMapping("/name")//http://127.0.0.1:55304/name
    public String getUser(){
        return "當前登入使用者資訊：";
    }
}
