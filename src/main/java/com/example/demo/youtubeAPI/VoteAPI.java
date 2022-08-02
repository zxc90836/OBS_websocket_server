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

    private static long endTime;

    public static void main(String args[]){
        List<String> scopes = Lists.newArrayList(YouTubeScopes.YOUTUBE_READONLY);

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

            // Get live chat messages
            listChatMessages(liveChatId, null, 1000); //delayMs : 每多少毫秒執行一次 目前預設1s
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

    private static void listChatMessages(final String liveChatId, final String nextPageToken, long delayMs) {
        /*System.out.println(
                String.format("Getting chat messages in %1$.3f seconds...", delayMs * 0.001));*/
        Timer pollTimer = new Timer();
        pollTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
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
                            for (int i = 0; i < messages.size(); i++) {
                                LiveChatMessage message = messages.get(i);
                                LiveChatMessageSnippet snippet = message.getSnippet();
                                System.out.println(buildOutput(
                                        snippet.getDisplayMessage(),
                                        message.getAuthorDetails(),
                                        snippet.getSuperChatDetails()));
                                VoteResult = detectVoteResult(keywords, snippet.getDisplayMessage(), message.getAuthorDetails(),
                                        snippet.getSuperChatDetails(),VoteResult);
                            }

                            // Request the next page of messages
                            listChatMessages(
                                    liveChatId,
                                    response.getNextPageToken(),
                                    response.getPollingIntervalMillis());
                        } catch (Throwable t) {
                            System.err.println("Throwable: " + t.getMessage());
                            t.printStackTrace();
                        }
                    }
                }, delayMs);
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

    private static Map<String,String> detectVoteResult(String[] keywords, String message, LiveChatMessageAuthorDetails author,
                                         LiveChatSuperChatDetails superChatDetails, Map<String,String> VoteResult){
        for(int i=0;i< keywords.length;i++){
            if(message.equals(keywords[i])){    //偵測每個投票關鍵字
                if(VoteResult.get(author.getDisplayName()) == null || VoteResult == null){    //是否重複投票
                    VoteResult.put(author.getDisplayName(), keywords[i]);

                    System.out.println(author.getDisplayName() + " 投了 " + keywords[i]);
                    break;
                }
            }
        }
        return VoteResult;
    }
}
