package com.hy.lang.mercury.client.cmpp.mina.cmpp;


import com.hy.lang.mercury.client.cmpp.mina.cmpp.pdu.*;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.sms.ByteBuffer;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.sms.ShortMessage;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static com.hy.lang.mercury.client.cmpp.mina.cmpp.MinaCmpp.MSG_COUNT;
import static com.hy.lang.mercury.client.cmpp.mina.cmpp.MinaCmpp.OPEN;


/**
 * TODO: Document me !
 * 
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 * 
 */
public class CmppIoHandler extends IoHandlerAdapter {
	private static final Logger logger = LoggerFactory
			.getLogger(CmppIoHandler.class);
	public static AtomicInteger received = new AtomicInteger(0);
	public static AtomicInteger closed = new AtomicInteger(0);
	private final Object LOCK;
	public static boolean Connect = false;
	public static boolean Firstmsg = true;
	private ExecutorService exec = Executors.newSingleThreadExecutor();
	public CmppIoHandler(Object lock) {
		LOCK = lock;
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
	public void sessionOpened(IoSession session) throws Exception {
		logger.info("Session " + session.getId() + " is opened");

		// ����ActivePDU-Thread
		// ExecutorService exec = Executors.newSingleThreadExecutor();
		// exec.execute(new ActiveThread(session));
		Thread t = new Thread(new ActiveThread(session));
		t.setDaemon(true);
		t.start();
		session.resumeRead();

	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		logger.info("Creation of session " + session.getId());
		session.setAttribute(OPEN);
		session.suspendRead();

	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		session.removeAttribute(OPEN);
		logger.info("{}> Session closed", session.getId());
		final int clsd = closed.incrementAndGet();

		if (clsd == MSG_COUNT) {
			synchronized (LOCK) {
				LOCK.notifyAll();
			}
		}
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		CmppPDU pdu = (CmppPDU) message;
		logger.info("MESSAGE: " + pdu.header.getCommandId() + ":"
				+ pdu.header.getSequenceNumber() + "on session "
				+ session.getId());
		final int rec = received.incrementAndGet();
		if (Firstmsg == true || Connect == true) {
			Firstmsg = false;
			switch (pdu.header.getCommandId()) {
			case CmppConstant.CMD_CONNECT:
				Connect con = (Connect) pdu;
				ConnectResp conresp = (ConnectResp) con
						.getResponse();
				session.write(conresp);
				logger.info("conresp:" + pdu.header.getSequenceNumber()
						+ " on session " + session.getId());
				Connect = true;
//				Thread t2 = new Thread(new SendMoThread(session));
//				t2.setDaemon(true);
//				t2.start();
				exec.execute(new SendMoThread(session));
				break;
			case CmppConstant.CMD_ACTIVE_TEST:
				ActiveTest activeTest = (ActiveTest) pdu;
				ActiveTestResp activeTestResp = (ActiveTestResp) activeTest
						.getResponse();
				session.write(activeTestResp);
				logger.info("active_test:" + pdu.header.getSequenceNumber()
						+ " on session " + session.getId());
				break;
			case CmppConstant.CMD_ACTIVE_TEST_RESP:
				ActiveTestResp activeTestRsp = (ActiveTestResp) pdu;
				pdu.dump();
				logger.info("activeTestRsp:" + pdu.header.getSequenceNumber()
						+ " on session " + session.getId());
				ActiveThread.lastActiveTime = System.currentTimeMillis();
				break;
			case CmppConstant.CMD_SUBMIT:
				Submit submit = (Submit) pdu;
				submit.dump();
				SubmitResp subresp = (SubmitResp) submit
						.getResponse();
				subresp.setMsgId(Tools.getRespId());
				session.write(subresp);
				logger.info("subresp:" + pdu.header.getSequenceNumber()
						+ " on session " + session.getId());
				// ����״̬����
				Deliver deliver = sendMsgStat(submit);
				session.write(deliver);
				break;
			case CmppConstant.CMD_DELIVER_RESP:
				DeliverResp delresp = (DeliverResp) pdu;
				delresp.dump();
				logger.info("DeliverResp:" + pdu.header.getSequenceNumber()
						+ " on session " + session.getId());
				break;
			default:
				logger.warn("Unexpected PDU received! PDU Header: "
						+ pdu.header.getData().getHexDump());
				break;
			}
		}
		if (rec == MSG_COUNT) {
			synchronized (LOCK) {
				LOCK.notifyAll();
			}
		}

		// session.close(true);
	}

	private Deliver sendMsgStat(Submit submit) {
		// TODO Auto-generated method stub
		Deliver delive = new Deliver();
		delive.setMsgId(Tools.getReqMsgId());
		delive.setDstId(submit.getMsgSrc());
		delive.setServiceId(submit.getServiceId());
		delive.setSrcTermId(submit.getDestTermId()[0]);
		delive.setIsReport((byte) 1);
		delive.assignSequenceNumber();
		ShortMessage sm = new ShortMessage();
		ByteBuffer messageData = new ByteBuffer();
		messageData.appendBytes(submit.getMsgId(), 8);
		messageData.appendString("0", 7);
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm");
		messageData.appendString(sdf.format(new Date()), 10);
		messageData.appendString(sdf.format(new Date()), 10);
		messageData.appendString(submit.getDestTermId()[0], 32);
		messageData.appendInt(30);
		sm.setMessage(messageData.getBuffer(), (byte) 04);
		delive.setSm(sm);
		delive.setLinkId(submit.getLinkId());
		return delive;
	}
}
