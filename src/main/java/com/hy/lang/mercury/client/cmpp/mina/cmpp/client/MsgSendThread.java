package com.hy.lang.mercury.client.cmpp.mina.cmpp.client;

import com.alibaba.fastjson.JSON;
import com.hy.lang.mercury.client.cmpp.common.msg.util.MsgConfig;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.CmppConstant;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.pdu.Query;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.pdu.Submit;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.pdu.Tools;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.sms.ShortMessage;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MsgSendThread extends Thread {
    private static BufferedReader keyboard = new BufferedReader(
            new InputStreamReader(System.in));
    private IoSession session;
    private static final Logger logger = LoggerFactory
            .getLogger(MsgSendThread.class);

    public MsgSendThread(IoSession s) {
        setDaemon(true);
        this.session = s;
    }

    public void run() {
        try {
            String option = "1";
            int optionInt;
            while (session.isConnected() & CmppClientIoHandler.Connect == true) {
                logger.info(">Please input your option: ");
                logger.info(">1 shutdown");
                logger.info(">2 test");
                System.out.print(">");
                try {
                    option = keyboard.readLine();
                    optionInt = Integer.parseInt(option);
                } catch (Exception e) {
                    logger.info("" + e + "\nPlease input an option number");
                    optionInt = -1;
                }
                //optionInt = 2;
                switch (optionInt) {
                    case 1:
                        shutdown();
                        break;
                    case 2:
                        test();
                        break;
                    case 3:
                        queryResult();
                        break;
                    case -1:
                        // default option if entering an option went wrong
                        break;
                    default:
                        logger.info("Invalid option. Choose between 0 and 10.");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void shutdown() {
        if (session != null)
            session.close(true);
    }

    private void test() {
        byte[] msgId = Tools.getReqMsgId();
        Submit submit = new Submit();
        submit.setServiceId(MsgConfig.getSpId());
        submit.setSrcId(MsgConfig.getSpCode());
        submit.setMsgSrc(MsgConfig.getSpId());
        submit.setDestTermIdCount((byte) 1);
        submit.setDestTermId(new String[]{"13122867838"});
        submit.setDestTermIdType((byte) 0);

        submit.setMsgId(msgId);
        submit.assignSequenceNumber();
        submit.setFeeCode("000000");
        /**
         * 被计费用户的号码，当Fee_UserType为3时该值有效，当Fee_UserType为0、1、2时该值无意义。
         */
        submit.setFeeTermId("");
        /**
         * 被计费用户的号码类型，0：真实号码；1：伪码。
         */
        submit.setFeeTermType((byte) 0);
        /**
         * 计费用户类型字段：
         * 0：对目的终端MSISDN计费；
         * 1：对源终端MSISDN计费；
         * 2：对SP计费；
         * 3：表示本字段无效，对谁计费参见Fee_terminal_Id字段。
         */
        submit.setFeeUserType((byte) 0);
        /**
         * 资费类别：
         * 01：对“计费用户号码”免费；
         * 02：对“计费用户号码”按条计信息费；
         * 03：对“计费用户号码”按包月收取信息费。
         */
        submit.setFeeType("02");
        ShortMessage sm = new ShortMessage();
        String msg = "<SPBSJ*P:BSJGPS*T:106.014.094.120,6973*A:CMNET*N:64792596056>";
        sm.setMessage(msg.getBytes(), (byte) 15);
        submit.setSm(sm);
        submit.setLinkId("12345678901234567890");
        logger.info("msg:" + JSON.toJSONString(submit));
        session.write(submit);
    }

    public void queryResult() {
        Query query = new Query();
        query.setTime("20180405");
        query.setQueryType((byte) 0);
        query.setQueryCode(MsgConfig.getSpId());
        query.setCommandId(CmppConstant.CMD_QUERY);
        logger.info("msg:" + JSON.toJSONString(query));
        session.write(query);
    }


}
