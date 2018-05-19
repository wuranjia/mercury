package com.hy.lang.mercury.client.cmpp.mina.cmpp.sms;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;


public class ShortMessage extends ByteData {

    byte msgFormat = 0;//0

    byte[] messageData = null;

    String encoding = "US-ASCII";//"GBK";//"US-ASCII";

    public ByteBuffer getData() {
        ByteBuffer buffer = null;
        buffer = new ByteBuffer(messageData);
        return buffer;
    }

    public void setMessage(byte[] messageData, byte msgFormat) {
        this.messageData = messageData;
        this.msgFormat = msgFormat;
        setMsgFormat(msgFormat);
    }

    public void setMessage(String msg, byte msgFormat) {
        setMsgFormat(msgFormat);
        try {
            this.messageData = msg.getBytes(encoding);
        } catch (UnsupportedEncodingException e) {
            logger.warn("unsupportted msgFormat!", e);
        }
    }

    public String getMessage() {
        String str = "";
        try {
            str = new String(messageData, encoding);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NullPointerException e) {
            //
        }
        return str;
    }


    public int getLength() {
        return messageData == null ? 0 : messageData.length;
    }


    public String dump() {
        String rt = "\r\nShortMessage: "
                + "\r\nmsgFormat: 	" + msgFormat
                + "\r\nmsg: 			" + getMessage();
        return rt;
    }


    public byte getMsgFormat() {
        return msgFormat;
    }

    public void setData(ByteBuffer buffer) throws PDUException {
        this.messageData = buffer.getBuffer();
    }

    public void setData(byte[] data) {
        this.messageData = data;
    }

    public void setMsgFormat(byte msgFormat) {
        this.msgFormat = msgFormat;
        if (msgFormat == 0) {
            encoding = "US-ASCII";
        } else if (msgFormat == 8) {
            encoding = "UnicodeBigUnmarked";
        } else if (msgFormat == 15) {
            encoding = "GBK";
        }
    }

    public void setSm(String msg, byte msgFormat) {
        setMsgFormat(msgFormat);
        try {
            setData(msg.getBytes(encoding));
        } catch (UnsupportedEncodingException e) {
            logger.warn("msgFormat unsupportted!", e);
        }
    }

    public static void main(String[] args) {
        byte a = 15;
        System.out.println(a == 15);

        String str = "<SPBSJ*P:BSJGPS*T:106.014.094.120,6973*A:CMNET*N:64792596056>";
        ShortMessage shortMessage = new ShortMessage();
        shortMessage.setMessage(str, (byte) 0);
        System.out.println(shortMessage.getMessage());
        System.out.println(JSON.toJSONString(shortMessage));

        str = "$3/0308/苏MJX163/LFNCRULX0G1E19128/普通货车/#";
        shortMessage.setMessage(str, (byte) 0);
        System.out.println(shortMessage.getMessage());
        System.out.println(JSON.toJSONString(shortMessage));

    }
}

