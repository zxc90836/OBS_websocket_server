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


    public static void main(String[] args) {
        LoginService l = new LoginService();
        l.createTeam();
    }

}
