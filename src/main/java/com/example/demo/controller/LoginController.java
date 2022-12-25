package com.example.demo.controller;


import com.example.demo.entity.*;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class LoginController {
    @Autowired
    private LoginService service = new LoginService();
    @GetMapping("/user")//../user
    public Object getUser(){
        return service.createTeam();
    }
    //回傳可選擇的團隊
    @PostMapping("/login")//../login?account=owner&password=password
    //../login?account=colab&password=password2
    @ResponseBody
    public User login(@RequestBody User request){
        User user = service.login(request.getUserName(),request.getPassword());
        if(user == null){
            return null;
        }
        return user;
    }
    @PostMapping("/signUp")//../signUp
    @ResponseBody
    public User signUp(@RequestBody SignUpData request){
        log.info("sign up");
        return service.signUp(request.userName,request.password,request.youtubeAccount);
    }
    @GetMapping("/getTeam")//../getTeam
    public Team getTeam(@RequestParam(value = "teamName", defaultValue = "") String teamName){
        log.info("getTeam");
        return service.getTeam(teamName);
    }
    @GetMapping("/getColab")//../getColab?account=owner
    public Map<String,String> getColab(@RequestParam(value = "account", defaultValue = "")String account){
        return service.getColab(account);
    }
    @GetMapping("/getSchedule")//../getSchedule?team=00000@gmail.com
    public Map<String, Schedule> getSchedule(@RequestParam(value = "team", defaultValue = "")String team){
        return service.getSchedule(team);
    }
    @GetMapping("/getMember")//../getMember?account=owner
    public Map<String,MemberPermission> getMember(@RequestParam(value = "team", defaultValue = "")String team){
        return service.getMember(team);
    }
//    {
//        "team": "0979710326a@gmail.com",
//            "date": "2022-10-22",
//            "describe": "專題實作",
//            "streamSchedule": "要死了"
//
//    }
    @PostMapping("/setSchedule")//../setSchedule
    @ResponseBody
    public boolean setSchedule(@RequestBody SetScheduleData request){
        log.info("setSchedule"+request);
        Schedule s = new Schedule();
        s.setSchedule(request);
        return service.setSchedule(request.team,s);
    }
    @PostMapping("/deleteSchedule")//../deleteSchedule
    @ResponseBody
    public boolean deleteSchedule(@RequestBody SetScheduleData request){
        log.info("deleteSchedule"+request);
        return service.deleteSchedule(request.team,request.date);
    }
    @PostMapping("/addMember")//../addMember
    @ResponseBody
    public boolean addMember(@RequestBody MemberPermission request){
        log.info("addMember"+request);
        return service.addMember(request);
    }
    @PostMapping("/modifyPermission")//../modifyPermission
    @ResponseBody
    public boolean modifyPermission(@RequestBody MemberPermission request){
        log.info("modifyPermission"+request);
        return service.addMember(request);
    }
    @PostMapping("/deleteMember")//../deleteMember
    @ResponseBody
    public boolean deleteMember(@RequestBody MemberPermission request){
        log.info("deleteMember"+request);
        return service.deleteMember(request);
    }

    @GetMapping("/get_streamingChat")//../Youtube_API/get_streamingChat
    //http://140.121.196.20:55304/Youtube_API/get_streamingChat
    public String getStreamingChat(@RequestParam(value = "key", defaultValue = "") String account){
        ClientMap.sendMSGToOBSServer(account,"getStreamingChat " + account);
        return service.getLiveChatMessage(account);
    }

    @GetMapping("/get_SC")//../Youtube_API/get_SC
    //http://140.121.196.20:55304/Youtube_API/get_SC
    public String getSC(@RequestParam(value = "key", defaultValue = "") String account){
        ClientMap.sendMSGToOBSServer(account,"getSCDetails " + account);
        return service.getSCDetail(account);
    }

    @GetMapping("/get_relatedVideo")//../Youtube_API/get_relatedVideo?key=YL471T6LkMA
    //http://140.121.196.20:55304/Youtube_API/get_relatedVideo?key=YL471T6LkMA
    public String getRelatedVideo(@RequestParam(value = "key", defaultValue = "") String account,
                                  @RequestParam(value = "id", defaultValue = "")String id){
        ClientMap.sendMSGToOBSServer(account,"getRelatedVideo " + account + "&" + id);
        return service.getRelatedVideo(account);
    }

    @GetMapping("/get_videoHistory")//../Youtube_API/getChannelHistory?key=YL471T6LkMA
    //http://140.121.196.20:55304/Youtube_API/getChannelHistory?key=YL471T6LkMA
    public String getVideoHistory(@RequestParam(value = "key", defaultValue = "") String account,
                                  @RequestParam(value = "id", defaultValue = "") String id,
                                  @RequestParam(value = "start", defaultValue = "") String start,
                                  @RequestParam(value = "end", defaultValue = "") String end){
        ClientMap.sendMSGToOBSServer(account,"getVideoHistory " + account + "&" + id + "&" + start + "&" + end);
        return service.getVideoHistory(account);
    }

    @GetMapping("/get_channelHistory")//../Youtube_API/getChannelHistory?key=YL471T6LkMA
    //http://140.121.196.20:55304/Youtube_API/getChannelHistory?key=YL471T6LkMA
    public String getChannelHistory(@RequestParam(value = "start", defaultValue = "") String start,
                                    @RequestParam(value = "end", defaultValue = "") String end){
        return service.getChannelHistory(start,end);
    }

    @PostMapping(value = "/add_LiveChatModerators")//../Youtube_API/addLiveChatModerators?id=
    //http://140.121.196.20:55304/Youtube_API/addLiveChatModerators?id=
    public String addLiveChatModerators(@RequestParam(value = "id", defaultValue = "") String id,
                                        @RequestParam(value = "key", defaultValue = "") String account){
        ClientMap.sendMSGToOBSServer(account,"addLiveChatModerators " + id);
        return service.addLiveChatModerators(account);
    }

    @PostMapping(value = "/ban_LiveChatUser")//../Youtube_API/banLiveChatUser?id=
    //http://140.121.196.20:55304/Youtube_API/banLiveChatUser?id=
    public String banLiveChatUser(@RequestParam(value = "id", defaultValue = "") String id,
                                  @RequestParam(value = "time", defaultValue = "") BigInteger time,
                                  @RequestParam(value = "key", defaultValue = "") String account){
        ClientMap.sendMSGToOBSServer(account,"banLiveChatUser " + id + "&" + time);
        return service.banLiveChatUser(id,time);
    }

    @PostMapping(value = "/delete_LiveChatMessage")//../Youtube_API/deleteLiveChatMessage?id=
    //http://140.121.196.20:55304/Youtube_API/deleteLiveChatMessage?id=
    public String deleteLiveChatMessage(@RequestParam(value = "id", defaultValue = "") String id,
                                        @RequestParam(value = "key", defaultValue = "") String account){
        ClientMap.sendMSGToOBSServer(account,"deleteLiveChatMessage " + id);
        return service.deleteLiveChatMessage(id);
    }
    @GetMapping("/getVideoFromDB")//../getVideoFromDB?key=YL471T6LkMA
    //http://140.121.196.20:55304/Youtube_API/get_myVideos
    public Video getVideoFromDB(@RequestParam(value = "key", defaultValue = "") String videoKey){
        return service.getVideoFromDB(videoKey);
    }
    @GetMapping("/getAllVideoFromDB")//../getAllVideoFromDB?key=YL471T6LkMA
    //http://140.121.196.20:55304/getAllVideoFromDB?key=YL471T6LkMA
    public List<Video> getAllVideoFromDB(@RequestParam(value = "key", defaultValue = "") String ytAccount){
        return service.getAllVideoFromDB(ytAccount);
    }

    @GetMapping("/get_video")//../Youtube_API/get_video?key=YL471T6LkMA
    //http://140.121.196.20:55304/Youtube_API/get_video?key=YL471T6LkMA
    public Video getVideo(@RequestParam(value = "key", defaultValue = "") String key){
        return service.getVideoData(key);
    }
    @GetMapping("/get_myVideos")//../get_myVideos?key=YL471T6LkMA
    //http://140.121.196.20:55304/Youtube_API/get_myVideos
    public String getMyVideos(@RequestParam(value = "key", defaultValue = "") String ytAccount){
        ClientMap.sendMSGToOBSServer(ytAccount,"getAllVideoFromDB " + ytAccount);
        return service.getAllVideoData(ytAccount);
    }


    @GetMapping("/get_comments")//../Youtube_API/get_myVideos
    //http://140.121.196.20:55304/Youtube_API/get_myVideos
    public String getComments(@RequestParam(value = "id", defaultValue = "") String id,
                              @RequestParam(value = "key", defaultValue = "") String ytAccount){
        ClientMap.sendMSGToOBSServer(ytAccount,"getComments " + ytAccount + "&" + id);
        return service.getComment(ytAccount);
    }
    @PostMapping(value = "/start_vote") //../start_vote
    public boolean startVote(@RequestBody VoteData voteData) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        ClientMap.sendMSGToOBSServer(voteData.getPollAccount(),"startVote " + mapper.writeValueAsString(voteData));
        return true;
    }
    @GetMapping("/get_voteResult")//../get_voteResult?key=4908795
    //http://140.121.196.20:55304/OBS_websocket/get_scenes?key=4908795
    public String getVoteResult(@RequestParam(value = "key", defaultValue = "") String ytAccount) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ClientMap.sendMSGToOBSServer(ytAccount,"getVoteData " + ytAccount);
        return service.getVoteData(ytAccount);
    }
    @GetMapping("/get_channelData")//http://127.0.0.1:55304/OBS_websocket/get_voteResult"
    //http://140.121.196.20:55304/OBS_websocket/get_scenes?key=4908795
    public String getChannelData(@RequestParam(value = "key", defaultValue = "") String account) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ClientMap.sendMSGToOBSServer(account,"getChannelData " + account);
        return service.getChannelData(account);
    }

//    @GetMapping("/get_StreamingVideo")//http://127.0.0.1:55304/OBS_websocket/get_voteResult"
//    //http://140.121.196.20:55304/OBS_websocket/get_scenes?key=4908795
//    public String getStreamingVideo(@RequestParam(value = "key", defaultValue = "") String account) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        ClientMap.sendMSGToOBSServer(account,"getStreamingVideo " + account);
//        return mapper.writeValueAsString(ClientMap.getStreamingData().get(account));
//    }
    @GetMapping("/getStreamingVideo")//http://127.0.0.1:55304/getStreamingVideo?key=帳號"
    public String getStreamingVideo(@RequestParam(value = "key", defaultValue = "") String account) throws IOException {
        //ObjectMapper mapper = new ObjectMapper();
        ClientMap.sendMSGToOBSServer(account,"getStreamingVideo " + account);

        return service.getStreamingURL(account);
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
