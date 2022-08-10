package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.ClinetMap;
import com.example.demo.VoteData;
import com.example.demo.youtubeAPI.VoteResult;
import io.netty.channel.ChannelHandlerContext;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/OBS_websocket")//http://127.0.0.1:55304/OBS_websocket/change_scene?scene=場景"
//http://180.177.24.44:55304/OBS_websocket
public class ControllAPI {
    @GetMapping("/change_scene")//http://127.0.0.1:8080/OBS_websocket/change_scene?key=&scene=場景"
    //http://140.121.196.20:55304/OBS_websocket/change_scene?key=&scene=場景
    public String changeScene(@RequestParam(value = "key", defaultValue = "") String key,@RequestParam(value =
            "scene", defaultValue = "") String scene){
        ClinetMap.sendMSGToOBSServer(key,"switchScene "+scene);
        return "success";
    }
    @GetMapping("/get_scenes")//http://127.0.0.1:8080/OBS_websocket/get_scenes?key="
    //http://140.121.196.20:55304/OBS_websocket/get_scenes?key=4908795
    public String getScenes(@RequestParam(value = "key", defaultValue = "") String key){
        ClinetMap.getScenes(key);
        return ClinetMap.getScenes(key);
    }
    @GetMapping
    String index() {
        return "index";
    }
    @PostMapping("/startVote") //http://127.0.0.1:55304/OBS_websocket/startVote
    public String startVote(@RequestBody JSONObject voteData){
        ClinetMap.sendMSGToOBSServer("startVote", JSON.toJSONString(voteData));
        return "start voting";
    }
}
