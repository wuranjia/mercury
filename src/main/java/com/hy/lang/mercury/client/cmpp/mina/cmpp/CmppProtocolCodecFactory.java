package com.hy.lang.mercury.client.cmpp.mina.cmpp;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * TODO: Document me
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 *
 */
public class CmppProtocolCodecFactory implements ProtocolCodecFactory {
  private ProtocolDecoder decoder = new CmppRequestDecoder();
  private ProtocolEncoder encoder = new CmppResponseEncoder();

  public ProtocolDecoder getDecoder(IoSession sessionIn) throws Exception {
    return decoder;
  }

  public ProtocolEncoder getEncoder(IoSession sessionIn) throws Exception {
    return encoder;
  }
}
