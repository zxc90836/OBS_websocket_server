package com.example.demo.youtubeAPI;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.api.services.youtube.model.SuperChatEventListResponse;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class GetSCDetails {
    /**
     * Define a global instance of a Youtube object, which will be used
     * to make YouTube Data API requests.
     */
    private static YouTube youtube;
    private static List<SuperChat> sc = new ArrayList<>();


    public static String getSCDetails() {

        // This OAuth 2.0 access scope allows for write access to the authenticated user's account.
        List<String> scopes = Lists.newArrayList(YouTubeScopes.YOUTUBE_FORCE_SSL);

        try {
            // Authorize the request.
            Credential credential = Auth.authorize(scopes, "GetSCDetails");

            // This object is used to make YouTube Data API requests.
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
                    .setApplicationName("youtube-cmdline-getSCdetails-sample").build();

            YouTube.SuperChatEvents.List request = youtube.superChatEvents()
                    .list("snippet");
            SuperChatEventListResponse response = request.setMaxResults(50L).execute();
            System.out.println(response);

            for(int i=0;i<response.size();i++){
                SuperChat s = new SuperChat();
                System.out.println("SCID: " + response.getItems().get(i).getId());
                System.out.println("SC發送人: " + response.getItems().get(i).getSnippet().getSupporterDetails().getDisplayName());
                System.out.println("SC發送人頻道ID: " + response.getItems().get(i).getSnippet().getSupporterDetails().getChannelId());
                System.out.println("SC等級: " + response.getItems().get(i).getSnippet().getMessageType());
                System.out.println("SC金額: " + response.getItems().get(i).getSnippet().getAmountMicros()); //(*1000000)
                System.out.println("SC貨幣: " + response.getItems().get(i).getSnippet().getCurrency()); //(ISO 4217貨幣代碼)
                System.out.println("SC內容: " + response.getItems().get(i).getSnippet().getCommentText());
                System.out.println("SC時間: " + response.getItems().get(i).getSnippet().getCreatedAt());
                System.out.println("------------------------------------------------");
                s.setId(response.getItems().get(i).getId());
                s.setFather(response.getItems().get(i).getSnippet().getSupporterDetails().getDisplayName());
                s.setFatherId(response.getItems().get(i).getSnippet().getSupporterDetails().getChannelId());
                s.setSCLevel(response.getItems().get(i).getSnippet().getMessageType());
                s.setSCAmount(response.getItems().get(i).getSnippet().getAmountMicros());
                s.setCurrency(response.getItems().get(i).getSnippet().getCurrency());
                s.setMessage(response.getItems().get(i).getSnippet().getCommentText());
                s.setCreatedAt(response.getItems().get(i).getSnippet().getCreatedAt());
                sc.add(s);
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
        return sc.toString();
    }

    public static class SuperChat{
        private String id;
        private String father;
        private String fatherId;
        private Long SCLevel;
        private BigInteger SCAmount;
        private String Currency;
        private String message;
        private DateTime createdAt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFather() {
            return father;
        }

        public void setFather(String father) {
            this.father = father;
        }

        public String getFatherId() {
            return fatherId;
        }

        public void setFatherId(String fatherId) {
            this.fatherId = fatherId;
        }

        public Long getSCLevel() {
            return SCLevel;
        }

        public void setSCLevel(Long SCLevel) {
            this.SCLevel = SCLevel;
        }

        public BigInteger getSCAmount() {
            return SCAmount;
        }

        public void setSCAmount(BigInteger SCAmount) {
            this.SCAmount = SCAmount;
        }

        public String getCurrency() {
            return Currency;
        }

        public void setCurrency(String currency) {
            Currency = currency;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public DateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(DateTime createdAt) {
            this.createdAt = createdAt;
        }

        @Override
        public String toString() {
            return "SuperChat{" +
                    "id='" + id + '\'' +
                    ", father='" + father + '\'' +
                    ", fatherId='" + fatherId + '\'' +
                    ", SCLevel='" + SCLevel + '\'' +
                    ", SCAmount=" + SCAmount +
                    ", Currency='" + Currency + '\'' +
                    ", message='" + message + '\'' +
                    ", createdAt=" + createdAt +
                    '}';
        }
    }
}
