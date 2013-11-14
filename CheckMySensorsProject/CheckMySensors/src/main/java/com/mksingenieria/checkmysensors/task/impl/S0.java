package com.mksingenieria.checkmysensors.task.impl;

import android.app.Activity;
import android.content.Context;
import android.telephony.SmsMessage;

import com.mksingenieria.checkmysensors.model.Device;
import com.mksingenieria.checkmysensors.task.SMSSender;
import com.mksingenieria.checkmysensors.task.Task;

/**
 * Created by diego.wald on 12/11/13.
 */
public class S0 extends Task {

    public S0(Device device, Context context) {
        super("S*0", device, context);
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
        return SMSSender.sendSMS(device.phone(), "S*0");
    }

    @Override
    protected String processReceptionMessage(String phoneNumner, SmsMessage smsMessage) {
        return "OK";
    }

}
