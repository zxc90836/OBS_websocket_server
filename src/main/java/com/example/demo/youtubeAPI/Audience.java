package com.example.demo.youtubeAPI;

import java.util.List;

public class Audience {
    private String name;
    private String channelURL;
    private String imgURL;
    private List<Message> messageList;

    public String getName() {
        return name;
    }

    public String getChannelURL() {
        return channelURL;
    }

    public String getImgURL() {
        return imgURL;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChannelURL(String channelURL) {
        this.channelURL = channelURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }
}

