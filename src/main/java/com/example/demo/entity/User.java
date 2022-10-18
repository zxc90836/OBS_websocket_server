package com.example.demo.entity;

import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@ToString
@Document(collection = "User")
public class User {
    private String youtubeAccount;
    private String userName;
    private String password;
    private String authCode;
    private Map<String,String> collaborate = new HashMap<>();


    public void addCollaborate(String name,String scope){
        collaborate.put(name,scope);
    }
    public String getYoutubeAccount() {
        return youtubeAccount;
    }
    public void setYoutubeAccount(String youtubeAccount) {
        this.youtubeAccount = youtubeAccount;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getAuthCode() {
        return authCode;
    }
    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
    public Map<String, String> getCollaborate() {
        return collaborate;
    }
    public void setCollaborate(Map<String, String> collaborate) {
        this.collaborate = collaborate;
    }
}
