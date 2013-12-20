package com.mksingenieria.imets.task;

import android.telephony.SmsManager;

/**
 * Created by diego.wald on 12/11/13.
 */


public class SMSSender {
    public static boolean sendSMS(String phoneDestination, String message) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneDestination, null, message, null, null);
        return true;
    }
}
