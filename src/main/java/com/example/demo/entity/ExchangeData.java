package com.example.demo.entity;


import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "DataExchange")
public class ExchangeData {
    private String teamName = "00000@gmail.com";
    private String ytKey = "";
    private String StreamingChat = "";
    private String SCDetail = "";
    private String relatedVideo = "";
    private String videoHistoryInfo = "";
    private String videoData = "";
    private String allVideoData = "";
    private String commentData = "";
    private String voteResult = "";
    private String channelData = "";
    private boolean saveFlag = false;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getYtKey() {
        return ytKey;
    }

    public void setYtKey(String ytKey) {
        this.ytKey = ytKey;
    }

    public boolean isSaveFlag() {
        return saveFlag;
    }

    public void setSaveFlag(boolean saveFlag) {
        this.saveFlag = saveFlag;
    }

    public String getStreamingChat() {
        return StreamingChat;
    }

    public void setStreamingChat(String streamingChat) {
        StreamingChat = streamingChat;
    }

    public String getSCDetail() {
        return SCDetail;
    }

    public void setSCDetail(String SCDetail) {
        this.SCDetail = SCDetail;
    }

    public String getRelatedVideo() {
        return relatedVideo;
    }

    public void setRelatedVideo(String relatedVideo) {
        this.relatedVideo = relatedVideo;
    }

    public String getVideoHistoryInfo() {
        return videoHistoryInfo;
    }

    public void setVideoHistoryInfo(String videoHistoryInfo) {
        this.videoHistoryInfo = videoHistoryInfo;
    }

    public String getVideoData() {
        return videoData;
    }

    public void setVideoData(String videoData) {
        this.videoData = videoData;
    }

    public String getAllVideoData() {
        return allVideoData;
    }

    public void setAllVideoData(String allVideoData) {
        this.allVideoData = allVideoData;
    }

    public String getCommentData() {
        return commentData;
    }

    public void setCommentData(String commentData) {
        this.commentData = commentData;
    }

    public String getVoteResult() {
        return voteResult;
    }

    public void setVoteResult(String voteResult) {
        this.voteResult = voteResult;
    }

    public String getChannelData() {
        return channelData;
    }

    public void setChannelData(String channelData) {
        this.channelData = channelData;
    }
}
