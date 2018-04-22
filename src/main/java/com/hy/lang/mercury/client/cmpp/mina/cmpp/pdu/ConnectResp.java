package com.hy.lang.mercury.client.cmpp.mina.cmpp.pdu;


import com.alibaba.fastjson.JSON;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.CmppConstant;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.sms.ByteBuffer;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.sms.NotEnoughDataInByteBufferException;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.sms.PDUException;

public class ConnectResp extends Response {

    private int status = 0;
    private String authServer = "";
    private byte version = 0;

    public ConnectResp() {
        super(CmppConstant.CMD_CONNECT_RESP);
    }

    /**
     * @return Returns the authServer.
     */
    public String getAuthServer() {
        return authServer;
    }

    /**
     * @param authServer The authServer to set.
     */
    public void setAuthServer(String authServer) {
        this.authServer = authServer;
    }

    /**
     * @return Returns the status.
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status The status to set.
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return Returns the version.
     */
    public byte getVersion() {
        return version;
    }

    /**
     * @param version The version to set.
     */
    public void setVersion(byte version) {
        this.version = version;
    }


    public void setBody(ByteBuffer buffer) throws PDUException {
        try {
            setStatus(buffer.removeInt());
            setAuthServer(buffer.removeStringEx(16));
            setVersion(buffer.removeByte());
        } catch (NotEnoughDataInByteBufferException e) {
            throw new PDUException(e);
        }
    }

    public ByteBuffer getBody() {
        ByteBuffer buffer = new ByteBuffer();
        buffer.appendInt(getStatus());
        buffer.appendString(getAuthServer(), 16);
        buffer.appendByte(getVersion());
        return buffer;
    }

    /* (non-Javadoc)
     * @see cmpp.sms.ByteData#setData(cmpp.sms.util.ByteBuffer)
     */
    public void setData(ByteBuffer buffer) throws PDUException {
        header.setData(buffer);
        setBody(buffer);
    }

    /* (non-Javadoc)
     * @see cmpp.sms.ByteData#getData()
     */
    public ByteBuffer getData() {
        ByteBuffer bodyBuf = getBody();
        header.setCommandLength(CmppConstant.PDU_HEADER_SIZE + bodyBuf.length());
        ByteBuffer buffer = header.getData();
        buffer.appendBuffer(bodyBuf);
        return buffer;
    }

    public String name() {
        return "CMPP ConnectResp";
    }

    public String dump() {
        return JSON.toJSONString(this);
    }
}
