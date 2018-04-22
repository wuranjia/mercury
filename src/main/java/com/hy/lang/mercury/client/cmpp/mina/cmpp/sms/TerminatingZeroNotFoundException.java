package com.hy.lang.mercury.client.cmpp.mina.cmpp.sms;

public class TerminatingZeroNotFoundException extends SmsException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8864252190823538499L;

	public TerminatingZeroNotFoundException() {
		super("Terminating zero not found in buffer.");
	}

	public TerminatingZeroNotFoundException(String s) {
		super(s);
	}

}

