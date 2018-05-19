package com.hy.lang.mercury.client.cmpp.mina.cmpp.client;

import com.hy.lang.mercury.client.cmpp.common.msg.util.MsgConfig;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component("cmppClient")
public class CmppClient {
    private final static Object LOCK = new Object();
    private static final Logger logger = LoggerFactory
            .getLogger(CmppClient.class);

    @Autowired
    private SmsReceiveHandlerFactory smsReceiveHandlerFactory;

    private ConnectionThread connectionThread;


    @PostConstruct
    public void init() {
        // TODO: 18/4/18  
        /*connectionThread = new ConnectionThread();
        connectionThread.start();*/
    }

    class ConnectionThread extends Thread {
        @Override
        public void run() {
            IoConnector ioConnector = this.createConnection();
            while (ioConnector == null) {
                ioConnector = this.createConnection();
                logger.info("client connection retry");
                try {
                    logger.info("Thread.sleep(30*1000)");
                    Thread.sleep(30 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * 创建连接
         * 注册 CmppClientIoHandler
         *
         * @return
         */
        private IoConnector createConnection() {
            // create tcp/ip connector
            IoConnector connector = new NioSocketConnector();

            connector.getFilterChain().addLast(
                    "codec",
                    new ProtocolCodecFilter(
                            new CmppProtocolCodecFactory()));

            connector.setHandler(new CmppClientIoHandler(LOCK, smsReceiveHandlerFactory));
            // 添加重连监听
            connector.addListener(new IoListener() {
                @Override
                public void sessionDestroyed(IoSession arg0) throws Exception {
                    try {
                        Thread.sleep(3000);
                        ConnectionThread connectionThread = new ConnectionThread();
                        connectionThread.start();
                        /*ConnectFuture cf = connector.connect(new InetSocketAddress(
                                MsgConfig.getIsmgIp(), Integer.valueOf(MsgConfig.getIsmgPort())));
                        cf.awaitUninterruptibly();
                        cf.getSession().getCloseFuture().awaitUninterruptibly();
                        if (cf.getSession().isConnected()) {
                            logger.info("断线重连[" + MsgConfig.getIsmgIp() + ":" + MsgConfig.getIsmgPort() + "]成功");
                            break;
                        }*/
                    } catch (Exception ex) {
                        logger.info("重连服务器登录失败,3秒再连接一次:" + ex.getMessage());
                    }
                }
            });

            connector.setConnectTimeoutMillis(30000);
            for (; ; ) {
                try {
                    ConnectFuture cf = connector.connect(new InetSocketAddress(
                            MsgConfig.getIsmgIp(), Integer.valueOf(MsgConfig.getIsmgPort())));
                    cf.awaitUninterruptibly();
                    cf.getSession().getCloseFuture().awaitUninterruptibly();
                    logger.info("连接服务端" + MsgConfig.getIsmgIp() + ":" + MsgConfig.getIsmgPort() + "[成功]" + ",,时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    break;
                } catch (Exception e) {
                    logger.error("连接服务端" + MsgConfig.getIsmgIp() + ":" + MsgConfig.getIsmgPort() + "失败" + ",,时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ", 连接MSG异常,请检查MSG端口、IP是否正确,MSG服务是否启动,异常内容:" + e.getMessage(), e);
                    try {
                        Thread.sleep(5000);// 连接失败后,重连间隔5s
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            //connector.dispose();
            return connector;
        }
    }

    /*public static void main(String args[]) {
        CmppClient client = new CmppClient();
        while (true) {
            client.startup();
            logger.info("client connection retry");
            try {
                logger.info("Thread.sleep(30*1000)");
                Thread.sleep(30 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }*/


}
