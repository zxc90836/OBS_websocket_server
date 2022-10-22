package com.example.demo.youtubeAPI;

import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.model.GeoPoint;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Document(collection = "VideosData")
public class Video {
    private String id;
    private String title;
    private String ytAccount;
    private String description;
    private String imgURL;
    private BigInteger likeCount;
    private BigInteger dislikeCount;
    private BigInteger viewCount;
    private BigInteger commentCount;
    private List<String> tags;
    private String uploadStatus;
    private GeoPoint geoPoint;
    private Date scheduledStartTime;
    private Date actualStartTime;
    private Date actualEndTime;
    private BigInteger concurrentViewers;
    private Map<Date,BigInteger> Viewers;
    private List<Audience> audiences;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public BigInteger getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(BigInteger likeCount) {
        this.likeCount = likeCount;
    }

    public BigInteger getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(BigInteger dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public BigInteger getViewCount() {
        return viewCount;
    }

    public void setViewCount(BigInteger viewCount) {
        this.viewCount = viewCount;
    }

    public BigInteger getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(BigInteger commentCount) {
        this.commentCount = commentCount;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(String uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public Date getScheduledStartTime() {
        return scheduledStartTime;
    }

    public void setScheduledStartTime(Date scheduledStartTime) {
        this.scheduledStartTime = scheduledStartTime;
    }

    public Date getActualStartTime() {
        return actualStartTime;
    }

    public void setActualStartTime(Date actualStartTime) {
        this.actualStartTime = actualStartTime;
    }

    public Date getActualEndTime() {
        return actualEndTime;
    }

    public void setActualEndTime(Date actualEndTime) {
        this.actualEndTime = actualEndTime;
    }

    public BigInteger getConcurrentViewers() {
        return concurrentViewers;
    }

    public void setConcurrentViewers(BigInteger concurrentViewers) {
        this.concurrentViewers = concurrentViewers;
    }

    public Map<Date, BigInteger> getViewers() {
        return Viewers;
    }

    public void setViewers(Map<Date, BigInteger> viewers) {
        Viewers = viewers;
    }

    public List<Audience> getAudiences() {
        return audiences;
    }

    public void setAudiences(List<Audience> audiences) {
        this.audiences = audiences;
    }

    public String getYtAccount() {
        return ytAccount;
    }

    public void setYtAccount(String ytAccount) {
        this.ytAccount = ytAccount;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", ytAccount='" + ytAccount + '\'' +
                ", description='" + description + '\'' +
                ", imgURL='" + imgURL + '\'' +
                ", likeCount=" + likeCount +
                ", dislikeCount=" + dislikeCount +
                ", viewCount=" + viewCount +
                ", commentCount=" + commentCount +
                ", tags=" + tags +
                ", uploadStatus='" + uploadStatus + '\'' +
                ", geoPoint=" + geoPoint +
                ", scheduledStartTime=" + scheduledStartTime +
                ", actualStartTime=" + actualStartTime +
                ", actualEndTime=" + actualEndTime +
                ", concurrentViewers=" + concurrentViewers +
                ", Viewers=" + Viewers +
                ", audiences=" + audiences +
                '}';
    }
}
