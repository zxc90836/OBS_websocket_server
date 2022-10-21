package com.example.demo.entity;

public class MemberPermission {
    public String team = "";
    public String memberName = "";
    public boolean remoteControl = true;
    public boolean dataAnalysis = false;
    public boolean teamManagement = false;
    public MemberPermission(){

    }
    public MemberPermission(String member,boolean remote, boolean data, boolean team){
        remoteControl = remote;
        dataAnalysis = data;
        teamManagement = team;
        memberName = member;
    }
}
