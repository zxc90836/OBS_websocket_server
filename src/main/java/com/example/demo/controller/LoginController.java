package com.example.demo.controller;

import com.example.demo.VoteData;
import com.example.demo.entity.SignUpData;
import com.example.demo.entity.Team;
import com.example.demo.entity.User;
import com.example.demo.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Map;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class LoginController {
    @Autowired
    private LoginService service = new LoginService();
    @GetMapping("/user")//http://127.0.0.1:55304/user
    public Object getUser(){
        return service.createTeam();
    }
    //回傳可選擇的團隊
    @GetMapping("/login")//http://127.0.0.1:55304/login?account=owner&password=password
    //http://127.0.0.1:55304/login?account=colab&password=password2
    public User login(@RequestBody User request){
        User user = service.login(request.getUserName(),request.getPassword());
        if(user == null){
            return null;
        }
        return user;
    }
    @PostMapping("/signUp")//http://127.0.0.1:55304/signUp
    @ResponseBody
    public User signUp(@RequestBody SignUpData request){
        log.info("sign up");
        return service.signUp(request.userName,request.password,request.youtubeAccount);
    }
    @GetMapping("/getColab")//http://127.0.0.1:55304/getColab?account=owner
    public Map<String,String> getColab(@RequestParam(value = "account", defaultValue = "")String account){
        return service.getColab(account);
    }
}
