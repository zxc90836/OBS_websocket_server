package com.example.demo.controller;

import com.example.demo.ClientMap;
import com.example.demo.VoteData;
import com.example.demo.entity.SignUpData;
import com.example.demo.entity.Team;
import com.example.demo.entity.User;
import com.example.demo.service.LoginService;
import com.example.demo.youtubeAPI.*;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.util.HashMap;
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

    @GetMapping("/get_streamingChat")//http://127.0.0.1:55304/Youtube_API/get_streamingChat
    //http://140.121.196.20:55304/Youtube_API/get_streamingChat
    public String getStreamingChat(){
        return service.getLiveChatMessage();
    }

    @GetMapping("/get_SC")//http://127.0.0.1:55304/Youtube_API/get_SC
    //http://140.121.196.20:55304/Youtube_API/get_SC
    public String getSC(){
        return service.getSCDetail();
    }

    @GetMapping("/get_relatedVideo")//http://127.0.0.1:55304/Youtube_API/get_relatedVideo?key=YL471T6LkMA
    //http://140.121.196.20:55304/Youtube_API/get_relatedVideo?key=YL471T6LkMA
    public String getRelatedVideo(@RequestParam(value = "key", defaultValue = "") String key){
        return service.getRelatedVideo(key);
    }

    @GetMapping("/get_videoHistory")//http://127.0.0.1:55304/Youtube_API/getChannelHistory?key=YL471T6LkMA
    //http://140.121.196.20:55304/Youtube_API/getChannelHistory?key=YL471T6LkMA
    public String getVideoHistory(@RequestParam(value = "id", defaultValue = "") String id,
                                  @RequestParam(value = "start", defaultValue = "") String start,
                                  @RequestParam(value = "end", defaultValue = "") String end){
        return service.getVideoHistory(id,start,end);
    }

    @GetMapping("/get_channelHistory")//http://127.0.0.1:55304/Youtube_API/getChannelHistory?key=YL471T6LkMA
    //http://140.121.196.20:55304/Youtube_API/getChannelHistory?key=YL471T6LkMA
    public String getChannelHistory(@RequestParam(value = "start", defaultValue = "") String start,
                                    @RequestParam(value = "end", defaultValue = "") String end){
        return service.getChannelHistory(start,end);
    }

    @PostMapping(value = "/add_LiveChatModerators")//http://127.0.0.1:55304/Youtube_API/addLiveChatModerators?id=
    //http://140.121.196.20:55304/Youtube_API/addLiveChatModerators?id=
    public String addLiveChatModerators(@RequestParam(value = "id", defaultValue = "") String id){
        return service.addLiveChatModerators(id);
    }

    @PostMapping(value = "/ban_LiveChatUser")//http://127.0.0.1:55304/Youtube_API/banLiveChatUser?id=
    //http://140.121.196.20:55304/Youtube_API/banLiveChatUser?id=
    public String banLiveChatUser(@RequestParam(value = "id", defaultValue = "") String id,
                                  @RequestParam(value = "time", defaultValue = "") BigInteger time){
        return service.banLiveChatUser(id,time);
    }

    @PostMapping(value = "/delete_LiveChatMessage")//http://127.0.0.1:55304/Youtube_API/deleteLiveChatMessage?id=
    //http://140.121.196.20:55304/Youtube_API/deleteLiveChatMessage?id=
    public String deleteLiveChatMessage(@RequestParam(value = "id", defaultValue = "") String id){
        return service.deleteLiveChatMessage(id);
    }

    @GetMapping("/get_video")//http://127.0.0.1:55304/Youtube_API/get_video?key=YL471T6LkMA
    //http://140.121.196.20:55304/Youtube_API/get_video?key=YL471T6LkMA
    public Video getVideo(@RequestParam(value = "key", defaultValue = "") String key){
        return service.getVideoData(key);
    }
    @GetMapping("/get_myVideos")//http://127.0.0.1:55304/Youtube_API/get_myVideos
    //http://140.121.196.20:55304/Youtube_API/get_myVideos
    public String getMyVideos(){
        return service.getAllVideoData();
    }

    @GetMapping("/get_comments")//http://127.0.0.1:55304/Youtube_API/get_myVideos
    //http://140.121.196.20:55304/Youtube_API/get_myVideos
    public String getComments(@RequestParam(value = "key", defaultValue = "") String key){
        return service.getComment(key);
    }
    @PostMapping(value = "/start_vote") //http://127.0.0.1:55304/OBS_websocket/start_vote
    public String startVote(@RequestBody VoteData voteData) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        ClientMap.sendMSGToOBSServer(voteData.getPollAccount(),"startVote " + mapper.writeValueAsString(voteData));
        return "success";
    }
    @GetMapping("/get_voteResult")//http://127.0.0.1:55304/OBS_websocket/get_voteResult"
    //http://140.121.196.20:55304/OBS_websocket/get_scenes?key=4908795
    public String getVoteResult(@RequestBody VoteData voteData){
        ClientMap.sendMSGToOBSServer(voteData.getPollAccount(),"getVoteData");
        return "success";
    }
    /*
    public static void main(String args[]) throws IOException {
        VoteData v = new VoteData();
        v.setQuestion("今天幾點睡");
        v.setTimeLimit(60);
        v.setPollAccount("00857027@email.ntou.edu.tw");
        Map<String,String> m = new HashMap<>();
        m.put("A","1");
        m.put("B","2");
        m.put("C","3");
        v.setLegalResponse(m);

        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(v));
    }
*/
}
