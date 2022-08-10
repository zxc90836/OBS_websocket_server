package com.example.demo.youtubeAPI;

import java.util.HashMap;
import java.util.Map;

public class VoteResult {
    private Map<String,String> voteResult = new HashMap<>();

    private Map<String, Integer> voteCount = new HashMap<>();;

    public VoteResult(String options[]) {
        for(int i = 0;i < options.length ; i++){
            this.voteCount.put(options[i],0);
        }
    }

    public void setVoteCount(String option) {
        this.voteCount.put(option,0);
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
}
