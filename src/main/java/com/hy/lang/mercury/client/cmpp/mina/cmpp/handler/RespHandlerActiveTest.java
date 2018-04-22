package com.hy.lang.mercury.client.cmpp.mina.cmpp.handler;

import com.hy.lang.mercury.client.cmpp.mina.cmpp.ActiveThread;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.CmppConstant;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.client.CmppClientIoHandler;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.pdu.CmppPDU;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("CMD_ACTIVE_TEST_RESP")
public class RespHandlerActiveTest implements IReceiveHandler {
    private static final Logger logger = LoggerFactory
            .getLogger(RespHandlerActiveTest.class);

    @Override
    public void doingWork(CmppPDU pdu, IoSession session) {
        //ActiveTestResp activeTestRsp = (ActiveTestResp) pdu;
        //logger.info(pdu.dump());
        if (session.getId() % 100 == 0) {
            logger.info("activeTestRsp:" + pdu.header.getSequenceNumber()
                    + " on session " + session.getId());
        }
        ActiveThread.lastActiveTime = System.currentTimeMillis();
        CmppClientIoHandler.Connect = true;
    }

    @Override
    public boolean match(int commandId) {
        return (CmppConstant.CMD_ACTIVE_TEST_RESP == commandId);
    }
}
