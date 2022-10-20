package com.example.demo.youtubeAPI;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.api.services.youtube.model.ChannelProfileDetails;
import com.google.api.services.youtube.model.LiveChatBan;
import com.google.api.services.youtube.model.LiveChatBanSnippet;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class BanLiveChatUser {
    /**
     * Define a global instance of a Youtube object, which will be used
     * to make YouTube Data API requests.
     */
    private static YouTube youtube;

    private static String state = "fail";
    public static String banLiveChatUser(String BanerID,BigInteger BanSec) {

        // This OAuth 2.0 access scope allows for write access to the authenticated user's account.
        List<String> scopes = Lists.newArrayList(YouTubeScopes.YOUTUBE_FORCE_SSL);

        try {
            // Authorize the request.
            Credential credential = Auth.authorize(scopes, "banlivechatmessage");

            // This object is used to make YouTube Data API requests.
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
                    .setApplicationName("youtube-cmdline-banlivechatmessage-sample").build();

            // Delete the message from live chat
            LiveChatBan liveChatBan = new LiveChatBan();
            LiveChatBanSnippet snippet = new LiveChatBanSnippet();
            snippet.setBanDurationSeconds(BanSec);
            ChannelProfileDetails bannedUserDetails = new ChannelProfileDetails();
            bannedUserDetails.setChannelId(BanerID);
            snippet.setBannedUserDetails(bannedUserDetails);
            snippet.setLiveChatId(GetLiveChatId.getLiveChatId(youtube));
            snippet.setType("temporary");
            liveChatBan.setSnippet(snippet);

            YouTube.LiveChatBans.Insert request = youtube.liveChatBans()
                    .insert("snippet", liveChatBan);
            LiveChatBan response = request.execute();
            System.out.println(response);
            state = "success";

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
        return state;
    }
}
