package com.example.demo.youtubeAPI;


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.api.services.youtube.model.*;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Lists live chat messages and SuperChat details from a live broadcast.
 *
 * The videoId is often included in the video's url, e.g.:
 * https://www.youtube.com/watch?v=L5Xc93_ZL60
 *                                 ^ videoId
 * The video URL may be found in the browser address bar, or by right-clicking a video and selecting
 * Copy video URL from the context menu.
 *
 * @author Jim Rogers
 */
public class GetLiveChatOnce {

    /**
     * Common fields to retrieve for chat messages
     */
    private static final String LIVE_CHAT_FIELDS =
            "items(authorDetails(channelId,displayName,isChatModerator,isChatOwner,isChatSponsor,"
                    + "profileImageUrl),snippet(displayMessage,superChatDetails,publishedAt)),"
                    + "nextPageToken,pollingIntervalMillis";

    /**
     * Define a global instance of a Youtube object, which will be used
     * to make YouTube Data API requests.
     */
    private static YouTube youtube;
    private static List<Message> messageList = new ArrayList<>();

    public static String getLiveChatOnce() {

        // This OAuth 2.0 access scope allows for read-only access to the
        // authenticated user's account, but not other types of account access.
        List<String> scopes = Lists.newArrayList(YouTubeScopes.YOUTUBE_READONLY);

        try {
            // Authorize the request.
            Credential credential = Auth.authorize(scopes, "getChatMessageOnce");

            // This object is used to make YouTube Data API requests.
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
                    .setApplicationName("youtube-cmdline-getChatMessageOnce-sample").build();

            // Get the liveChatId
            String liveChatId = GetLiveChatId.getLiveChatId(youtube);
            if (liveChatId != null) {
                System.out.println("Live chat id: " + liveChatId);
            } else {
                System.err.println("Unable to find a live chat id");
                System.exit(1);
            }

            // Get live chat messages
            listChatMessages(liveChatId, null);
            System.out.println(messageList);

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
        return messageList.toString();
    }

    /**
     * Lists live chat messages, polling at the server supplied interval. Owners and moderators of a
     * live chat will poll at a faster rate.
     *
     * @param liveChatId The live chat id to list messages from.
     * @param nextPageToken The page token from the previous request, if any.
     */
    private static void listChatMessages(
            final String liveChatId,
            final String nextPageToken) {

        try {
            // Get chat messages from YouTube
            LiveChatMessageListResponse response = youtube
                    .liveChatMessages()
                    .list(liveChatId, "snippet, authorDetails ,id")
                    .setPageToken(nextPageToken)
                    .execute();
            // Display messages and super chat details
            List<LiveChatMessage> messages = response.getItems();

            String id = getStreamingVideo.getVideoId();

            System.out.println(messages.toString());
            for (int i = 0; i < messages.size(); i++) {
                Message msg = new Message();
                LiveChatMessage message = messages.get(i);
                LiveChatMessageSnippet snippet = message.getSnippet();
                msg.setId(message.getId());
                msg.setChannelID(message.getAuthorDetails().getChannelId());
                msg.setMessage(snippet.getDisplayMessage());
                msg.setTime(snippet.getPublishedAt());
                msg.setName(message.getAuthorDetails().getDisplayName());
                msg.setVideo(null); //影片先放置
                YouTube.Channels.List request = youtube.channels()
                        .list("snippet,contentDetails,statistics");
                msg.setImgURL(request.setId(message.getAuthorDetails().getChannelId()).execute().getItems().get(0).getSnippet().getThumbnails().getHigh().getUrl());

                System.out.println(buildOutput(
                        snippet.getDisplayMessage(),
                        message.getAuthorDetails(),
                        snippet.getSuperChatDetails()));
                System.out.println(message.getId()); //每則留言訊息的ID
                System.out.println(message.getAuthorDetails().getChannelId());
                messageList.add(msg);
            }

        } catch (Throwable t) {
            System.err.println("Throwable: " + t.getMessage());
            t.printStackTrace();
        }
    }

    /**
     * Formats a chat message for console output.
     *
     * @param message The display message to output.
     * @param author The author of the message.
     * @param superChatDetails SuperChat details associated with the message.
     * @return A formatted string for console output.
     */
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
}