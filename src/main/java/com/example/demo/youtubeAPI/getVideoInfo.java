package com.example.demo.youtubeAPI;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.api.services.youtube.model.*;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class getVideoInfo {
    /**
     * Define a global instance of a Youtube object, which will be used
     * to make YouTube Data API requests.
     */
    private static YouTube youtube;
    private static Video video = new Video();

    public static Video getVideoInfo(String videoID) {

        // This OAuth 2.0 access scope allows for write access to the authenticated user's account.
        List<String> scopes = Lists.newArrayList(YouTubeScopes.YOUTUBE_FORCE_SSL);

        try {
            // Authorize the request.
            Credential credential = Auth.authorize(scopes, "getvideoinfo");

            // This object is used to make YouTube Data API requests.
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
                    .setApplicationName("youtube-cmdline-getvideoinfo-sample").build();

            YouTube.Videos.List request6 = youtube.videos()
                    .list("snippet,liveStreamingDetails,statistics,status,recordingDetails");
            VideoListResponse response = request6.setId(videoID).execute();
            //System.out.println(response);
            List<String> tags = response.getItems().get(0).getSnippet().getTags(); //tags
            String description = response.getItems().get(0).getSnippet().getDescription(); //說明欄
            String uploadStatus = response.getItems().get(0).getStatus().getPrivacyStatus(); //影片權限
            GeoPoint geoPoint = response.getItems().get(0).getRecordingDetails().getLocation(); //影片位置(座標)

            String title = response.getItems().get(0).getSnippet().getTitle(); //標題
            String imgURL = response.getItems().get(0).getSnippet().getThumbnails().getHigh().getUrl(); //封面圖網址
            BigInteger commentCount = response.getItems().get(0).getStatistics().getCommentCount(); //留言數
            BigInteger dislikeCount = response.getItems().get(0).getStatistics().getDislikeCount(); //不喜歡數
            BigInteger likeCount = response.getItems().get(0).getStatistics().getLikeCount(); //喜歡數
            BigInteger viewCount = response.getItems().get(0).getStatistics().getViewCount(); //觀看次數
            BigInteger concurrentViewers = null;
            DateTime actualStartTime = null;
            DateTime scheduledStartTime = null;
            DateTime actualEndTime = null;
            if(response.getItems().get(0).getLiveStreamingDetails() != null){ //直播影片
                if(response.getItems().get(0).getSnippet().getLiveBroadcastContent().equals("live")){ //正在直播
                    concurrentViewers = response.getItems().get(0).getLiveStreamingDetails().getConcurrentViewers(); //當下觀看人數
                    actualStartTime = response.getItems().get(0).getLiveStreamingDetails().getActualStartTime(); //實際開始時間
                }
                scheduledStartTime = response.getItems().get(0).getLiveStreamingDetails().getScheduledStartTime(); //表定開始時間
                actualEndTime = response.getItems().get(0).getLiveStreamingDetails().getActualEndTime(); //實際結束時間
            }
            System.out.println("tags:" + tags);
            System.out.println("說明欄:" + description);
            System.out.println("影片權限:" + uploadStatus);
            System.out.println("上傳位址:" + geoPoint);
            System.out.println("標題:" + title);
            System.out.println("封面圖網址(高清):" + imgURL);
            System.out.println("留言數:" + commentCount);
            System.out.println("不喜歡數:" + dislikeCount);
            System.out.println("喜歡數:" + likeCount);
            System.out.println("觀看次數:" + viewCount);
            System.out.println("當下觀看人數:" + concurrentViewers);
            System.out.println("實際開始時間:" + actualStartTime);
            System.out.println("表定開始時間:" + scheduledStartTime);
            System.out.println("實際結束時間:" + actualEndTime);

            video.setId(videoID);
            video.setTags(tags);
            video.setDescription(description);
            video.setUploadStatus(uploadStatus);
            video.setGeoPoint(geoPoint);
            video.setTitle(title);
            video.setImgURL(imgURL);
            video.setCommentCount(commentCount);
            video.setDislikeCount(dislikeCount);
            video.setLikeCount(likeCount);
            video.setViewCount(viewCount);
            video.setConcurrentViewers(concurrentViewers);
            video.setActualStartTime(actualStartTime);
            video.setScheduledStartTime(scheduledStartTime);
            video.setActualEndTime(actualEndTime);

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
        return video;
    }
}
