package com.example.demo.youtubeAPI;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.api.services.youtube.model.*;
import com.google.api.services.youtubeAnalytics.v2.YouTubeAnalytics;
import com.google.api.services.youtubeAnalytics.v2.model.QueryResponse;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetRelatedVideo {
    /**
     * Define a global instance of a Youtube object, which will be used
     * to make YouTube Data API requests.
     */
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    /**
     * Define a global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static YouTube youtube;
    private static YouTubeAnalytics analytics;
    private static List<String> relatedVideo = new ArrayList<>();
    private static String channelId = "";

    public static String getRelatedVideo(String RelatedToVideoId) {

        // This OAuth 2.0 access scope allows for write access to the authenticated user's account.
        List<String> scopes = Lists.newArrayList(
                "https://www.googleapis.com/auth/yt-analytics.readonly",
                "https://www.googleapis.com/auth/youtube.readonly"
        );

        try {
            // Authorize the request.
            Credential credential = Auth.authorize(scopes, "addlivechatmoderators");

            LiveChatModerator liveChatModerator = new LiveChatModerator();
            // This object is used to make YouTube Data API requests.
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
                    .setApplicationName("youtube-cmdline-searchvideo-sample").build();
            analytics = new YouTubeAnalytics.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                    .setApplicationName("youtube-analytics-api-report-example")
                    .build();
            // Define and execute the API request
            YouTube.Search.List request = youtube.search()
                    .list("snippet");

            YouTube.Videos.List request2 = youtube.videos()
                    .list("snippet,liveStreamingDetails,statistics,status,recordingDetails");

            SearchListResponse response = null;
            VideoListResponse myVideoResponse = request2.setId(RelatedToVideoId).execute();
            List<String> tags = myVideoResponse.getItems().get(0).getSnippet().getTags();
            if(channelId.equals("")) channelId = myVideoResponse.getItems().get(0).getSnippet().getChannelId();

            if(tags != null){ //有tag -> 用tag搜尋
                for(int i=0;i < tags.size();i++){
                    response = request.setMaxResults(10L)
                            .setQ(tags.get(i))
                            .setType("video")
                            .execute();
                    for(int j=0;j<response.size();j++){
                        if(!isMyVideo(response,j,channelId)) //非自己的影片
                            if(CosineSimilarity.tagsSimilarity(tags,request2.setId(response.getItems().get(j).getId().getVideoId()).execute().getItems().get(0).getSnippet().getTags()) > 0.001)
                                relatedVideo.add(response.getItems().get(j).getId().getVideoId());
                    }
                }
            }
            if(relatedVideo.size()<=20){ //經過上面tag演算比數少於20筆或影片無tag導致沒結果
                YouTubeAnalytics.Reports.Query requestByReport = analytics.reports()
                        .query();
                QueryResponse responseByReport = requestByReport.setDimensions("insightTrafficSourceDetail")
                        .setEndDate("2022-10-17")
                        .setFilters("video==" + RelatedToVideoId +";insightTrafficSourceType==YT_SEARCH")
                        .setIds("channel==MINE")
                        .setMaxResults(10)
                        .setMetrics("views")
                        .setSort("-views")
                        .setStartDate("2017-01-01")
                        .execute();
                List<String> searchTerm = new ArrayList<>();
                for(int i = 0;i<responseByReport.getRows().size();i++){ //拿到所有搜尋關鍵字
                    searchTerm.add(responseByReport.getRows().get(i).get(0).toString());
                }

                for(int i=0;i < searchTerm.size();i++){ //再用關鍵字搜索
                    response = request.setMaxResults(10L)
                            .setQ(searchTerm.get(i))
                            .setType("video")
                            .execute();
                    for(int j=0;j<response.size();j++){
                        if(!isMyVideo(response,j,channelId)) //非自己的影片
                            if(CosineSimilarity.tagsSimilarity(tags,request2.setId(response.getItems().get(j).getId().getVideoId()).execute().getItems().get(0).getSnippet().getTags()) > 0.001)
                                relatedVideo.add(response.getItems().get(j).getId().getVideoId());
                    }
                }
            }
            if(relatedVideo.size()<=20) { //結果還是少於20筆
                response =  request.setMaxResults(10L)
                        .setRelatedToVideoId(RelatedToVideoId)
                        .setType("video")
                        .execute();
                for(int j=0;j<response.size();j++){
                    if(!isMyVideo(response,j,channelId)) //非自己的影片
                        if(CosineSimilarity.tagsSimilarity(tags,request2.setId(response.getItems().get(j).getId().getVideoId()).execute().getItems().get(0).getSnippet().getTags()) > 0.001)
                            relatedVideo.add(response.getItems().get(j).getId().getVideoId());
                }
            }

            //System.out.println(response);
            for(int i=0;i<response.size();i++){
                System.out.println("videoID:" + response.getItems().get(i).getId().getVideoId());
                System.out.println("channelID:" + response.getItems().get(i).getSnippet().getChannelId());
                System.out.println("channelTitle:" + response.getItems().get(i).getSnippet().getChannelTitle());
                System.out.println("title:" + response.getItems().get(i).getSnippet().getTitle());

                System.out.println("cos:" + CosineSimilarity.tagsSimilarity(tags,request2.setId(response.getItems().get(i).getId().getVideoId()).execute().getItems().get(0).getSnippet().getTags()));
                System.out.println("---------------------------------");
            }

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
        return relatedVideo.toString();
    }
    private static boolean isMyVideo(SearchListResponse response,int number,String myChannelId){
        if(response.getItems().get(number).getSnippet().getChannelId().equals(myChannelId))
            return true;
        else
            return false;
    }
}
