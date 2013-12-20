package com.mksingenieria.imets.task.impl;

import android.content.Context;
import android.telephony.SmsMessage;

import com.mksingenieria.imets.exceptions.BaseException;
import com.mksingenieria.imets.model.Device;
import com.mksingenieria.imets.task.SMSSender;
import com.mksingenieria.imets.task.Task;

public class M1 extends Task {
    
	public M1(Device device, Context context) {
        super("M*1", device, context);
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
        return SMSSender.sendSMS(device.phone(), "M*1");
    }

    @Override
    protected String processReceptionMessage(String phoneNumner, 
    		SmsMessage smsMessage) throws BaseException {
    	String[] splitted = smsMessage.getMessageBody().split(",");
    	if (!splitted[1].startsWith("MODO: ")) {
    		throw new BaseException("Missing: MODO");
    	}
    	return splitted[1].split(":")[1];
    }
}
