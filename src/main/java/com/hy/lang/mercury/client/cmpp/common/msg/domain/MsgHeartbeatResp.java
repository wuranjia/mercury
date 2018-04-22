package com.hy.lang.mercury.client.cmpp.common.msg.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * 链路检查消息结构定义
 */
public class MsgHeartbeatResp extends MsgHead {
    private static final Logger logger = LoggerFactory.getLogger(MsgHeartbeatResp.class);
    private byte reserved;//

    public MsgHeartbeatResp(byte[] data) {
        if (data.length == 8 + 1) {
            ByteArrayInputStream bins = new ByteArrayInputStream(data);
            DataInputStream dins = new DataInputStream(bins);
            try {
                this.setTotalLength(data.length + 4);
                this.setCommandId(dins.readInt());
                this.setSequenceId(dins.readInt());
                this.reserved = dins.readByte();
                dins.close();
                bins.close();
            } catch (IOException e) {
            }
        } else {
            logger.info("链路检查,解析数据包出错，包长度不一致。长度为:" + data.length);
        }
    }

    public byte getReserved() {
        return reserved;
    }

    public void setReserved(byte reserved) {
        this.reserved = reserved;
    }
}
