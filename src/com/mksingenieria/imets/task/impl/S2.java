package com.mksingenieria.imets.task.impl;

import android.content.Context;
import android.telephony.SmsMessage;

import com.mksingenieria.imets.model.Device;
import com.mksingenieria.imets.task.SMSSender;
import com.mksingenieria.imets.task.Task;

public class S2 extends Task {
	
	public S2(Device device, Context context) {
		super("S*2", device, context);
	}

	@Override
	protected String getSuccessMessage() {
		return "Message sent";
	}

	@Override
	protected String getErrorMessage() {
		return "Error";
	}
	
	@Override
	protected boolean doJob() {
		return SMSSender.sendSMS(device.phone(), "S*2");
	}

	/**
	 * “S*2 ,XXXXXXXXXX”
	 */
	@Override
	protected String processReceptionMessage(String phoneNumner, 
			SmsMessage smsMessage) {
		String [] splitted = smsMessage.getMessageBody().split(",");
    	return splitted[1];
	}
}
