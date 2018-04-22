package com.hy.lang.mercury.resource.job.cmpp;

import com.hy.lang.mercury.client.cmpp.common.msg.util.MsgAble;
import com.hy.lang.mercury.client.cmpp.common.msg.util.MsgConfig;
import com.hy.lang.mercury.client.cmpp.common.msg.util.MsgProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 接口调用
 */

//@Configuration
//@EnableScheduling
public class HeartBeatJob {
    private static final Logger logger = LoggerFactory.getLogger(HeartBeatJob.class);

   // @Autowired
    MsgAble msgProtocol;

    /**
     * 短信接口长链接，定时进行链路检查
     */
  //  @Scheduled(cron = "0/10 * * * * ?") // 每30s执行一次
    public void call() {
        logger.info("×××××××××××××开始链路检查××××××××××××××");
        int count = 0;
        boolean result = msgProtocol.sendHeartBeatMsg();
        while (!result) {
            count++;
            logger.info("retryTimes:" + count + "||, config.retryTimes:" + MsgConfig.getConnectCount() + "||,result = " + result);
            result = msgProtocol.sendHeartBeatMsg();
            if (count >= (MsgConfig.getConnectCount() - 1)) {//如果再次链路检查次数超过两次则终止连接
                break;
            }
        }
        logger.info("×××××××××××××链路检查结束×××××××××××××× , result = " + result);
    }
}
