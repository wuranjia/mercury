package com.hy.lang.mercury.client.cmpp.mina.cmpp.client;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class CmppProtocolCodecFactory implements ProtocolCodecFactory {
    private ProtocolDecoder decoder = new CmppRequestDecoder();
    private ProtocolEncoder encoder = new CmppResponseEncoder();

    public ProtocolDecoder getDecoder(IoSession sessionIn) {
        return decoder;
    }

    public ProtocolEncoder getEncoder(IoSession sessionIn) {
        return encoder;
    }

}
