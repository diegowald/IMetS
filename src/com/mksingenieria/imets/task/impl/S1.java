package com.mksingenieria.imets.task.impl;

import android.content.Context;
import android.telephony.SmsMessage;

import com.mksingenieria.imets.model.Device;
import com.mksingenieria.imets.task.SMSSender;
import com.mksingenieria.imets.task.Task;

public class S1 extends Task {

	 public S1(Device device, Context context) {
	        super("S*1", device, context);
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
	        return SMSSender.sendSMS(device.phone(), "S*1");
	    }

	    @Override
	    protected String processReceptionMessage(String phoneNumner, 
	    		SmsMessage smsMessage) {
	        return "OK";
	    }

}
