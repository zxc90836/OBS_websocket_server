package com.example.demo;

import lombok.Data;

import java.util.Map;

public class VoteData {
    private String question;

    private Map<String,String> legalResponse;

    private int timeLimit;

    private String pollAccount;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getPollAccount() {
        return pollAccount;
    }

    public void setPollAccount(String pollAccount) {
        this.pollAccount = pollAccount;
    }

    public Map<String, String> getLegalResponse() {
        return legalResponse;
    }

    public void setLegalResponse(Map<String, String> legalResponse) {
        this.legalResponse = legalResponse;
    }

    @Override
    public String toString() {
        return "VoteData{" +
                "question='" + question + '\'' +
                ", legalResponse=" + legalResponse +
                ", timeLimit=" + timeLimit +
                ", pollAccount='" + pollAccount + '\'' +
                '}';
    }
}
