package com.example.demo.youtubeAPI;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.youtubeAnalytics.v2.YouTubeAnalytics;
import com.google.api.services.youtubeAnalytics.v2.model.QueryResponse;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.List;

public class GetVideoHistoryInfo {
    /**
     * Define a global instance of a Youtube object, which will be used
     * to make YouTube Data API requests.
     */
    private static YouTubeAnalytics youtube;
    private static String result = "";


    public static String getVideoHistoryInfo(String videoID,String startDate,String endDate) {

        // This OAuth 2.0 access scope allows for write access to the authenticated user's account.
        List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/yt-analytics.readonly","https://www.googleapis.com/auth/yt-analytics-monetary.readonly");

        try {
            // Authorize the request.
            Credential credential = Auth.authorize(scopes, "getvideohistoryinfo");

            // This object is used to make YouTube Data API requests.
            youtube = new YouTubeAnalytics.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
                    .setApplicationName("youtube-cmdline-getvideohistoryinfo-sample").build();

            // Define and execute the API request
            YouTubeAnalytics.Reports.Query request = youtube.reports()
                    .query();
            QueryResponse response = request.setEndDate(endDate)
                    .setFilters("video==" + videoID)
                    .setIds("channel==MINE")
                    .setMetrics("averageViewDuration,cardClicks,subscribersGained,averageViewDuration,grossRevenue,cpm")
                    .setStartDate(startDate)
                    .execute();
            System.out.println(response);
            result = response.toString();

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
        return result;
    }

    public static String getVideoHistoryInfo(String startDate,String endDate) {

        // This OAuth 2.0 access scope allows for write access to the authenticated user's account.
        List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/yt-analytics.readonly","https://www.googleapis.com/auth/yt-analytics-monetary.readonly");

        try {
            // Authorize the request.
            Credential credential = Auth.authorize(scopes, "getvideohistoryinfo");

            // This object is used to make YouTube Data API requests.
            youtube = new YouTubeAnalytics.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
                    .setApplicationName("youtube-cmdline-getvideohistoryinfo-sample").build();

            // Define and execute the API request
            YouTubeAnalytics.Reports.Query request = youtube.reports()
                    .query();
            QueryResponse response = request.setEndDate(endDate)
                    .setIds("channel==MINE")
                    .setMetrics("averageViewDuration,cardClicks,subscribersGained,averageViewDuration,grossRevenue,cpm")
                    .setStartDate(startDate)
                    .execute();
            System.out.println(response);
            result = response.toString();

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
        return result;
    }
}