package com.example.demo.youtubeAPI;

import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.model.GeoPoint;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class Video {
    private String title;
    private String description;
    private String imgURL;
    private BigInteger likeCount;
    private BigInteger dislikeCount;
    private BigInteger viewCount;
    private BigInteger commentCount;
    private List<String> tags;
    private String uploadStatus;
    private GeoPoint geoPoint;
    private DateTime scheduledStartTime;
    private DateTime actualStartTime;
    private DateTime actualEndTime;
    private BigInteger concurrentViewers;
    private Map<DateTime,BigInteger> Viewers;
    private List<Audience> audiences;

    public void setConcurrentViewers(BigInteger concurrentViewers) {
        this.concurrentViewers = concurrentViewers;
    }

    public Map<DateTime, BigInteger> getViewers() {
        return Viewers;
    }

    public void setViewers(Map<DateTime, BigInteger> viewers) {
        Viewers = viewers;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public void setLikeCount(BigInteger likeCount) {
        this.likeCount = likeCount;
    }

    public void setDislikeCount(BigInteger dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public void setViewCount(BigInteger viewCount) {
        this.viewCount = viewCount;
    }

    public void setCommentCount(BigInteger commentCount) {
        this.commentCount = commentCount;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setUploadStatus(String uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public void setScheduledStartTime(DateTime scheduledStartTime) {
        this.scheduledStartTime = scheduledStartTime;
    }

    public void setActualStartTime(DateTime actualStartTime) {
        this.actualStartTime = actualStartTime;
    }

    public void setActualEndTime(DateTime actualEndTime) {
        this.actualEndTime = actualEndTime;
    }

    public void setAudiences(List<Audience> audiences) {
        this.audiences = audiences;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImgURL() {
        return imgURL;
    }

    public BigInteger getLikeCount() {
        return likeCount;
    }

    public BigInteger getDislikeCount() {
        return dislikeCount;
    }

    public BigInteger getViewCount() {
        return viewCount;
    }

    public BigInteger getCommentCount() {
        return commentCount;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getUploadStatus() {
        return uploadStatus;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public DateTime getScheduledStartTime() {
        return scheduledStartTime;
    }

    public DateTime getActualStartTime() {
        return actualStartTime;
    }

    public DateTime getActualEndTime() {
        return actualEndTime;
    }

    public List<Audience> getAudiences() {
        return audiences;
    }
}
