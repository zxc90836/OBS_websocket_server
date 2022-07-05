package com.example.demo;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
public class ClinetMap {
    private static Map<String,Object> OBSServerMap = new HashMap<>();
    private static Map<String,String> OBSScenes = new HashMap<>();
    private static Map<Object,String> controlClientMap = new HashMap<>();
    public static String getScenes(String key){
        return OBSScenes.get(key);
    }
    public static boolean addNewControlClinet(ChannelHandlerContext ctx,String connect){
        if(OBSServerMap.containsKey(connect) == true && controlClientMap.containsKey(ctx) == false){
            controlClientMap.put(ctx,connect);
            ctx.write("connect success!!");
            ctx.flush();
            return true;
        }
        return false;
    }
    public static Object getCtxByRandomKey(String key){
        return OBSServerMap.get(key);
    }
    public static boolean addNewOBSServer(ChannelHandlerContext ctx,String scenes){
        if(OBSServerMap.containsValue(ctx) == false){
            int randomKey = (int)(Math.random()*9000000)+1000000;
            String stringValue = Integer.toString(randomKey);
            while(OBSServerMap.containsKey(stringValue) == true){
                randomKey = (int)(Math.random()*9000000)+1000000;
                stringValue = Integer.toString(randomKey);
            }
            OBSServerMap.put(stringValue,ctx);
            OBSScenes.put(stringValue,scenes);
            ctx.write(stringValue);
            ctx.flush();
            return true;
        }
        return false;
    }
    public static void sendMSGToOBSServer(ChannelHandlerContext ctx,String msg){//用控制端的ctx傳
        ChannelHandlerContext OBSserver = (ChannelHandlerContext)OBSServerMap.get(controlClientMap.get(ctx));
        OBSserver.write(msg);
        OBSserver.flush();
        log.info("sendMSGToOBSServer");
    }
    public static void sendMSGToOBSServer(String key,String msg){//用server端的key傳
        ChannelHandlerContext OBSserver = (ChannelHandlerContext)OBSServerMap.get(key);
        OBSserver.write(msg);
        OBSserver.flush();
        log.info("sendMSGToOBSServer");
    }
    public static void removeControlClinet(ChannelHandlerContext ctx){
        controlClientMap.remove(ctx);
    }
    public static void removeOBSServer(ChannelHandlerContext ctx){
        for (Map.Entry<String, Object> entry : OBSServerMap.entrySet()) {
            if(entry.getValue() == ctx){
                for(Map.Entry<String, Object> controlClient : OBSServerMap.entrySet())
                OBSServerMap.remove(entry.getKey());
                OBSScenes.remove(entry.getKey());
                break;
            }
        }
    }
}
