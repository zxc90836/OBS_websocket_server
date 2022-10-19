package com.example.demo.controller;


import com.alibaba.fastjson.JSON;
import com.example.demo.VoteData;
import com.example.demo.youtubeAPI.GetAllVideos;
import com.example.demo.youtubeAPI.VoteAPI;
import com.example.demo.youtubeAPI.VoteResult;
import com.example.demo.youtubeAPI.getVideoInfo;
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

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/OBS_websocket")
//http://180.177.24.44:55304/OBS_websocket
public class YoutubeController {

    @GetMapping("/get_myVideos")//http://127.0.0.1:55304/OBS_websocket/get_myVideos
    //http://140.121.196.20:55304/OBS_websocket/get_myVideos
    public String getMyVideos(){
        return GetAllVideos.getAllVideos();
    }
    @GetMapping("/get_video")//http://127.0.0.1:55304/OBS_websocket/get_video?key=YL471T6LkMA"
    //http://140.121.196.20:55304/OBS_websocket/get_scenes?key=4908795
    public String getVideo(@RequestParam(value = "key", defaultValue = "") String key){
        return getVideoInfo.getVideoInfo(key).toString();
    }
    
}
