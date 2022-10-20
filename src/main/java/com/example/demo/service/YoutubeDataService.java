package com.example.demo.service;

import com.example.demo.entity.Team;
import com.example.demo.entity.User;
import com.example.demo.repository.LoginRepository;
import com.example.demo.youtubeAPI.GetAllVideos;
import com.example.demo.youtubeAPI.Video;
import com.example.demo.youtubeAPI.getVideoInfo;
import com.google.api.services.youtube.model.PlaylistItem;
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
public class YoutubeDataService {

    private static final String VideoCollection= "VideosData";
    @Autowired
    private MongoTemplate mongoTemplate;
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
    public String getAllVideoData(){
        log.info("get all my videos--------------------");
        List<PlaylistItem> myVideos = GetAllVideos.getAllVideos();
        for(int i=0;i<myVideos.size();i++){
            Query query = new Query(Criteria.where("id").is(myVideos.get(i).getId()));
            //Video result = mongoTemplate.findOne(query, Video.class, VideoCollection);
            if(true){
                Video newVideo = getVideoInfo.getVideoInfo(myVideos.get(i).getId());
                mongoTemplate.save(newVideo);
                //result = mongoTemplate.findOne(query, Video.class, VideoCollection);
                //if(result == null) return null;
            }
        }
        return "success";
    }
    public static void main(String[] args) {
        LoginService l = new LoginService();
        l.createTeam();
    }
}
