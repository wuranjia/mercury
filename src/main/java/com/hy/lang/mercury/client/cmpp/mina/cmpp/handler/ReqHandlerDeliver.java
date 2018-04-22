package com.hy.lang.mercury.client.cmpp.mina.cmpp.handler;

import com.alibaba.fastjson.JSON;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.CmppConstant;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.pdu.CmppPDU;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.pdu.Deliver;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.pdu.DeliverResp;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.pdu.Tools;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.sms.ByteBuffer;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.sms.StrUtil;
import com.hy.lang.mercury.client.ws.SmsStatus;
import com.hy.lang.mercury.common.Constants;
import com.hy.lang.mercury.dao.SmsDeliverMapper;
import com.hy.lang.mercury.dao.SmsViewMapper;
import com.hy.lang.mercury.pojo.SmsDeliver;
import com.hy.lang.mercury.pojo.SmsView;
import com.hy.lang.mercury.pojo.SmsViewType;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component("CMD_DELIVER")
public class ReqHandlerDeliver implements IReceiveHandler {
    private static final Logger logger = LoggerFactory
            .getLogger(ReqHandlerDeliver.class);
    @Autowired
    private SmsDeliverMapper smsDeliverMapper;
    @Autowired
    private SmsViewMapper smsViewMapper;

    @Override
    public void doingWork(CmppPDU pdu, IoSession session) {
        logger.info("CMD_DELIVER");
        Deliver deliver = (Deliver) pdu;
        DeliverResp deliverResp = (DeliverResp) deliver
                .getResponse();
        //响应
        logger.info(deliver.getSm().getMessage());
        session.write(deliverResp);
        if (deliver.getIsReport() == 0) { //
            logger.info("sms_mo");

            ByteBuffer buffer = deliver.getSm().getData();
            SmsDeliver smsDeliver = new SmsDeliver();
            try {
                smsDeliver.setMsgId(StrUtil.bytesToHex(buffer.removeBytes(8)
                        .getBuffer()));
                smsDeliver.setCreatedBy(Constants.SYS);
                smsDeliver.setCreatedTime(new Date());
                smsDeliver.setDstId(deliver.getDstId());
                smsDeliver.setMemo(Constants.NVL);
                smsDeliver.setServiceId(deliver.getServiceId());
                smsDeliver.setSrcTermid(deliver.getSrcTermId());
                byte[] array = new byte[]{deliver.getSrcTermType()};
                smsDeliver.setSrcTermtype(Tools.byteArray2HexString(array));
                smsDeliver.setUpdatedBy(Constants.SYS);
                smsDeliver.setUpdatedTime(new Date());
                smsDeliver.setSmsContent(deliver.getMsgContent());
                logger.info("smsDeliver:" + JSON.toJSONString(smsDeliver));
                smsDeliverMapper.insert(smsDeliver);
                SmsView smsView = new SmsView(-1L,smsDeliver.getSrcTermid(), smsDeliver.getSmsContent(), SmsViewType.接收, SmsStatus.接收成功.name());
                logger.info("smsDeliver view:" + JSON.toJSONString(smsView));
                smsViewMapper.insert(smsView);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            logger.info("sms_stat");
            ByteBuffer buffer = deliver.getSm().getData();
            try {
                logger.info("buffer.length=" + buffer.length());
                logger.info("setMsgId:"
                        + (StrUtil.bytesToHex(buffer.removeBytes(8)
                        .getBuffer())));
                logger.info("setStat:" + (buffer.removeStringEx(7)));//sms_stat  sms_mo
                logger.info("setSubmitTime:"
                        + (buffer.removeStringEx(10)));
                logger.info("setDoneTime:"
                        + (buffer.removeStringEx(10)));
                logger.info("setUserNumber:"
                        + (buffer.removeStringEx(32)));
                logger.info("setSmsSequence:" + (buffer.removeInt()));
            } catch (Exception e) {
                //
                e.printStackTrace();
            }
        }

    }

    @Override
    public boolean match(int commandId) {
        return CmppConstant.CMD_DELIVER == commandId;
    }
}
