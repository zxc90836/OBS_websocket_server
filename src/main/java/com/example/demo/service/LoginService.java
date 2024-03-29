package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.LoginRepository;
import com.example.demo.youtubeAPI.*;
import com.google.api.services.youtube.model.PlaylistItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import javax.annotation.Resource;
import java.util.*;
import java.math.BigInteger;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.HashMap;


@Slf4j
@org.springframework.stereotype.Service
public class LoginService {

    private static final String COLLECTION_NAME = "Team";
    private static final String userCollection= "User";
    private static final String VideoCollection= "VideosData";
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
        team.addMember(new MemberPermission("owner",true,true,true));
        team.addMember(new MemberPermission("colab",true,true,true));
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
        team2.addMember(new MemberPermission("colab",true,true,true));
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
            newUser.addCollaborate(account,youtubeAccount);

            newTeam.setUserName(account);
            newTeam.setYoutubeAccount(youtubeAccount);
            newTeam.addMember(new MemberPermission(account,true,true,true));
            mongoTemplate.save(newUser);
            mongoTemplate.save(newTeam);
            result = mongoTemplate.findOne(query, User.class, userCollection);
            return result;
        }
        return null;
    }
    public Map<String,String> getColab(String account){//獲取加入的協作團隊
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
    public Map<String, Schedule> getSchedule(String team){
        Query query = new Query(Criteria.where("youtubeAccount").is(team));
        Team result = mongoTemplate.findOne(query, Team.class, COLLECTION_NAME);
        if(result != null){
            return result.getSchedule();
        }
        return null;
    }
    public Team getTeam(String teamName){
        Query query = new Query(Criteria.where("youtubeAccount").is(teamName));
        Team result = mongoTemplate.findOne(query, Team.class, COLLECTION_NAME);
        if(result != null){
            log.info("getTeam got it--------------------"+result);
            return result;
        }
        log.info("null--------------------");
        return null;
    }
    public boolean setSchedule(String team,Schedule newSchedule){
        Query query = new Query(Criteria.where("youtubeAccount").is(team));
        Update update = new Update();
        Team result = mongoTemplate.findOne(query, Team.class, COLLECTION_NAME);
        result.addSchedule(newSchedule.getDate(),newSchedule);
        update.set("schedule", result.getSchedule());
        log.info("got it--------------------"+result);
        if(result != null){
            mongoTemplate.updateFirst(query, update, Team.class);
            return true;
        }
        return false;
    }
    public boolean deleteSchedule(String team,String date){
        Query query = new Query(Criteria.where("youtubeAccount").is(team));
        Update update = new Update();
        Team result = mongoTemplate.findOne(query, Team.class, COLLECTION_NAME);
        result.deleteSchedule(date);
        update.set("schedule", result.getSchedule());
        log.info("got it--------------------"+result);
        if(result != null){
            mongoTemplate.updateFirst(query, update, Team.class);
            return true;
        }
        return false;
    }
    public Map<String,MemberPermission> getMember(String team){//獲取加入的協作團隊
        Query query = new Query(Criteria.where("youtubeAccount").is(team));
        Team result = mongoTemplate.findOne(query, Team.class, COLLECTION_NAME);
        if(result != null){
            log.info("got it--------------------"+result);
            return result.getMember();
        }
        log.info("null--------------------");
        return null;
    }
    public boolean addMember(MemberPermission memberPermission){
        Query query = new Query(Criteria.where("youtubeAccount").is(memberPermission.team));
        Query query2 = new Query(Criteria.where("userName").is(memberPermission.memberName));
        Update update = new Update();
        Update update2 = new Update();

        Team result = mongoTemplate.findOne(query, Team.class, COLLECTION_NAME);
        result.addMember(memberPermission);
        User result2 = mongoTemplate.findOne(query2, User.class, userCollection);
        result2.addCollaborate(result.getUserName(),memberPermission.team);

        update.set("member", result.getMember());
        update2.set("collaborate",result2.getCollaborate());
        log.info("got it--------------------"+result);
        if(result != null){
            mongoTemplate.updateFirst(query, update, Team.class);
            mongoTemplate.updateFirst(query2, update2, User.class);
            return true;
        }
        return false;
    }
    public boolean modifyPermission(MemberPermission memberPermission){
        Query query = new Query(Criteria.where("youtubeAccount").is(memberPermission.team));
        Update update = new Update();
        Team result = mongoTemplate.findOne(query, Team.class, COLLECTION_NAME);
        result.modifyMember(memberPermission);
        update.set("member", result.getSchedule());
        log.info("got it--------------------"+result);
        if(result != null){
            mongoTemplate.updateFirst(query, update, Team.class);
            return true;
        }
        return false;
    }
    public boolean deleteMember(MemberPermission memberPermission) {
        Query query = new Query(Criteria.where("youtubeAccount").is(memberPermission.team));
        Query query2 = new Query(Criteria.where("userName").is(memberPermission.memberName));
        Update update = new Update();
        Update update2 = new Update();

        Team result = mongoTemplate.findOne(query, Team.class, COLLECTION_NAME);
        result.deleteMember(memberPermission);
        User result2 = mongoTemplate.findOne(query2, User.class, userCollection);
        result2.deleteCollaborate(result.getUserName());

        update.set("member", result.getMember());
        update2.set("collaborate", result2.getCollaborate());
        log.info("got it--------------------" + result);
        if (result != null) {
            mongoTemplate.updateFirst(query, update, Team.class);
            mongoTemplate.updateFirst(query2, update2, User.class);
            return true;
        }
        return false;
    }
    public Video getVideoFromDB(String videoID){
        Query query = new Query(Criteria.where("_id").is(videoID));
        Video result = mongoTemplate.findOne(query, Video.class, VideoCollection);
        if(result!=null)
            return result;
        return null;
    }
    public List<Video> getAllVideoFromDB(String ytAccount){
        Query query = new Query(Criteria.where("ytAccount").is(ytAccount));
        List<Video> result = mongoTemplate.find(query, Video.class, VideoCollection);
        if(result!=null)
            return result;
        return null;
    }
    public Video getVideoData(String id){
        log.info("get videos--------------------" + id);
        Query query = new Query(Criteria.where("id").is(id));
        Video result = mongoTemplate.findOne(query, Video.class, VideoCollection);
        if(result == null){
            Video newVideo = getVideoInfo.getVideoInfo(id);
            mongoTemplate.save(newVideo);
            result = mongoTemplate.findOne(query, Video.class, VideoCollection);
            return result;
        }
        return null;
    }
    public String getAllVideoData(String ytAccount){

        log.info("get all my videos--------------------");
        List<PlaylistItem> myVideos = GetAllVideos.getAllVideos();

        Iterator<PlaylistItem> playlistEntries = myVideos.iterator();
        while (playlistEntries.hasNext()){
            PlaylistItem playlistItem = playlistEntries.next();
            Query query = new Query(Criteria.where("id").is(playlistItem.getContentDetails().getVideoId()));
            Video result = mongoTemplate.findOne(query, Video.class, VideoCollection);
            if(result == null){
                Video newVideo = getVideoInfo.getVideoInfo(playlistItem.getContentDetails().getVideoId());
                newVideo.setYtAccount(ytAccount);
                mongoTemplate.save(newVideo);
                result = mongoTemplate.findOne(query, Video.class, VideoCollection);
                if(result == null) return null;
            }
        }
        return myVideos.toString();
    }
    public String getComment(String ytAccount){
        log.info("get comments--------------------");
        Query query = new Query(Criteria.where("teamName").is(ytAccount));
        ExchangeData result = mongoTemplate.findOne(query, ExchangeData.class, "DataExchange");
        Update update = new Update();
        update.set("saveFlag", false);
        mongoTemplate.updateFirst(query, update, ExchangeData.class);
        result = mongoTemplate.findOne(query, ExchangeData.class, "DataExchange");

        while (result == null || result.isSaveFlag() == false){
            result = mongoTemplate.findOne(query, ExchangeData.class, "DataExchange");
        }
        return result.getCommentData();
    }
    public String getLiveChatMessage(String ytAccount){
        log.info("get liveChatMessage--------------------");

        Query query = new Query(Criteria.where("teamName").is(ytAccount));
        ExchangeData result = mongoTemplate.findOne(query, ExchangeData.class, "DataExchange");
        Update update = new Update();
        update.set("saveFlag", false);
        mongoTemplate.updateFirst(query, update, ExchangeData.class);
        result = mongoTemplate.findOne(query, ExchangeData.class, "DataExchange");

        while (result == null || result.isSaveFlag() == false){
            result = mongoTemplate.findOne(query, ExchangeData.class, "DataExchange");
        }
        log.info("StreamingChat：      " + result.toString());
        return result.getStreamingChat();
    }
    public String getSCDetail(String ytAccount){
        log.info("get SCDetail--------------------");

        Query query = new Query(Criteria.where("teamName").is(ytAccount));
        ExchangeData result = mongoTemplate.findOne(query, ExchangeData.class, "DataExchange");
        Update update = new Update();
        update.set("saveFlag", false);
        mongoTemplate.updateFirst(query, update, ExchangeData.class);
        result = mongoTemplate.findOne(query, ExchangeData.class, "DataExchange");

        while (result == null || result.isSaveFlag() == false){
            result = mongoTemplate.findOne(query, ExchangeData.class, "DataExchange");
        }
        return result.getSCDetail();
    }
    public String getRelatedVideo(String ytAccount){
        log.info("get relatedVideo--------------------");
        Query query = new Query(Criteria.where("teamName").is(ytAccount));
        ExchangeData result = mongoTemplate.findOne(query, ExchangeData.class, "DataExchange");
        Update update = new Update();
        update.set("saveFlag", false);
        mongoTemplate.updateFirst(query, update, ExchangeData.class);
        result = mongoTemplate.findOne(query, ExchangeData.class, "DataExchange");

        while (result == null || result.isSaveFlag() == false){
            result = mongoTemplate.findOne(query, ExchangeData.class, "DataExchange");
        }
        return result.getRelatedVideo();
    }
    public String getVideoHistory(String ytAccount){
        log.info("get videoHistory--------------------");
        Query query = new Query(Criteria.where("teamName").is(ytAccount));
        ExchangeData result = mongoTemplate.findOne(query, ExchangeData.class, "DataExchange");
        Update update = new Update();
        update.set("saveFlag", false);
        mongoTemplate.updateFirst(query, update, ExchangeData.class);
        result = mongoTemplate.findOne(query, ExchangeData.class, "DataExchange");

        while (result == null || result.isSaveFlag() == false){
            result = mongoTemplate.findOne(query, ExchangeData.class, "DataExchange");
        }
        return result.getVideoHistoryInfo();
    }
    public String getChannelHistory(String start,String end){
        log.info("get channelHistory--------------------");
        return GetVideoHistoryInfo.getVideoHistoryInfo(start,end);
    }
    public String addLiveChatModerators(String id){
        log.info("get liveChatModerators--------------------");
        return "done";
    }
    public String banLiveChatUser(String id, BigInteger time){
        log.info("ban LiveChatUser--------------------");
        return "done";
    }
    public String deleteLiveChatMessage(String id){
        log.info("delete LiveChatMessage--------------------");
        return "done";
    }
    public String getStreamingURL(String ytAccount){
        Query query = new Query(Criteria.where("teamName").is(ytAccount));
        ExchangeData result = mongoTemplate.findOne(query, ExchangeData.class, "DataExchange");
        Update update = new Update();
        update.set("saveFlag", false);
        mongoTemplate.updateFirst(query, update, ExchangeData.class);
        result = mongoTemplate.findOne(query, ExchangeData.class, "DataExchange");

        while (result == null || result.isSaveFlag() == false){
            result = mongoTemplate.findOne(query, ExchangeData.class, "DataExchange");

        }
        return result.getYtKey();
    }
    public String getVoteData(String ytAccount){
        Query query = new Query(Criteria.where("teamName").is(ytAccount));
        ExchangeData result = mongoTemplate.findOne(query, ExchangeData.class, "DataExchange");
        Update update = new Update();
        update.set("saveFlag", false);
        mongoTemplate.updateFirst(query, update, ExchangeData.class);
        result = mongoTemplate.findOne(query, ExchangeData.class, "DataExchange");

        while (result == null || result.isSaveFlag() == false){
            result = mongoTemplate.findOne(query, ExchangeData.class, "DataExchange");

        }
        return result.getVoteResult();
    }
    public String getChannelData(String ytAccount){
        Query query = new Query(Criteria.where("teamName").is(ytAccount));
        ExchangeData result = mongoTemplate.findOne(query, ExchangeData.class, "DataExchange");
        Update update = new Update();
        update.set("saveFlag", false);
        mongoTemplate.updateFirst(query, update, ExchangeData.class);
        result = mongoTemplate.findOne(query, ExchangeData.class, "DataExchange");

        while (result == null || result.isSaveFlag() == false){
            result = mongoTemplate.findOne(query, ExchangeData.class, "DataExchange");

        }
        return result.getChannelData();
    }
}
