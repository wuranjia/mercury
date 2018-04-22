package com.hy.lang.mercury.client.cmpp.mina.cmpp.handler;

import com.hy.lang.mercury.client.cmpp.mina.cmpp.CmppConstant;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.pdu.CmppPDU;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.pdu.SubmitResp;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.pdu.Tools;
import com.hy.lang.mercury.client.ws.SmsStatus;
import com.hy.lang.mercury.common.Constants;
import com.hy.lang.mercury.dao.SmsInfoMapper;
import com.hy.lang.mercury.dao.SmsViewMapper;
import com.hy.lang.mercury.pojo.SmsInfo;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("CMD_SUBMIT_RESP")
public class RespHandlerSubmit implements IReceiveHandler {

    private static final Logger logger = LoggerFactory
            .getLogger(RespHandlerSubmit.class);

    @Autowired
    private SmsInfoMapper smsInfoMapper;
    @Autowired
    private SmsViewMapper smsViewMapper;

    @Override
    public void doingWork(CmppPDU pdu, IoSession session) {
        try {
            SubmitResp submitResp = (SubmitResp) pdu;
            logger.info(submitResp.dump());
            logger.info("submitResp:" + pdu.header.getSequenceNumber()
                    + " on session " + session.getId());
            int seqNum = pdu.header.getSequenceNumber();
            String msgId = Tools.byteArray2HexString(submitResp.getMsgId());
            int result = submitResp.getResult();
            String status;
            int statusCode;
            if (result == 0) {
                //success
                status = SmsStatus.发送成功.name();
                statusCode = SmsStatus.发送成功.getCode();
            } else {
                status = SmsStatus.发送失败.name();
                statusCode = SmsStatus.发送失败.getCode();
            }
            String memo = "msgId" + Constants.冒号 + msgId + "||" + "result" + Constants.冒号 + result + "||status" + Constants.冒号 + status;
            List<SmsInfo> smsInfoList = smsInfoMapper.selectBySmsOtherNo(String.valueOf(seqNum));
            logger.info("[selectBySmsOtherNo] msgId = " + msgId + ", result = " + smsInfoList.size());
            for (SmsInfo entity : smsInfoList) {
                entity.setStatus(statusCode);
                entity.setMemo(memo);
                smsInfoMapper.updateSmsStatusAndMemo(entity.getSmsId(), entity.getStatus(), entity.getMemo());
                smsViewMapper.updateSmsStatus(status, entity.getSmsId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("submitResp", e);
        }
    }

    @Override
    public boolean match(int commandId) {

        return CmppConstant.CMD_SUBMIT_RESP == commandId;
    }

    public static void main(String[] args) {
        System.out.println(CmppConstant.CMD_SUBMIT_RESP == -2147483644);
    }
}
