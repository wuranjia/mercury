package com.hy.lang.mercury.client.cmpp.mina.cmpp.client;

import com.hy.lang.mercury.client.cmpp.mina.cmpp.handler.IReceiveHandler;
import com.hy.lang.mercury.service.impl.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class SmsReceiveHandlerFactory {

    private static final Logger logger = LoggerFactory
            .getLogger(SmsReceiveHandlerFactory.class);

    @Autowired
    private SmsService smsService;

    @Autowired
    @Qualifier("CMD_CONNECT_RESP")
    private IReceiveHandler CMD_CONNECT_RESP;

    @Autowired
    @Qualifier("CMD_ACTIVE_TEST_RESP")
    private IReceiveHandler CMD_ACTIVE_TEST_RESP;

    @Autowired
    @Qualifier("CMD_ACTIVE_TEST")
    private IReceiveHandler CMD_ACTIVE_TEST;

    @Autowired
    @Qualifier("CMD_SUBMIT_RESP")
    private IReceiveHandler CMD_SUBMIT_RESP;

    @Autowired
    @Qualifier("CMD_DELIVER")
    private IReceiveHandler CMD_DELIVER;

    private List<IReceiveHandler> list;

    @PostConstruct
    public void init() {
        list = new ArrayList<IReceiveHandler>();
        list.add(CMD_CONNECT_RESP);
        list.add(CMD_ACTIVE_TEST_RESP);
        list.add(CMD_ACTIVE_TEST);
        list.add(CMD_SUBMIT_RESP);
        list.add(CMD_DELIVER);
    }

    /**
     * switch (pdu.header.getCommandId()) {
     * case CmppConstant.CMD_CONNECT_RESP:
     *
     * break;
     * case CmppConstant.CMD_ACTIVE_TEST_RESP:
     * //ActiveTestResp activeTestRsp = (ActiveTestResp) pdu;
     * pdu.dump();
     * logger.info("activeTestRsp:" + pdu.header.getSequenceNumber()
     * + " on session " + session.getId());
     * ActiveThread.lastActiveTime = System.currentTimeMillis();
     * break;
     * case CmppConstant.CMD_ACTIVE_TEST:
     * ActiveTest activeTest = (ActiveTest) pdu;
     * ActiveTestResp activeTestResp = (ActiveTestResp) activeTest
     * .getResponse();
     * session.write(activeTestResp);
     * logger.info("active_test:" + pdu.header.getSequenceNumber()
     * + " on session " + session.getId());
     * break;
     * case CmppConstant.CMD_SUBMIT_RESP:
     * //SubmitResp subresp = (SubmitResp) pdu;
     * pdu.dump();
     * logger.info("submitResp:" + pdu.header.getSequenceNumber()
     * + " on session " + session.getId());
     * break;
     * case CmppConstant.CMD_DELIVER:
     * logger.info("CMD_DELIVER");
     * Deliver deliver = (Deliver) pdu;
     * DeliverResp cmppDeliverResp = (DeliverResp) deliver
     * .getResponse();
     * logger.info(deliver.getSm().getMessage());
     * session.write(cmppDeliverResp);
     * if (deliver.getIsReport() == 0) { //
     * logger.info("sms_mo");
     * } else {
     * logger.info("sms_stat");
     * ByteBuffer buffer = deliver.getSm().getData();
     * try {
     * logger.info("buffer.length=" + buffer.length());
     * logger.info("setMsgId:"
     * + (StrUtil.bytesToHex(buffer.removeBytes(8)
     * .getBuffer())));
     * logger.info("setStat:" + (buffer.removeStringEx(7)));
     * logger.info("setSubmitTime:"
     * + (buffer.removeStringEx(10)));
     * logger.info("setDoneTime:"
     * + (buffer.removeStringEx(10)));
     * logger.info("setUserNumber:"
     * + (buffer.removeStringEx(32)));
     * logger.info("setSmscSequence:" + (buffer.removeInt()));
     * } catch (Exception e) {
     * //
     * }
     * }
     * break;
     * default:
     * logger.warn("Unexpected PDU received! PDU Header: "
     * + pdu.header.getData().getHexDump());
     * break;
     * }
     *
     * @param commandId
     * @return
     */
    public IReceiveHandler pick(int commandId) {
        for (IReceiveHandler entity : list) {
            if (entity.match(commandId)) {
                return entity;
            }
        }
        logger.info("no match handler");
        return null;
    }

}
