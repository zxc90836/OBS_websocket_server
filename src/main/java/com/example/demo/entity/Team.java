package com.example.demo.entity;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.HashMap;
import java.util.Map;

@Data
@ToString
@Accessors(chain = true)
@Document(collection = "Team")
public class Team {
    private String youtubeAccount;
    private String userName;
    private String password;
    private Map<String,String> member;
    private Map<String,String> collaborate;
    public void addMember(String name,String scope){
        member.put(name,scope);
    }
    public void addCollaborate(String name,String scope){
        collaborate.put(name,scope);
    }
}
