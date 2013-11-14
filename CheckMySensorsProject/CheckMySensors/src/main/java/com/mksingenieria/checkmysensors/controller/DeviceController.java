package com.mksingenieria.checkmysensors.controller;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.mksingenieria.checkmysensors.DetailsActivity;
import com.mksingenieria.checkmysensors.MainActivity;
import com.mksingenieria.checkmysensors.model.Device;
import com.mksingenieria.checkmysensors.task.Task;
import com.mksingenieria.checkmysensors.task.TaskFactory;
import com.mksingenieria.checkmysensors.task.impl.*;


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

    public String getSecondaryText() {
        return device.phone();
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
