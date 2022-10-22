package com.example.demo.entity;

public class Schedule {
    private String date;
    private String describe;
    private String streamSchedule;
    private String startTime;
    private String endTime;


    public void setSchedule(SetScheduleData data){
        date = data.date;
        describe = data.describe;
        streamSchedule = data.streamSchedule;
        startTime = data.startTime;
        endTime = data.endTime;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getStreamSchedule() {
        return streamSchedule;
    }

    public void setStreamSchedule(String streamSchedule) {
        this.streamSchedule = streamSchedule;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
