package com.example.demo.youtubeAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VoteResult {
    private Map<String,String> voteResult = new HashMap<>();

    private Map<String, Integer> voteCount = new HashMap<>();

    private String question;
    private boolean endFlag = false;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public VoteResult(ArrayList<String> options) {
        for(int i = 0;i < options.size() ; i++){
            this.voteCount.put(options.get(i),0);
        }
    }



    public void addVoteCount(String option,int number){
        int oldNumber = this.voteCount.get(option);
        this.voteCount.replace(option,oldNumber+number);
    }

    public Map<String, Integer> getVoteCount() {
        return voteCount;
    }

    public void setVoteResult(String person,String option) {
        this.voteResult.put(person,option);
    }

    public Map<String, String> getVoteResult() {
        return voteResult;
    }

    public boolean isEndFlag() {
        return endFlag;
    }

    public void setEndFlag(boolean endFlag) {
        this.endFlag = endFlag;
    }

}
