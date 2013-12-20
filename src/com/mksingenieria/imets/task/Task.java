package com.mksingenieria.imets.task;

import java.util.Calendar;

import android.content.Context;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.mksingenieria.imets.exceptions.BaseException;
import com.mksingenieria.imets.helper.DatabaseHelper;
import com.mksingenieria.imets.model.CommandHistory;
import com.mksingenieria.imets.model.Device;

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

    protected abstract String processReceptionMessage(String phoneNumner, SmsMessage smsMessage) throws BaseException;

    public boolean runReception(String phoneNumber, SmsMessage smsMessage) {
        String processReceptionMessage;
        boolean result = false;
		try {
			processReceptionMessage = processReceptionMessage(phoneNumber, smsMessage);
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			processReceptionMessage = "Bad Message";
		}
        
		DatabaseHelper db = new DatabaseHelper(context);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(smsMessage.getTimestampMillis());

        db.createCommandHistory(new CommandHistory(phoneNumber, 
        		smsMessage.getMessageBody(), 
        		"",
                "", 
                calendar.getTime().toString(), 
                processReceptionMessage));
        result = !processReceptionMessage.equals("Bad Message");
        db.close();
        
        return result;
    }

}
