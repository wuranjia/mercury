package com.hy.lang.mercury.client.cmpp.mina.cmpp.client;


import com.hy.lang.mercury.client.cmpp.common.msg.util.MsgConfig;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.CmppConstant;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.handler.IReceiveHandler;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.pdu.CmppPDU;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.pdu.Connect;
import com.hy.lang.mercury.common.SpringContextUtils;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import static com.hy.lang.mercury.client.cmpp.mina.cmpp.MinaCmpp.MSG_COUNT;
import static com.hy.lang.mercury.client.cmpp.mina.cmpp.MinaCmpp.OPEN;


/**
 */
public class CmppClientIoHandler extends IoHandlerAdapter {
    private static final Logger logger = LoggerFactory
            .getLogger(CmppClientIoHandler.class);
    public static AtomicInteger received = new AtomicInteger(0);
    public static AtomicInteger closed = new AtomicInteger(0);
    private final Object LOCK;
    public static boolean Connect = false;
    public static boolean FirstMsg = true;

    private final SmsReceiveHandlerFactory smsReceiveHandlerFactory;

    public CmppClientIoHandler(Object lock, SmsReceiveHandlerFactory smsReceiveHandlerFactory) {
        LOCK = lock;
        this.smsReceiveHandlerFactory = smsReceiveHandlerFactory;
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        if (!(cause instanceof IOException)) {
            logger.error("Exception: ", cause);
        } else {
            logger.info("I/O error: " + cause.getMessage());
        }
        session.close(true);
    }

    @Override
    public void sessionOpened(IoSession session) {
        logger.info("Session " + session.getId() + " is opened");
        Connect(session);
//		ExecutorService exec = Executors.newSingleThreadExecutor();
//		exec.execute(new ActiveThread(session));
        Thread t = new Thread(new ActiveThread(session));
        t.setDaemon(true);
        t.start();
        session.resumeRead();
        Connect = true;
    }

    /**
     * 连接信息认证
     *
     * @param session
     */
    public void Connect(IoSession session) {
        Connect request = new Connect(
                CmppConstant.TRANSMITTER);
        // TODO: 2018/4/5
        request.setClientId(MsgConfig.getSpId());
        request.setSharedSecret(MsgConfig.getSpSharedSecret());
        request.setTimeStamp(request.genTimeStamp());
        request.setAuthClient(request.genAuthClient());
        request.setVersion((byte) 0x30);
        request.assignSequenceNumber();
        logger.info("Connect: " + request.getData().getHexDump());
        logger.info(request.dump());
        session.write(request);
    }

    @Override
    public void sessionCreated(IoSession session) {
        logger.info("Creation of session " + session.getId());
        session.setAttribute(OPEN);
        session.suspendRead();

    }

    @Override
    public void sessionClosed(IoSession session) {
        session.removeAttribute(OPEN);
        logger.error("{}> Session closed", session.getId());
        final int clsd = closed.incrementAndGet();

        if (clsd == MSG_COUNT) {
            synchronized (LOCK) {
                LOCK.notifyAll();
            }
        }
    }

    @Override
    public void messageReceived(IoSession session, Object message) {
        CmppPDU pdu = (CmppPDU) message;
        try {
        logger.info("MESSAGE: " + pdu.header.getCommandId() + ":"
                + pdu.header.getSequenceNumber() + "on session "
                + session.getId());
            final int rec = received.incrementAndGet();
            if (FirstMsg == true || Connect == true) {
                FirstMsg = false;
                int commandId = pdu.header.getCommandId();
                IReceiveHandler handler = smsReceiveHandlerFactory.pick(commandId);
                if (handler != null) {
                    try {
                        handler.doingWork(pdu, session);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Connect = false;
                        session.close(true);
                    }
                }
            }
            if (rec == MSG_COUNT) {
                synchronized (LOCK) {
                    LOCK.notifyAll();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("MESSAGE: " + pdu.header.getCommandId() + ":"
                    + pdu.header.getSequenceNumber() + "on session ");
        }
        // session.close(true);
    }
}
