package com.mksingenieria.checkmysensors.task;

import android.app.Activity;
import android.content.Context;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.mksingenieria.checkmysensors.helper.DatabaseHelper;
import com.mksingenieria.checkmysensors.model.CommandHistory;
import com.mksingenieria.checkmysensors.model.Device;

import java.util.Calendar;

/**
 * Created by diego.wald on 12/11/13.
 */
public abstract class Task {
    protected String name;
    protected Context context;
    protected Device device;

    protected Task(String name, Device device, Context context) {
        this.name = name;
        this.device = device;
        this.context = context;
    }



    protected abstract String getSuccessMessage();
    protected abstract String getErrorMessage();
    protected abstract boolean doJob();

    public void runSend() {
        boolean result = doJob();
        String message = result ? getSuccessMessage() : getErrorMessage();
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    protected abstract String processReceptionMessage(String phoneNumner, SmsMessage smsMessage);

    public void runReception(String phoneNumber, SmsMessage smsMessage) {
        String processReceptionMessage = processReceptionMessage(phoneNumber, smsMessage);
        DatabaseHelper db = new DatabaseHelper(context);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(smsMessage.getTimestampMillis());

        db.createCommandHistory(new CommandHistory(phoneNumber, smsMessage.getMessageBody(), "",
                "", calendar.getTime().toString()));

        db.close();
    }
}
