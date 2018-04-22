package com.hy.lang.mercury.client.cmpp.mina.cmpp.handler;

import com.hy.lang.mercury.client.cmpp.mina.cmpp.CmppConstant;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.client.SmsSendProcess;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.client.SmsSendService;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.pdu.CmppPDU;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.pdu.ConnectResp;
import com.hy.lang.mercury.common.McException;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component("CMD_CONNECT_RESP")
public class RespHandlerConnect implements IReceiveHandler {
    private static final Logger logger = LoggerFactory
            .getLogger(RespHandlerConnect.class);

    private ExecutorService exec = Executors.newSingleThreadExecutor();

    @Autowired
    private SmsSendService smsSendService;

    private SmsSendProcess smsSendProcess;

    @Override
    public void doingWork(CmppPDU pdu, IoSession session) throws McException {
        ConnectResp connectResp = (ConnectResp) pdu;
        logger.info("connectionResp::" + connectResp.dump()
                + " on session " + session.getId());
        if (connectResp.getStatus() == 0) {
            logger.info("start sendSmsProcess ..................begin");
            /*Thread s = new Thread(new MsgSendThread(session));
            s.setDaemon(true);
            s.start();*/
            //MsgSendThread t = new MsgSendThread(session);
            if (smsSendProcess == null) {
                smsSendProcess = new SmsSendProcess(session);
                exec.execute(smsSendProcess);
                //注入到sendService
                smsSendService.setService(smsSendProcess);
            }
            logger.info("start sendSmsProcess ..................processing");

        } else {

            throw new McException("connectResp is false, status = " + connectResp.getStatus());
        }
    }

    @Override
    public boolean match(int commandId) {
        return CmppConstant.CMD_CONNECT_RESP == commandId;
    }
}
