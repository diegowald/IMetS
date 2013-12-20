package com.mksingenieria.imets.task.impl;

import android.content.Context;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;

import com.mksingenieria.imets.exceptions.BaseException;
import com.mksingenieria.imets.model.Device;
import com.mksingenieria.imets.task.SMSSender;
import com.mksingenieria.imets.task.Task;

public class N extends Task {
    public N(Device device, Context context) {
        super("N*", device, context);
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
    	
        return SMSSender.sendSMS(device.phone(), "N*" +
        getMyPhoneNumber());
    }

    @Override
    protected String processReceptionMessage(String phoneNumner, 
    		SmsMessage smsMessage) {
    	String[] splitted = smsMessage.getMessageBody().split(",");
    	return splitted[1];
    }
    
    private String getMyPhoneNumber(){
        TelephonyManager mTelephonyMgr;
        mTelephonyMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE); 
        return mTelephonyMgr.getLine1Number();
    }
}
