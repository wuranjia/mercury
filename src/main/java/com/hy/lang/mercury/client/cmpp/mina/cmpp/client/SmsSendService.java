package com.hy.lang.mercury.client.cmpp.mina.cmpp.client;

import com.alibaba.fastjson.JSON;
import com.hy.lang.mercury.client.cmpp.common.msg.util.MsgConfig;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.pdu.Submit;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.pdu.Tools;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.sms.ShortMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SmsSendService {

    @Autowired
    private SmsSendProcess smsSendProcess;

    private static final Logger logger = LoggerFactory
            .getLogger(SmsSendService.class);

    public void addMsg(Submit submit) {
        if (smsSendProcess != null) {
            smsSendProcess.addEle(submit);
        } else {
            logger.info("smsSendProcess is not ready!!!");
        }
    }

    public int send(String dest, Long seq, String smsContent) {
        // byte[] msgId = Tools.getReqMsgId();
        Submit submit = new Submit();

        submit.setServiceId(MsgConfig.getSpId());
        submit.setSrcId(MsgConfig.getSpCode());
        submit.setMsgSrc(MsgConfig.getSpId());
        submit.setDestTermIdCount((byte) 1);
        submit.setDestTermId(new String[]{dest});//1064792596056
        submit.setDestTermIdType((byte) 0);

        // submit.setMsgId(msgId);
        //submit.assignSequenceNumber();
        submit.setSequenceNumber(Integer.valueOf(seq+""));
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
        //<SPBSJ*P:BSJGPS*T:106.014.094.120,6973*A:CMNET*N:64792596056>
        sm.setMessage(smsContent.getBytes(), (byte) 0);
        submit.setSm(sm);
        //submit.setLinkId("12345678901234567890");
        logger.info("msgId:" + seq + ",respId:" + Tools.byteArray2HexString(submit.getRespMsgId()) + ",msg:" + JSON.toJSONString(submit));

        addMsg(submit);
        return submit.getSequenceNumber();
    }
}
