package com.example.demo.youtubeAPI;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.api.services.youtube.model.ChannelProfileDetails;
import com.google.api.services.youtube.model.LiveChatModerator;
import com.google.api.services.youtube.model.LiveChatModeratorSnippet;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.List;

public class AddLiveChatModerators {
    /**
     * Define a global instance of a Youtube object, which will be used
     * to make YouTube Data API requests.
     */
    private static YouTube youtube;
    private static String state = "fail";

    public static String addLiveChatModerators(String ModeratorID) {

        // This OAuth 2.0 access scope allows for write access to the authenticated user's account.
        List<String> scopes = Lists.newArrayList(YouTubeScopes.YOUTUBE_FORCE_SSL);

        try {
            // Authorize the request.
            Credential credential = Auth.authorize(scopes, "addlivechatmoderators");

            LiveChatModerator liveChatModerator = new LiveChatModerator();
            // This object is used to make YouTube Data API requests.
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
                    .setApplicationName("youtube-cmdline-addlivechatmoderators-sample").build();

            // Delete the message from live chat
            LiveChatModeratorSnippet snippet = new LiveChatModeratorSnippet();
            snippet.setLiveChatId(GetLiveChatId.getLiveChatId(youtube));
            ChannelProfileDetails moderatorDetails = new ChannelProfileDetails();
            moderatorDetails.setChannelId(ModeratorID);
            snippet.setModeratorDetails(moderatorDetails);
            liveChatModerator.setSnippet(snippet);

            // Define and execute the API request
            YouTube.LiveChatModerators.Insert request = youtube.liveChatModerators()
                    .insert("snippet", liveChatModerator);
            LiveChatModerator response = request.execute();
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
