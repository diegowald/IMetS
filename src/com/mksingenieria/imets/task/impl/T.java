package com.mksingenieria.imets.task.impl;

import android.content.Context;
import android.telephony.SmsMessage;

import com.mksingenieria.imets.model.Device;
import com.mksingenieria.imets.task.SMSSender;
import com.mksingenieria.imets.task.Task;

public class T extends Task {
	
    public T(Device device, Context context) {
        super("T*", device, context);
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
        return SMSSender.sendSMS(device.phone(), "T*");
    }

    @Override
    protected String processReceptionMessage(String phoneNumner, 
    		SmsMessage smsMessage) {
        return "OK";
    }
}
