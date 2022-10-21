package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("/user")//http://127.0.0.1:55304/user
    public Object getUser(){
        return service.createTeam();
    }
    //回傳可選擇的團隊
    @PostMapping("/login")//http://127.0.0.1:55304/login?account=owner&password=password
    //http://127.0.0.1:55304/login?account=colab&password=password2
    @ResponseBody
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
    @GetMapping("/getSchedule")//http://127.0.0.1:55304/getSchedule?team=00000@gmail.com
    public Map<String, Schedule> getSchedule(@RequestParam(value = "team", defaultValue = "")String team){
        return service.getSchedule(team);
    }
//    {
//        "team": "0979710326a@gmail.com",
//            "date": "2022-10-22",
//            "describe": "專題實作",
//            "streamSchedule": "要死了"
//
//    }
    @PostMapping("/setSchedule")//http://127.0.0.1:55304/setSchedule
    @ResponseBody
    public boolean setSchedule(@RequestBody SetScheduleData request){
        log.info("setSchedule"+request);
        Schedule s = new Schedule();
        s.setSchedule(request);
        return service.setSchedule(request.team,s);
    }
    @PostMapping("/deleteSchedule")//http://127.0.0.1:55304/deleteSchedule
    @ResponseBody
    public boolean deleteSchedule(@RequestBody SetScheduleData request){
        log.info("deleteSchedule"+request);
        return service.deleteSchedule(request.team,request.date);
    }
    @PostMapping("/addMember")//http://127.0.0.1:55304/addMember
    @ResponseBody
    public boolean addMember(@RequestBody MemberPermission request){
        log.info("addMember"+request);
        return service.addMember(request);
    }
    @PostMapping("/modifyPermission")//http://127.0.0.1:55304/modifyPermission
    @ResponseBody
    public boolean modifyPermission(@RequestBody MemberPermission request){
        log.info("modifyPermission"+request);
        return service.addMember(request);
    }
    @PostMapping("/deleteMember")//http://127.0.0.1:55304/deleteMember
    @ResponseBody
    public boolean deleteMember(@RequestBody MemberPermission request){
        log.info("deleteMember"+request);
        return service.deleteMember(request);
    }
}
