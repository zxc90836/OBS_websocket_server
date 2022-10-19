package com.example.demo.entity;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.HashMap;
import java.util.Map;


@ToString
@Document(collection = "Team")
public class Team {
    private String youtubeAccount;
    private String userName;
    private Map<String,String> member = new HashMap<>();
    private Schedule schedule;
    public void addMember(String name,String scope){
        member.put(name,scope);
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
    public Map<String, String> getMember() {
        return member;
    }
    public void setMember(Map<String, String> member) {
        this.member = member;
    }
}
