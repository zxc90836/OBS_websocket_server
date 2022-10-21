package com.example.demo.youtubeAPI;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.List;

/**
 * Delets a message from a live broadcast, using OAuth 2.0 to authorize API requests.
 *
 * @author Jim Rogers
 */
public class DeleteLiveChatMessage {

    /**
     * Define a global instance of a Youtube object, which will be used
     * to make YouTube Data API requests.
     */
    private static YouTube youtube;
    private static String state = "fail";

    public static String deleteLiveChatMessage(String messageId) {
        // Get the message id to delete
        /*if (args.length == 0) {
            System.err.println("No message id specified");
            System.exit(1);
        }*/

        // This OAuth 2.0 access scope allows for write access to the authenticated user's account.
        List<String> scopes = Lists.newArrayList(YouTubeScopes.YOUTUBE_FORCE_SSL);

        try {
            // Authorize the request.
            Credential credential = Auth.authorize(scopes, "deletelivechatmessage");

            // This object is used to make YouTube Data API requests.
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
                    .setApplicationName("youtube-cmdline-deletechatmessages-sample").build();

            // Delete the message from live chat
            YouTube.LiveChatMessages.Delete liveChatDelete =
                    youtube.liveChatMessages().delete(messageId);
            liveChatDelete.execute();
            System.out.println("Deleted message id " + messageId);
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