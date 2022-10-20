package com.example.demo.controller;


import com.alibaba.fastjson.JSON;
import com.example.demo.VoteData;
import com.example.demo.service.LoginService;
import com.example.demo.service.YoutubeDataService;
import com.example.demo.youtubeAPI.*;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.ui.Model;
import com.example.demo.ClientMap;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/Youtube_API")
//http://180.177.24.44:55304/Youtube_API
public class YoutubeController {

    private LoginService service = new LoginService();



    @GetMapping("/get_comment")//http://127.0.0.1:55304/Youtube_API/get_comment?key=YL471T6LkMA
    //http://140.121.196.20:55304/Youtube_API/get_comment?key=YL471T6LkMA
    public String getComment(@RequestParam(value = "key", defaultValue = "") String key){
        return getComment(key);
    }

    @GetMapping("/get_streamingChat")//http://127.0.0.1:55304/Youtube_API/get_streamingChat
    //http://140.121.196.20:55304/Youtube_API/get_streamingChat
    public String getStreamingChat(){
        return GetLiveChatOnce.getLiveChatOnce();
    }

    @GetMapping("/get_SC")//http://127.0.0.1:55304/Youtube_API/get_SC
    //http://140.121.196.20:55304/Youtube_API/get_SC
    public String getSC(){
        return GetSCDetails.getSCDetails();
    }

    @GetMapping("/get_relatedVideo")//http://127.0.0.1:55304/Youtube_API/get_relatedVideo?key=YL471T6LkMA
    //http://140.121.196.20:55304/Youtube_API/get_relatedVideo?key=YL471T6LkMA
    public String getRelatedVideo(@RequestParam(value = "key", defaultValue = "") String key){
        return getRelatedVideo(key);
    }

    @GetMapping("/get_videoHistory")//http://127.0.0.1:55304/Youtube_API/getChannelHistory?key=YL471T6LkMA
    //http://140.121.196.20:55304/Youtube_API/getChannelHistory?key=YL471T6LkMA
    public String getVideoHistory(@RequestParam(value = "id", defaultValue = "") String id,
                                  @RequestParam(value = "start", defaultValue = "") String start,
                                  @RequestParam(value = "end", defaultValue = "") String end){
        return GetVideoHistoryInfo.getVideoHistoryInfo(id,start,end);
    }

    @GetMapping("/get_channelHistory")//http://127.0.0.1:55304/Youtube_API/getChannelHistory?key=YL471T6LkMA
    //http://140.121.196.20:55304/Youtube_API/getChannelHistory?key=YL471T6LkMA
    public String getChannelHistory(@RequestParam(value = "start", defaultValue = "") String start,
                                    @RequestParam(value = "end", defaultValue = "") String end){
        return GetVideoHistoryInfo.getVideoHistoryInfo(start,end);
    }

    @PostMapping(value = "/add_LiveChatModerators")//http://127.0.0.1:55304/Youtube_API/addLiveChatModerators?id=
    //http://140.121.196.20:55304/Youtube_API/addLiveChatModerators?id=
    public String addLiveChatModerators(@RequestParam(value = "id", defaultValue = "") String id){
        return AddLiveChatModerators.addLiveChatModerators(id);
    }

    @PostMapping(value = "/ban_LiveChatUser")//http://127.0.0.1:55304/Youtube_API/banLiveChatUser?id=
    //http://140.121.196.20:55304/Youtube_API/banLiveChatUser?id=
    public String banLiveChatUser(@RequestParam(value = "id", defaultValue = "") String id,
                                  @RequestParam(value = "time", defaultValue = "") BigInteger time){
        return BanLiveChatUser.banLiveChatUser(id,time);
    }

    @PostMapping(value = "/delete_LiveChatMessage")//http://127.0.0.1:55304/Youtube_API/deleteLiveChatMessage?id=
    //http://140.121.196.20:55304/Youtube_API/deleteLiveChatMessage?id=
    public String deleteLiveChatMessage(@RequestParam(value = "id", defaultValue = "") String id){
        return DeleteLiveChatMessage.deleteLiveChatMessage(id);
    }


}
