package com.hy.lang.mercury.client.cmpp.common.msg.util;

public interface MsgAble {


    boolean cancelISMG();

    boolean sendMsg(String msg, String cusMsg);

    /**
     * 发送web push短信
     *
     * @param url    wap网址
     * @param desc   描述
     * @param cusMsg 短信
     * @return
     */
    boolean sendWapPushMsg(String url, String desc, String cusMsg);

    boolean sendHeartBeatMsg();

}
