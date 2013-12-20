package com.mksingenieria.imets.task;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.mksingenieria.imets.helper.DatabaseHelper;
import com.mksingenieria.imets.model.Device;

/**
 * Created by diego.wald on 12/11/13.
 */
public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Object[] messages = (Object[]) bundle.get("pdus");
        SmsMessage[] sms = new SmsMessage[messages.length];
        // Create messages for each incoming PDU
        for (int n = 0; n < messages.length; n++) {
            sms[n] = SmsMessage.createFromPdu((byte[]) messages[n]);
        }
        for (SmsMessage msg : sms) {
            // Verify if the message came from our known sender
            if (processSMS(context, msg)) {
                Toast.makeText(context, "Received message from the mothership: " + msg.getMessageBody(),
                        Toast.LENGTH_SHORT).show();
                abortBroadcast();
            }
        }
    }

    private boolean processSMS(Context context, SmsMessage smsMessage) {
        String originatingAddress = smsMessage.getOriginatingAddress();
        if (isInWhiteList(context, originatingAddress)) {
            return executeCommand(context, originatingAddress, smsMessage);
        }
        return false;
    }

    private boolean isInWhiteList(Context context, String phoneNumber) {
        DatabaseHelper db = new DatabaseHelper(context);
        Device device = db.getDevice(phoneNumber);
        db.close();
        return device != null;
    }

    private boolean executeCommand(Context context, String phoneNumber, SmsMessage smsMessage) {
        DatabaseHelper db = new DatabaseHelper(context);

        Device device = db.getDevice(phoneNumber);
        db.close();
        
        if (!isErrorMessage(smsMessage)) {
        	// El comando va a venir al principio, y puede estar separado por , o por espacios
        	// por lo que no se va a hacer el split de ahora en mas       
        	
        	Task task = TaskFactory.createTask(context, device, smsMessage);
        	if (task != null) {
        		return task.runReception(phoneNumber, smsMessage);
        	}
        }
        return false;
    }
    
    private static final String ERR_MSG = "ERROR DE PROGRAMACION";

    private boolean isErrorMessage(SmsMessage smsMessage) {
    	return smsMessage.getMessageBody().trim().equals(ERR_MSG);
	}
}
