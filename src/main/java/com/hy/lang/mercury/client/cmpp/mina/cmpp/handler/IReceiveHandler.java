package com.hy.lang.mercury.client.cmpp.mina.cmpp.handler;

import com.hy.lang.mercury.client.cmpp.mina.cmpp.pdu.CmppPDU;
import com.hy.lang.mercury.common.McException;
import org.apache.mina.core.session.IoSession;

public interface IReceiveHandler {
    void doingWork(CmppPDU pdu, IoSession session) throws McException;
    boolean match(int commandId);
}
