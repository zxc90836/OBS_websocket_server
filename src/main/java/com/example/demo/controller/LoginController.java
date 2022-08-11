package com.example.demo.controller;

import com.example.demo.VoteData;
import com.example.demo.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;


import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class LoginController {
    @Autowired
    private LoginService service = new LoginService();
    @GetMapping("/")//http://127.0.0.1:55304
    public String loginYTYT(){
        return "HELLO";
    }
    @GetMapping("/user")//http://127.0.0.1:55304/user
    public Object getUser(){
        return service.createTeam();
    }
    //回傳可選擇的團隊
    @GetMapping("/login")//http://127.0.0.1:55304/login?account=owner&password=password
    //http://127.0.0.1:55304/login?account=colab&password=password2
    public boolean login(@RequestParam(value = "account", defaultValue = "")String account,@RequestParam(value =
            "password",defaultValue = "")String password){

        return service.login(account,password);
    }
    @GetMapping("/getColab")//http://127.0.0.1:55304/getColab?account=owner
    public Map<String,String> getColab(@RequestParam(value = "account", defaultValue = "")String account){
        return service.getColab(account);
    }
}
