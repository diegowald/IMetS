package com.mksingenieria.imets.task.impl;

import android.content.Context;
import android.telephony.SmsMessage;

import com.mksingenieria.imets.exceptions.BaseException;
import com.mksingenieria.imets.model.Device;
import com.mksingenieria.imets.task.SMSSender;
import com.mksingenieria.imets.task.Task;

public class M0 extends Task {
	
    public M0(Device device, Context context) {
        super("M*0", device, context);
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
        return SMSSender.sendSMS(device.phone(), "M*0");
    }

    /** El formato es “M*0,MODO: POR DEMANDA"
    * spllited[0] = M*0
    * splitted[1] = MODO: POR DEMANDA
     * @throws BaseException 
    */
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
