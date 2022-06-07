package com.example.demo;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

@Component
@Slf4j
public class NettyServer {
    /**
     * boss 執行緒組用於處理連線工作
     */
    private EventLoopGroup boss = new NioEventLoopGroup();
    /**
     * work 執行緒組用於資料處理
     */
    private EventLoopGroup work = new NioEventLoopGroup();


    @Value("${netty.port}")
    private Integer port;

    /**
     * 啟動Netty Server
     *
     * @throws InterruptedException
     */;
    @PostConstruct
    public void start() throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, work)
                // 指定Channel
                .channel(NioServerSocketChannel.class)
                //使用指定的埠設定套接字地址
                .localAddress(new InetSocketAddress(port))

                //服務端可連線佇列數,對應TCP/IP協議listen函式中backlog引數
                .option(ChannelOption.SO_BACKLOG, 1024)

                //設定TCP長連線,一般如果兩個小時內沒有資料的通訊時,TCP會自動傳送一個活動探測資料報文
                .childOption(ChannelOption.SO_KEEPALIVE, true)

                //將小的資料包包裝成更大的幀進行傳送，提高網路的負載
                .childOption(ChannelOption.TCP_NODELAY, true)

                .childHandler(new ServerChannelInitializer());
        ChannelFuture future = bootstrap.bind().sync();
        if (future.isSuccess()) {
            log.info("啟動 Netty Server");
        }
    }

    @PreDestroy
    public void destory() throws InterruptedException {
        boss.shutdownGracefully().sync();
        work.shutdownGracefully().sync();
        log.info("關閉Netty");
    }
}
