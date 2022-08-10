package com.example.demo.youtubeAPI;

import com.alibaba.fastjson.JSON;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.api.services.youtube.model.*;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.*;

public class VoteAPI extends Thread{
    private static final String LIVE_CHAT_FIELDS =
            "items(authorDetails(channelId,displayName,isChatModerator,isChatOwner,isChatSponsor,"
                    + "profileImageUrl),snippet(displayMessage,superChatDetails,publishedAt)),"
                    + "nextPageToken,pollingIntervalMillis";

    private static YouTube youtube;

    private static String[] keywords = {"1","2","3"}; //關鍵字

    private static String[] voteOptions = {"A","B","C"}; //選項名

    private static VoteResult VoteResult = new VoteResult(voteOptions);

    private static long voteTime = 300*1000; //300s * 1000ms/sec

    private static InsertLiveChatMessage insertChatMessage;

    public static void main(String args[]){
        List<String> scopes = Lists.newArrayList(YouTubeScopes.YOUTUBE_READONLY);

        try {
            // Authorize the request.
            Credential credential = Auth.authorize(scopes, "listlivechatmessages");

            // This object is used to make YouTube Data API requests.
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
                    .setApplicationName("youtube-cmdline-listchatmessages-sample").build();

            // Get the liveChatId
            String liveChatId = GetLiveChatId.getLiveChatId(youtube);
            if (liveChatId != null) {
                System.out.println("Live chat id: " + liveChatId);
            } else {
                System.err.println("Unable to find a live chat id");
                System.exit(1);
            }

            long start = System.currentTimeMillis();
            long end = start + voteTime;

            String allKeywords = "";
            String allVoteOptions = "";
            for (int i = 0; i < keywords.length; i++) {
                allKeywords += "\"" + keywords[i] + "\"， ";
                allVoteOptions += "\"" + voteOptions[i] + "\"， ";
            }

            String voteMessage = "現在開始投票，選項有 " + allVoteOptions +
                    "請在聊天室打出" + allKeywords + "來投票(注意須完全相同才會計算)，計時" + voteTime/1000/60 +"分鐘";

            insertChatMessage.InsertLiveChatMessage(liveChatId,voteMessage);

            // Get live chat messages
            listChatMessages(liveChatId, null, 1000, end,voteMessage); //delayMs : 每多少毫秒執行一次 目前預設1s
        } catch (GoogleJsonResponseException e) {
            System.err
                    .println("GoogleJsonResponseException code: " + e.getDetails().getCode() + " : "
                            + e.getDetails().getMessage());
            e.printStackTrace();

        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        } catch (Throwable t) {
            System.err.println("Throwable: " + t.getMessage());
            t.printStackTrace();
        }
    }

    private static void listChatMessages(final String liveChatId, final String nextPageToken, long delayMs, long end,String voteMessage) {
        /*System.out.println(
                String.format("Getting chat messages in %1$.3f seconds...", delayMs * 0.001));*/
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                sendVoteResult();
            }
        },5000L); //每5秒傳一次結果

        while(System.currentTimeMillis() < end){
            try {
                // Get chat messages from YouTube
                LiveChatMessageListResponse response = youtube
                        .liveChatMessages()
                        .list(liveChatId, "snippet, authorDetails")
                        .setPageToken(nextPageToken)
                        .setFields(LIVE_CHAT_FIELDS)
                        .execute();

                // Display messages and super chat details
                List<LiveChatMessage> messages = response.getItems();
                boolean notInTime = false;
                for (int i = 0; i < messages.size(); i++) {
                    LiveChatMessage message = messages.get(i);
                    LiveChatMessageSnippet snippet = message.getSnippet();
                    System.out.println(buildOutput(
                            snippet.getDisplayMessage(),
                            message.getAuthorDetails(),
                            snippet.getSuperChatDetails()));
                    if(snippet.getDisplayMessage().equals(voteMessage)) notInTime = true;
                    detectVoteResult(liveChatId,keywords, snippet.getDisplayMessage(), message.getAuthorDetails(),
                            snippet.getSuperChatDetails(),notInTime);
                }

                Thread.sleep(1000); //設定延遲
                // Request the next page of messages
                listChatMessages(
                        liveChatId,
                        response.getNextPageToken(),
                        response.getPollingIntervalMillis(),
                        end,voteMessage);
            } catch (Throwable t) {
                System.err.println("Throwable: " + t.getMessage());
                t.printStackTrace();
            }
        }
        insertChatMessage.InsertLiveChatMessage(liveChatId,"投票已結束");

        System.exit(0); //直接中斷
    }

    private static String buildOutput(
            String message,
            LiveChatMessageAuthorDetails author,
            LiveChatSuperChatDetails superChatDetails) {
        StringBuilder output = new StringBuilder();
        if (superChatDetails != null) {
            output.append(superChatDetails.getAmountDisplayString());
            output.append("SUPERCHAT RECEIVED FROM ");
        }
        output.append(author.getDisplayName());
        List<String> roles = new ArrayList<String>();
        if (author.getIsChatOwner()) {
            roles.add("OWNER");
        }
        if (author.getIsChatModerator()) {
            roles.add("MODERATOR");
        }
        if (author.getIsChatSponsor()) {
            roles.add("SPONSOR");
        }
        if (roles.size() > 0) {
            output.append(" (");
            String delim = "";
            for (String role : roles) {
                output.append(delim).append(role);
                delim = ", ";
            }
            output.append(")");
        }
        if (message != null && !message.isEmpty()) {
            output.append(": ");
            output.append(message);
        }
        return output.toString();
    }

    private static void detectVoteResult(final String liveChatId,String[] keywords, String message, LiveChatMessageAuthorDetails author,
                                         LiveChatSuperChatDetails superChatDetails,boolean notInTime){
        for(int i=0;i< keywords.length;i++){
            if(!notInTime){ //在投票時間內
                if(message.equals(keywords[i])){    //偵測每個投票關鍵字
                    if(VoteResult.getVoteResult().get(author.getDisplayName()) == null){    //是否重複投票
                        VoteResult.setVoteResult(author.getDisplayName(), voteOptions[i]);
                        VoteResult.addVoteCount(voteOptions[i],1);
                        System.out.println(author.getDisplayName() + " 投了 " + voteOptions[i]);
                        insertChatMessage.InsertLiveChatMessage(liveChatId,author.getDisplayName() + " 投了 " + voteOptions[i]);
                        break;
                    }
                }
            }
        }
    }

    public static void sendVoteResult(){
        System.out.println(JSON.toJSONString(VoteResult));
    }

    public static void sendEnd(){
        System.out.println("END");
    }
}
