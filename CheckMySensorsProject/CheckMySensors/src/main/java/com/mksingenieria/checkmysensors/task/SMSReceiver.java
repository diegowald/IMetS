package com.mksingenieria.checkmysensors.task;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.widget.Toast;

import com.mksingenieria.checkmysensors.helper.DatabaseHelper;
import com.mksingenieria.checkmysensors.model.Device;

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
            processSMS(context, msg);
            if (TextUtils.equals(msg.getOriginatingAddress(), "1234")) {
                Toast.makeText(context, "Received message from the mothership: " + msg.getMessageBody(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void processSMS(Context context, SmsMessage smsMessage) {
        String originatingAddress = smsMessage.getOriginatingAddress();
        if (isInWhiteList(context, originatingAddress)) {
            executeCommand(context, originatingAddress, smsMessage);
        }
    }

    private boolean isInWhiteList(Context context, String phoneNumber) {
        DatabaseHelper db = new DatabaseHelper(context);
        Device device = db.getDevice(phoneNumber);
        db.close();
        return device != null;
    }

    private void executeCommand(Context context, String phoneNumber, SmsMessage smsMessage) {
        DatabaseHelper db = new DatabaseHelper(context);

        Device device = db.getDevice(phoneNumber);

        Task task = TaskFactory.createTask(context, device);

        task.runReception(phoneNumber, smsMessage);

        db.close();
    }
}
