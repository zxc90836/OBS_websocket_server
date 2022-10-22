package com.example.demo.youtubeAPI;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigInteger;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChannelData{
    BigInteger videoCount;
    BigInteger subscriberCount;
    BigInteger viewCount;
    String estimatedRevenue;

    @Override
    public String toString() {
        return "channelData{" +
                "videoCount=" + videoCount +
                ", subscriberCount=" + subscriberCount +
                ", viewCount=" + viewCount +
                ", estimatedRevenue='" + estimatedRevenue + '\'' +
                '}';
    }

    public BigInteger getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(BigInteger videoCount) {
        this.videoCount = videoCount;
    }

    public BigInteger getSubscriberCount() {
        return subscriberCount;
    }

    public void setSubscriberCount(BigInteger subscriberCount) {
        this.subscriberCount = subscriberCount;
    }

    public BigInteger getViewCount() {
        return viewCount;
    }

    public void setViewCount(BigInteger viewCount) {
        this.viewCount = viewCount;
    }

    public String getEstimatedRevenue() {
        return estimatedRevenue;
    }

    public void setEstimatedRevenue(String estimatedRevenue) {
        this.estimatedRevenue = estimatedRevenue;
    }
}