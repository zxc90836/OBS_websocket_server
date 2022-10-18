package com.example.demo.youtubeAPI;

import com.google.api.client.util.DateTime;

public class Message {
    private String id;
    private Video video;
    private DateTime time;
    private String message;
    private String name;
    private String channelID;
    private String imgURL;
    private boolean inDanger;

    public boolean isInDanger() {
        return inDanger;
    }

    public void setInDanger(boolean inDanger) {
        this.inDanger = inDanger;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getChannelID() {
        return channelID;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public DateTime getTime() {
        return time;
    }

    public void setTime(DateTime time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", video=" + video +
                ", time=" + time +
                ", message='" + message + '\'' +
                ", name='" + name + '\'' +
                ", channelID='" + channelID + '\'' +
                ", imgURL='" + imgURL + '\'' +
                '}';
    }
}
