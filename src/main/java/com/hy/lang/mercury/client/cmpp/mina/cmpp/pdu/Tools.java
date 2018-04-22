package com.hy.lang.mercury.client.cmpp.mina.cmpp.pdu;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools {
    private static int msgi = 1000;
    private static Object obj = new Object();
    private static Object obj2 = new Object();

    public static byte[] getReqMsgId() {
        String s_msg = "";
        Date nowTime = new Date();
        SimpleDateFormat time = new SimpleDateFormat("yyMMddHHmmss");
        byte[] msgid = null;
        try {
            synchronized (obj) {
                msgi = msgi + 1;
            }
            if (msgi >= 9999)
                msgi = 1000;
            s_msg = time.format(nowTime) + msgi;
            // logger.info(s_msg);
            msgid = hexString2ByteArray(s_msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msgid;
    }

    public static byte[] getRespId() {
        String s_msg = "";
        Date nowTime = new Date();
        SimpleDateFormat time = new SimpleDateFormat("yyMMddHHmmss");
        byte[] msgid = null;
        try {
            synchronized (obj2) {
                msgi = msgi + 1;
            }
            if (msgi >= 9999)
                msgi = 1000;
            s_msg = time.format(nowTime) + msgi;
            msgid = hexString2ByteArray(s_msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msgid;
    }

    public static byte[] hexString2ByteArray(String hex) {
        if (hex.length() % 2 != 0)
            return null;
        byte[] buf = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i += 2) {
            char c0 = hex.charAt(i);
            char c1 = hex.charAt(i + 1);
            byte b0 = hexChar2byte(c0);
            byte b1 = hexChar2byte(c1);
            if (b0 < 0 || b0 > 15)
                return null;
            if (b1 < 0 || b1 > 15)
                return null;
            buf[i / 2] = (byte) ((b0 << 4) | b1);
        }
        return buf;
    }

    public static byte hexChar2byte(char c) {
        if (c >= '0' && c <= '9')
            return (byte) (c - '0');
        if (c >= 'a' && c <= 'f')
            return (byte) (c - 'a' + 10);
        if (c >= 'A' && c <= 'F')
            return (byte) (c - 'A' + 10);
        return -1;
    }

    public static String byteArray2HexString(byte[] buf) {
        char[] hexTable = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            byte b = buf[i];
            byte b0 = (byte) (b & 0x0F);
            b = (byte) (b >>> 4);
            byte b1 = (byte) (b & 0x0F);
            sb.append(hexTable[b1]).append(hexTable[b0]);
        }
        return sb.toString();
    }

    public static byte[] int2byte(int res) {
        byte[] targets = new byte[4];

        targets[0] = (byte) (res & 0xff);// ���λ
        targets[1] = (byte) ((res >> 8) & 0xff);// �ε�λ
        targets[2] = (byte) ((res >> 16) & 0xff);// �θ�λ
        targets[3] = (byte) (res >>> 24);// ���λ,�޷������ơ�
        return targets;
    }

    public static int byte2int(byte[] res) {
        int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00) // | ��ʾ��λ��
                | ((res[2] << 24) >>> 8) | (res[3] << 24);
        return targets;
    }

    public static String decodeMsgId(byte[] bt) {

        int mask = 0xf0;
        int month = (bt[0] & mask) >> 4;
        System.out.println("Month:" + month);

        mask = 0xF;
        int date = (bt[0] & mask) << 1;

        mask = 0x80;
        int date2 = bt[1] >> 7;
        date = (date & (date2 | 0x1E));
        System.out.println("Date:" + date);

        //01111100
        mask = 0x7C;
        int hour = (bt[1] & mask) >> 2;
        System.out.println("Hour:" + hour);

        mask = 0x3;
        int minute = (bt[1] & mask) << 4;
        minute |= (bt[2] & 0xF0) >> 4;
        System.out.println("Minute:" + minute);

        mask = 0xf;
        int second = (bt[2] & mask) << 2;
        second |= (bt[3] & 0xff) >> 6;
        System.out.println("Second:" + second);

        mask = 0x3F;
        int Msg_Id = (bt[3] & mask) << 16;
        Msg_Id |= (bt[4] & 0xFF) << 8;
        Msg_Id |= (bt[5] & 0xFF);
        System.out.println("Msg_Id:" + Msg_Id);

        mask = 0xFF;
        int seq_id = (bt[6] & mask) << 8;
        seq_id |= (bt[7] & mask);
        System.out.println("seq_id:" + seq_id);

        return seq_id + "";
    }

}
