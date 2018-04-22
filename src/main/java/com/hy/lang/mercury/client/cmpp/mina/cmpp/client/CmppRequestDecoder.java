package com.hy.lang.mercury.client.cmpp.mina.cmpp.client;


import com.hy.lang.mercury.client.cmpp.mina.cmpp.pdu.CmppPDU;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.pdu.CmppPDUParser;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.sms.ByteBuffer;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * TODO: Document me !
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class CmppRequestDecoder extends CumulativeProtocolDecoder {
    private static final Logger logger = LoggerFactory
            .getLogger(CmppRequestDecoder.class);

    @Override
    protected boolean doDecode(final IoSession session, IoBuffer in,
                               ProtocolDecoderOutput out) {

        if (in.remaining() > 4) {
            logger.info("received msg " + in.toString());
            int length = in.getInt();
            logger.info("length=" + length + ",in.limit=" + in.limit() + ",in.remaining=" + in.remaining());
            if (length > (in.remaining() + 4)) {
                in.rewind();
                return false;
            }
            byte[] byteData = new byte[length - 4];
            in.get(byteData);
            ByteBuffer buffer = new ByteBuffer();
            buffer.appendInt(length);
            buffer.appendBytes(byteData);
            CmppPDU pdu = CmppPDUParser.createPDUFromBuffer(buffer);
            if (pdu == null) return false;
           // logger.info("com.hy.lang.mercury.client.cmpp.mina.cmpp.client.CmppRequestDecoder" + pdu.dump());
            out.write(pdu);
            return true;
        }
        return false;
    }
}
