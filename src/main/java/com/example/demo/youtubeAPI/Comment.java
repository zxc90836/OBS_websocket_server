package com.example.demo.youtubeAPI;

public class Comment {
    private String videoId;
    private String Author;
    private String Comment;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "videoId='" + videoId + '\'' +
                ", Author='" + Author + '\'' +
                ", Comment='" + Comment + '\'' +
                '}';
    }
}
