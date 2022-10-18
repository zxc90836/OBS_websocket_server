package com.example.demo.service;

import com.example.demo.entity.Team;
import com.example.demo.entity.User;
import com.example.demo.repository.LoginRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class LoginService {

    private static final String COLLECTION_NAME = "Team";
    private static final String userCollection= "User";
    @Resource
    MongoTemplate mongoTemplate;
    //LoginRepository repository;
    public Object createTeam(){
        Team team = new Team();
        User user = new User();
        team.setUserName("owner");
        user.setUserName("owner");
        user.setPassword("password");
        user.setYoutubeAccount("00857027@email.ntou.edu.tw");
        team.setYoutubeAccount("00857027@email.ntou.edu.tw");
        user.setCollaborate(new HashMap<>());
        team.setMember(new HashMap<>());
        user.addCollaborate("owner","00857027@email.ntou.edu.tw");
        team.addMember("owner","all");
        team.addMember("colab","all");
        Team team2 = new Team();
        User user2 = new User();
        team2.setUserName("colab");
        user2.setUserName("colab");
        user2.setPassword("password2");
        user2.setYoutubeAccount("0979710326a@gmail.com");
        team2.setYoutubeAccount("0979710326a@gmail.com");
        user2.setCollaborate(new HashMap<>());
        team2.setMember(new HashMap<>());
        user2.addCollaborate("owner","00857027@email.ntou.edu.tw");
        user2.addCollaborate("colab","0979710326a@gmail.com");
        team2.addMember("colab","all");
        mongoTemplate.save(team2);
        mongoTemplate.save(user);
        mongoTemplate.save(user2);
        return mongoTemplate.save(team);
    }
    public User login(String account,String password){
        Query query = new Query(Criteria.where("userName").is(account).and("password").is(password));
        User result = mongoTemplate.findOne(query, User.class, userCollection);
        if(result != null){
            log.info("got it--------------------"+result);
            return result;
        }
        log.info("null--------------------");
        return null;
    }
    public User signUp(String account,String password,String youtubeAccount){
        log.info("sign up successful--------------------"+account);
        Query query = new Query(Criteria.where("userName").is(account).and("youtubeAccount").is(youtubeAccount));
        User result = mongoTemplate.findOne(query, User.class, userCollection);
        if(result == null){
            log.info("sign up successful--------------------"+account);
            Team newTeam = new Team();
            User newUser = new User();
            String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            String code = "";
            for (int i = 1; i <= 8; i++) {
                code += chars.charAt((int) (Math.random() * 62));
            }
            newUser.setAuthCode(code);
            newUser.setPassword(password);
            newUser.setUserName(account);
            newUser.setYoutubeAccount(youtubeAccount);
            newUser.addCollaborate(account,"all");
            newTeam.setUserName(account);
            newTeam.setYoutubeAccount(youtubeAccount);
            newTeam.addMember(account,"all");
            mongoTemplate.save(newUser);
            mongoTemplate.save(newTeam);
            result = mongoTemplate.findOne(query, User.class, userCollection);
            return result;
        }
        return null;

    }
    public Map<String,String> getColab(String account){
        Query query = new Query(Criteria.where("userName").is(account));
        User result = mongoTemplate.findOne(query, User.class, userCollection);
        if(result != null){
            log.info("got it--------------------"+result);
            User temp = result;
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
