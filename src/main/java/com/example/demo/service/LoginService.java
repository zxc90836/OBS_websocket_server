package com.example.demo.service;

import com.example.demo.entity.Team;
import com.example.demo.repository.LoginRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class LoginService {

    private static final String COLLECTION_NAME = "Team";
    @Resource
    MongoTemplate mongoTemplate;
    //LoginRepository repository;
    public Object createTeam(){
        Team team = new Team()
                .setUserName("owner")
                .setPassword("password")
                .setYoutubeAccount("00857027@email.ntou.edu.tw")
                .setCollaborate(new HashMap<>())
                .setMember(new HashMap<>());
        team.addCollaborate("owner","00857027@email.ntou.edu.tw");
        team.addMember("owner","all");
        team.addMember("colab","all");
        Team team2 = new Team()
                .setUserName("colab")
                .setPassword("password2")
                .setYoutubeAccount("0979710326a@gmail.com")
                .setCollaborate(new HashMap<>())
                .setMember(new HashMap<>());
        team2.addCollaborate("owner","00857027@email.ntou.edu.tw");
        team2.addCollaborate("colab","0979710326a@gmail.com");
        team2.addMember("colab","all");
        mongoTemplate.save(team2);
        return mongoTemplate.save(team);
    }
    public boolean login(String account,String password){
        Query query = new Query(Criteria.where("userName").is(account).and("password").is(password));
        Team result = mongoTemplate.findOne(query, Team.class, COLLECTION_NAME);
        if(result != null){
            log.info("got it--------------------"+result);
            Team temp = result;
            return true;
        }
        log.info("null--------------------");
        return false;
    }
    public Map<String,String> getColab(String account){
        Query query = new Query(Criteria.where("userName").is(account));
        Team result = mongoTemplate.findOne(query, Team.class, COLLECTION_NAME);
        if(result != null){
            log.info("got it--------------------"+result);
            Team temp = result;
            return temp.getCollaborate();
        }
        log.info("null--------------------");
        return null;
    }
    public static void main(String[] args) {
        LoginService l = new LoginService();
        l.createTeam();
    }
}
