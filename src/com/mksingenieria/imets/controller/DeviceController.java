package com.mksingenieria.imets.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.mksingenieria.imets.DetailsActivity;
import com.mksingenieria.imets.helper.DatabaseHelper;
import com.mksingenieria.imets.model.CommandHistory;
import com.mksingenieria.imets.model.Device;
import com.mksingenieria.imets.task.Task;
import com.mksingenieria.imets.task.TaskFactory;


/**
 * Created by diego.wald on 08/11/13.
 */
public class DeviceController {
    Device device;

    public DeviceController(Device d) {
        device = d;
    }

    public String getMainText() {
        return device.name();
    }

    public String getSecondaryText(Context context) {
        return getLastCommandHistoryText(context);
    }
    
    private String getLastCommandHistoryText(Context context) {
		DatabaseHelper databaseHelper = new DatabaseHelper(context);
		CommandHistory commandHistory = databaseHelper.getLastCommandHistory(device);
		if (commandHistory != null) {
			return commandHistory.dateReceived() + ": " + commandHistory.commamdReceived() + 
					". " + commandHistory.processResult();
		}
		return "";
	}

    public String getPhone() {
        return device.phone();
    }

    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Task task = TaskFactory.createTask((Activity) view.getContext(), device);
            if (task != null) {
                task.runSend();
            }
        }
    };

    public View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            Intent intent = new Intent(view.getContext(), DetailsActivity.class);
            intent.putExtra("isNew", false);
            intent.putExtra("phone", device.phone());
            view.getContext().startActivity(intent);
            return true;
        }
    };
}
