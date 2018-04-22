/*
 * Created on 2005-5-7
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.hy.lang.mercury.client.cmpp.mina.cmpp.pdu;


import com.hy.lang.mercury.client.cmpp.mina.cmpp.CmppConstant;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.sms.ByteBuffer;
import com.hy.lang.mercury.client.cmpp.mina.cmpp.sms.PDUException;

/**
 * @author lucien
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Terminate extends Request {

	public Terminate() {
		super(CmppConstant.CMD_TERMINATE);
	}
	/* (non-Javadoc)
	 * @see cmpp.smgp.pdu.Request#createResponse()
	 */
	protected Response createResponse() {
		return new TerminateResp();
	}
	/* (non-Javadoc)
	 * @see cmpp.sms.ByteData#setData(cmpp.sms.util.ByteBuffer)
	 */
	public void setData(ByteBuffer buffer) throws PDUException {
		header.setData(buffer);
		
	}
	/* (non-Javadoc)
	 * @see cmpp.sms.ByteData#getData()
	 */
	public ByteBuffer getData() {
		return header.getData();
	}
	
	public String name() {
		return "CMPP Terminate";
	}
}
