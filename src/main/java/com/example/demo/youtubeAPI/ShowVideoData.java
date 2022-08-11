package com.example.demo.youtubeAPI;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.api.services.youtube.model.VideoListResponse;
import com.google.common.collect.Lists;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class ShowVideoData {

    private static YouTube youtube;

    public static void main(String args[]){
        List<String> scopes = Lists.newArrayList(YouTubeScopes.YOUTUBE_READONLY);

        try {
            // Authorize the request.
            Credential credential = Auth.authorize(scopes, "showvideodata");

            // This object is used to make YouTube Data API requests.
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
                    .setApplicationName("youtube-cmdline-showvideodata-sample").build();

            VideoListResponse response = youtube.videos().list("statistics,snippet").setId("_ePr7vhcJV8").execute();

            JSONObject videoData = new JSONObject(response.getItems().get(0).getStatistics());
            JSONObject videoName = new JSONObject(response.getItems().get(0).getSnippet());
            System.out.println(response);
            //System.out.println(js);

            System.out.println("影片:\"" + videoName.getString("title") + "\"的數據:");
            System.out.println("不喜歡人數:" + videoData.getInt("dislikeCount"));
            System.out.println("喜歡人數:" + videoData.getInt("likeCount"));
            System.out.println("觀看次數:" + videoData.getInt("viewCount"));
            System.out.println("總留言數:" + videoData.getInt("commentCount"));
            System.out.println("你最愛的留言數:" + videoData.getInt("favoriteCount"));
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
