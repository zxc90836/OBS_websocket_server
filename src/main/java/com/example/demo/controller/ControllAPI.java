package com.example.demo.controller;

import com.example.demo.ClinetMap;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/OBS_websocket")//http://127.0.0.1:8080/OBS_websocket/change_scene?scene=場景"
public class ControllAPI {
    @GetMapping("/change_scene")//http://127.0.0.1:8080/OBS_websocket/change_scene?key=&scene=場景"
    public String changeScene(@RequestParam(value = "key", defaultValue = "") String key,@RequestParam(value =
            "scene", defaultValue = "") String scene){
        ClinetMap.sendMSGToOBSServer(key,"switchScene "+scene);
        return "success";
    }
    @GetMapping("/get_scenes")//http://127.0.0.1:8080/OBS_websocket/get_scenes?key="
    public String getScenes(@RequestParam(value = "key", defaultValue = "") String key){
        ClinetMap.getScenes(key);
        return ClinetMap.getScenes(key);
    }
}
