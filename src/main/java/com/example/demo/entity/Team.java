package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;


@ToString
@Document(collection = "Team")
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    private String youtubeAccount;
    private String userName;
    private Map<String, MemberPermission> member = new HashMap<>();
    private Map<String,Schedule> schedule = new HashMap<>();
    public void addMember(MemberPermission scope){
        member.put(scope.memberName,scope);
    }
    public void modifyMember(MemberPermission memberPermission){
        member.put(memberPermission.memberName,memberPermission);
    }
    public void deleteMember(MemberPermission memberPermission){
            member.remove(memberPermission.memberName);
    }
    public void addSchedule(String date,Schedule newSchedule){
        schedule.put(date,newSchedule);
    }
    public void deleteSchedule(String date){
        schedule.remove(date);
    }
    public String getYoutubeAccount() {
        return youtubeAccount;
    }
    public void setYoutubeAccount(String youtubeAccount) {
        this.youtubeAccount = youtubeAccount;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Map<String, MemberPermission> getMember() {
        return member;
    }
    public void setMember(Map<String, MemberPermission> member) {
        this.member = member;
    }
    public Map<String, Schedule> getSchedule() {
        return schedule;
    }
    public void setSchedule(Map<String, Schedule> schedule) {
        this.schedule = schedule;
    }
}
