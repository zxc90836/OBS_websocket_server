package com.example.demo.youtubeAPI;

import java.util.*;

public class CosineSimilarity {

    private static List<String> targetTags = Arrays.asList("vtuber", "新人vtuberを発掘する", "RTしたvtuber全員フォローする", "Q妹","雑談","歌枠");
    private static List<String> compareTags = Arrays.asList("欣希亞", "欣婝子", "就醬實驗室", "虛擬創作者", "TWVtuber", "Vtuber", "台灣Vtuber", "欣希亞 Live", "台V", "閒聊", "雜談", "Live", "Cynia Live", "Cynia", "シンシヤ", "孟欣亞","Asterigos", "失落迷城", "Asterigos：失落迷城", "群星的詛咒", "失落迷城：群星的詛咒", "Curse Of The Stars");

    static double tagsSimilarity(List<String> targetTags, List<String> compareTags){
        List<Integer> targetList = new ArrayList<>();
        List<Integer> compareList = new ArrayList<>();

        if(compareTags == null || targetTags == null) return 0;
        Set<String> tmpSet = new HashSet<>(targetTags);
        tmpSet.addAll(compareTags);
        List<String> totalList = new ArrayList<String>();

        totalList.addAll(tmpSet); //全部的tag(無重複)
        System.out.println(tmpSet);

        for(int i=0;i<totalList.size();i++){
            int count = 0;
            targetList.add(count++);
            for(int j=0;j<targetTags.size();j++){
                //是否重複(忽略大小寫、tag包含於其他tag內)
                if(totalList.get(i).equalsIgnoreCase(targetTags.get(j)) || totalList.get(i).contains(targetTags.get(j))) targetList.set(i,count++);
            }
        }
        System.out.println(targetList);

        for(int i=0;i<totalList.size();i++){
            int count = 0;
            compareList.add(count++);
            for(int j=0;j<compareTags.size();j++){
                if(totalList.get(i).equalsIgnoreCase(compareTags.get(j)) || totalList.get(i).contains(compareTags.get(j))) compareList.set(i,count++);
            }
        }
        System.out.println(compareList);

        return CosineCalculate(targetList,compareList);
    }

    public static double CosineCalculate(List<Integer> listA,List<Integer> listB){
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < listA.size(); i++) {
            dotProduct += listA.get(i) * listB.get(i);
            normA += Math.pow(listA.get(i), 2);
            normB += Math.pow(listB.get(i), 2);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    public static void main(String[] args){
        System.out.println(tagsSimilarity(targetTags,compareTags));
    }

}
