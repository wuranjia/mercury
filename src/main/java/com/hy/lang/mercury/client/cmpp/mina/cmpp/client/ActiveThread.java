package com.hy.lang.mercury.client.cmpp.mina.cmpp.client;

import com.hy.lang.mercury.client.cmpp.mina.cmpp.pdu.ActiveTest;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActiveThread extends Thread {
    private IoSession session = null;
    private static final Logger logger = LoggerFactory
            .getLogger(ActiveThread.class);
    private long reconnectInterval = 5000;
    public static long lastActiveTime = 0;

    public ActiveThread(IoSession s) {
        setDaemon(true);
        this.session = s;
        lastActiveTime = System.currentTimeMillis();
    }

    public void run() {
        try {
            while (session.isConnected()) {
                long currentTime = System.currentTimeMillis();
                //if ((currentTime - lastCheckTime) > heartbeatInterval) {
                //logger.info("com.hy.lang.mercury.client.cmpp.mina.cmpp.client.ActiveThread --- CmppSession.checkConnection");
                //if ((currentTime - lastActiveTime) < (heartbeatInterval * heartbeatRetry)) {
                //logger.info("send ActiveTest");
                ActiveTest activeTest = new ActiveTest();
                activeTest.assignSequenceNumber();
                activeTest.timeStamp = currentTime;
                session.write(activeTest);
                    /*} else {
                        logger.info("connection lost!");
                        session.close(true);
                        break;
                    }*/
                //}
                try {
                    Thread.sleep(reconnectInterval);
                } catch (InterruptedException e) {
                    //
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("ActiveThread error", e);
        }
    }
}
