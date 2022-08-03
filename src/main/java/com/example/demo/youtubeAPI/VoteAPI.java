package com.example.demo.youtubeAPI;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.api.services.youtube.model.*;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.*;

public class VoteAPI {
    private static final String LIVE_CHAT_FIELDS =
            "items(authorDetails(channelId,displayName,isChatModerator,isChatOwner,isChatSponsor,"
                    + "profileImageUrl),snippet(displayMessage,superChatDetails,publishedAt)),"
                    + "nextPageToken,pollingIntervalMillis";

    private static YouTube youtube;

    private static Map<String,String> VoteResult;

    private static String[] keywords = {"1","2","3"};

    private static long voteTime = 300*1000; //300s * 1000ms/sec

    private static String voteMessage = "現在開始投票，選項有" + keywords.toString() +
            "請在聊天室打出完全相同的字來投票，計時" + voteTime/1000/60 +"分鐘";

    public static void main(String args[]){
        List<String> scopes = Lists.newArrayList(YouTubeScopes.YOUTUBE_FORCE_SSL);

        VoteResult = new HashMap<String,String>();

        try {
            // Authorize the request.
            Credential credential = Auth.authorize(scopes, "listlivechatmessages");

            // This object is used to make YouTube Data API requests.
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
                    .setApplicationName("youtube-cmdline-listchatmessages-sample").build();

            // Get the liveChatId
            String liveChatId = args.length == 1
                    ? GetLiveChatId.getLiveChatId(youtube, args[0])
                    : GetLiveChatId.getLiveChatId(youtube);
            if (liveChatId != null) {
                System.out.println("Live chat id: " + liveChatId);
            } else {
                System.err.println("Unable to find a live chat id");
                System.exit(1);
            }

            long start = System.currentTimeMillis();
            long end = start + voteTime;

            insertChatMessage(liveChatId,voteMessage);

            // Get live chat messages
            listChatMessages(liveChatId, null, 1000, end); //delayMs : 每多少毫秒執行一次 目前預設1s
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

    private static void listChatMessages(final String liveChatId, final String nextPageToken, long delayMs, long end) {
        /*System.out.println(
                String.format("Getting chat messages in %1$.3f seconds...", delayMs * 0.001));*/
        Timer pollTimer = new Timer();


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
                boolean inTime = false;
                for (int i = 0; i < messages.size(); i++) {
                    LiveChatMessage message = messages.get(i);
                    LiveChatMessageSnippet snippet = message.getSnippet();
                    System.out.println(buildOutput(
                            snippet.getDisplayMessage(),
                            message.getAuthorDetails(),
                            snippet.getSuperChatDetails()));
                    if(snippet.getDisplayMessage().equals(voteMessage)) inTime = true;
                    VoteResult = detectVoteResult(liveChatId,keywords, snippet.getDisplayMessage(), message.getAuthorDetails(),
                            snippet.getSuperChatDetails(),VoteResult,inTime);
                }

                Thread.sleep(1000); //設定延遲
                // Request the next page of messages
                listChatMessages(
                        liveChatId,
                        response.getNextPageToken(),
                        response.getPollingIntervalMillis(),
                        end);
            } catch (Throwable t) {
                System.err.println("Throwable: " + t.getMessage());
                t.printStackTrace();
            }
        }
        insertChatMessage(liveChatId,"投票已結束");
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

    private static Map<String,String> detectVoteResult(final String liveChatId,String[] keywords, String message, LiveChatMessageAuthorDetails author,
                                         LiveChatSuperChatDetails superChatDetails, Map<String,String> VoteResult,boolean inTime){
        for(int i=0;i< keywords.length;i++){
            if(inTime){ //在投票時間內
                if(message.equals(keywords[i])){    //偵測每個投票關鍵字
                    if(VoteResult.get(author.getDisplayName()) == null){    //是否重複投票
                        VoteResult.put(author.getDisplayName(), keywords[i]);
                        System.out.println(author.getDisplayName() + " 投了 " + keywords[i]);
                        insertChatMessage(liveChatId,author.getDisplayName() + " 投了 " + keywords[i]);
                        break;
                    }
                }
            }
        }
        return VoteResult;
    }
    private static void insertChatMessage(String liveChatId,String message){
        try {
            LiveChatMessage liveChatMessage = new LiveChatMessage();
            LiveChatMessageSnippet snippet = new LiveChatMessageSnippet();
            snippet.setType("textMessageEvent");
            snippet.setLiveChatId(liveChatId);
            LiveChatTextMessageDetails details = new LiveChatTextMessageDetails();
            details.setMessageText(message);
            snippet.setTextMessageDetails(details);
            liveChatMessage.setSnippet(snippet);
            YouTube.LiveChatMessages.Insert liveChatInsert =
                    youtube.liveChatMessages().insert("snippet", liveChatMessage);
            LiveChatMessage response = liveChatInsert.execute();
            System.out.println("Inserted message id " + response.getId());
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

}
