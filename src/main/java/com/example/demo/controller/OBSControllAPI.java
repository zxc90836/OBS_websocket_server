package com.example.demo.controller;


import com.example.demo.VoteData;
import com.example.demo.youtubeAPI.VoteAPI;
import com.example.demo.youtubeAPI.VoteResult;
import lombok.extern.slf4j.Slf4j;
import com.example.demo.ClientMap;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/OBS_websocket")//http://127.0.0.1:55304/OBS_websocket/change_scene?scene=場景"
//http://180.177.24.44:55304/OBS_websocket
public class OBSControllAPI {
    @GetMapping("/change_scene")//http://127.0.0.1:55304/OBS_websocket/change_scene?key=&scene=場景"
    //http://140.121.196.20:55304/OBS_websocket/change_scene?key=&scene=場景
    public String changeScene(@RequestParam(value = "key", defaultValue = "") String key,@RequestParam(value =
            "scene", defaultValue = "") String scene){
        ClientMap.sendMSGToOBSServer(key,"switchScene "+ scene);
        return "success";
    }
    @GetMapping("/get_scenes")//http://127.0.0.1:55304/OBS_websocket/get_scenes?key=00857027@email.ntou.edu.tw"
    //http://140.121.196.20:55304/OBS_websocket/get_scenes?key=4908795
    public String getScenes(@RequestParam(value = "key", defaultValue = "") String key){
        ClientMap.getScenes(key);
        return ClientMap.getScenes(key);
    }
}
