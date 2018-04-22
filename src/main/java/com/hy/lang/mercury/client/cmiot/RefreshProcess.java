package com.hy.lang.mercury.client.cmiot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class RefreshProcess extends Thread {

    //final成员变量表示常量，只能被赋值一次，赋值后值不再改变。
    private static final int queueSize = 200000;
    private static final ArrayBlockingQueue<ScheduleService.SingleThreed> queue = new ArrayBlockingQueue<ScheduleService.SingleThreed>(queueSize);

    private static final Logger logger = LoggerFactory
            .getLogger(RefreshProcess.class);

    private ExecutorService exec = Executors.newFixedThreadPool(30);


    public RefreshProcess() {
        setDaemon(true);
    }

    public void run() {
        while (true) {
            try {
                ScheduleService.SingleThreed submit = queue.take();
                //logger.info("msg:" + JSON.toJSONString(submit));
                exec.execute(submit);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addEle(ScheduleService.SingleThreed msg) {
        try {
            queue.put(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
