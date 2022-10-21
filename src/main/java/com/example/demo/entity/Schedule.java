package com.example.demo.entity;

public class Schedule {
    private String date;
    private String describe;
    private String streamSchedule;


    public void setSchedule(SetScheduleData data){
        date = data.date;
        describe = data.describe;
        streamSchedule = data.streamSchedule;
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
}
