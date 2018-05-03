package com.hy.lang.mercury.client.cmpp.mina.cmpp.client;

import com.alibaba.fastjson.JSON;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.handler.IReceiveHandler;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.handler.RespHandlerConnect;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.pdu.Submit;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;

@Component
public class SmsSendProcess extends Thread {

    //final成员变量表示常量，只能被赋值一次，赋值后值不再改变。
    private static final int queueSize = 500;
    private static final ArrayBlockingQueue<Submit> queue = new ArrayBlockingQueue<Submit>(queueSize);

    private static final Logger logger = LoggerFactory
            .getLogger(SmsSendProcess.class);

    public void run() {
        while (true) {
            try {
                Submit submit = queue.take();
                logger.info("msg:" + JSON.toJSONString(submit));
                RespHandlerConnect.getIoSession().write(submit);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addEle(Submit msg) {
        try {
            queue.put(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void init() {
        setDaemon(true);
    }


    /*public static void main(String[] args) {
        // SmsSendProcess process = new SmsSendProcess();
        // process.start();
        int i = 0;
        while (true) {
            addEle("msg" + i);
            if (i > 100) {
                break;
            }
            i++;
        }
    }
*/
}
