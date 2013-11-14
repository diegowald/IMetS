package com.mksingenieria.checkmysensors.task;

import android.app.Activity;
import android.content.Context;

import com.mksingenieria.checkmysensors.helper.DatabaseHelper;
import com.mksingenieria.checkmysensors.model.CommandModel;
import com.mksingenieria.checkmysensors.model.Device;
import com.mksingenieria.checkmysensors.model.Model;
import com.mksingenieria.checkmysensors.task.impl.S0;

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
        if (commandModel.commandClass().equals("S*0")) {
            return new S0(device, context);
        }

        return null;
    }

    private static CommandModel getDefaultCommandFromDevice(Context context, Device device) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);

        Model model = databaseHelper.getModel(device.model());
        CommandModel commandModel = databaseHelper.getCommandModel(model.getDefaultCommand());

        return commandModel;
    }
}
