package com.example.demo.youtubeAPI;

import com.google.api.client.util.DateTime;

import java.math.BigInteger;

public class Comment {
    private String videoId;
    private String authorName;
    private String authorImg;
    private String authorId;
    private Long likeCount;
    private String comment;
    private DateTime publishAt;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorImg() {
        return authorImg;
    }

    public void setAuthorImg(String authorImg) {
        this.authorImg = authorImg;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public DateTime getPublishAt() {
        return publishAt;
    }

    public void setPublishAt(DateTime publishAt) {
        this.publishAt = publishAt;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "videoId='" + videoId + '\'' +
                ", authorName='" + authorName + '\'' +
                ", authorImg='" + authorImg + '\'' +
                ", authorId='" + authorId + '\'' +
                ", likeCount=" + likeCount +
                ", comment='" + comment + '\'' +
                ", publishAt=" + publishAt +
                '}';
    }
}
