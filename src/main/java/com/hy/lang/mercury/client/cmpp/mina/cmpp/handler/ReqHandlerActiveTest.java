package com.hy.lang.mercury.client.cmpp.mina.cmpp.handler;

import com.hy.lang.mercury.client.cmpp.mina.cmpp.CmppConstant;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.pdu.ActiveTest;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.pdu.ActiveTestResp;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.pdu.CmppPDU;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("CMD_ACTIVE_TEST")
public class ReqHandlerActiveTest implements IReceiveHandler {
    private static final Logger logger = LoggerFactory
            .getLogger(ReqHandlerActiveTest.class);


    @Override
    public void doingWork(CmppPDU pdu, IoSession session) {
        ActiveTest activeTest = (ActiveTest) pdu;
        ActiveTestResp activeTestResp = (ActiveTestResp) activeTest
                .getResponse();
        session.write(activeTestResp);
        if (session.getId() % 100 == 0) {
            logger.info("active_test:" + pdu.header.getSequenceNumber()
                    + " on session " + session.getId());
        }

    }

    @Override
    public boolean match(int commandId) {

        return CmppConstant.CMD_ACTIVE_TEST == commandId;
    }
}
