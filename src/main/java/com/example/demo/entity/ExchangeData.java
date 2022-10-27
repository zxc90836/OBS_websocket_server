package com.example.demo.entity;


import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "DataExchange")
public class ExchangeData {
    private String teamName = "00000@gmail.com";
    private String ytKey = "";
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
}
