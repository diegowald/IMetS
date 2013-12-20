package com.mksingenieria.imets.task;

import android.content.Context;
import android.telephony.SmsMessage;
import android.util.Log;

import com.mksingenieria.imets.helper.DatabaseHelper;
import com.mksingenieria.imets.model.CommandModel;
import com.mksingenieria.imets.model.Device;
import com.mksingenieria.imets.model.Model;
import com.mksingenieria.imets.task.impl.M0;
import com.mksingenieria.imets.task.impl.M1;
import com.mksingenieria.imets.task.impl.N;
import com.mksingenieria.imets.task.impl.S0;
import com.mksingenieria.imets.task.impl.S1;
import com.mksingenieria.imets.task.impl.S2;
import com.mksingenieria.imets.task.impl.S3;
import com.mksingenieria.imets.task.impl.S4;
import com.mksingenieria.imets.task.impl.T;

/**
 * Created by diego.wald on 12/11/13.
 */
public class TaskFactory {

    public static Task createTask(Context context, Device device)
    {
        CommandModel commandModel = getDefaultCommandFromDevice(context, device);
        if (commandModel == null) {
            return null;
        }
        return createTask(commandModel.commandClass(), context, device);
    }
    
    public static Task createTask(Context context, Device device, SmsMessage smsMessage) {
    	DatabaseHelper databaseHelper = new DatabaseHelper(context);
    	
    	CommandModel commandModel = databaseHelper.getCommandModelByClass(smsMessage.getMessageBody());
    	
    	return createTask(commandModel.commandClass(), context, device);
    }
    
    private static Task createTask(String task, Context context, Device device) {
    	Log.e("TASK", task);
    	if (task.equals("S*0")) {
    		return new S0(device, context);
    	}
    	if (task.equals("S*1")) {
    		return new S1(device, context);
    	}
    	if (task.equals("S*2")) {
    		return new S2(device, context);
    	}
    	if (task.equals("S*3")) {
    		return new S3(device, context);
    	}
    	if (task.equals("S*4")) {
    		return new S4(device, context);
    	}
    	if (task.equals("M*0")) {
    		return new M0(device, context);
    	}
    	if (task.equals("M*1")) {
    		return new M1(device, context);
    	}
    	if (task.startsWith("N*")) {
    		return new N(device, context);
    	}
    	if (task.startsWith("T*")) {
    		return new T(device, context);
    	}
    	return null;
    }
    

    private static CommandModel getDefaultCommandFromDevice(Context context, Device device) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);

        Model model = databaseHelper.getModel(device.model());
        CommandModel commandModel = databaseHelper.getCommandModelByName(model.getDefaultCommand());

        return commandModel;
    }
}
