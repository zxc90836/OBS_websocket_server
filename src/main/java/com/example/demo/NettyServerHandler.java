package com.example.demo;

import com.example.demo.youtubeAPI.ChannelData;
import com.example.demo.youtubeAPI.VoteResult;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;

@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 客戶端連線會觸發
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("Channel active......");
    }

    /**
     * 客戶端發訊息會觸發
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("伺服器收到訊息: {}", msg.toString());
        ObjectMapper mapper = new ObjectMapper();
        String temp = msg.toString();
        String type = msg.toString();
        String cmd = "";
        if(temp.indexOf(" ") != -1)
            type = temp.substring(0,temp.indexOf(" "));
        if(temp.indexOf(" ") != -1)
            cmd = temp.substring(type.length()+1,temp.length());
        switch (type){
            case "OBSServer":
                ClientMap.addNewOBSServer(ctx,cmd);
                log.info("OBSServer");
                break;
            case "addScenes":
                ClientMap.addScenes(ctx,cmd);
                log.info("addScenes");
                break;
            case "OBSServerCmd":
                break;
            case "controlClientCmd":
                ClientMap.sendMSGToOBSServer(ctx,cmd);
                log.info("controlClientCmd");
                break;
            case "addVoteData":
                VoteResult v = mapper.readValue(cmd,VoteResult.class);
                ClientMap.addVoteData(ctx,v);
                break;
            case "addChannelData":
                System.out.println(cmd);
                ChannelData d = mapper.readValue(cmd,ChannelData.class);
                ClientMap.addChannelData(ctx,d);
                break;
            case "addStreamingData":
                ClientMap.addStreamingData(ctx,cmd);
            default:
                ctx.write("我是服務端，格式錯誤，無此服務！");
                break;
        }
        ctx.write("收到訊息了！");
        ctx.flush();
    }

    /**
     * 發生異常觸發
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ClientMap.removeControlClinet(ctx);
        ClientMap.removeOBSServer(ctx);
        log.info("刪除ctx");
        ctx.close();
    }
}