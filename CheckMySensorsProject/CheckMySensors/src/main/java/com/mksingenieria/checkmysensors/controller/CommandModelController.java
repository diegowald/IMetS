package com.mksingenieria.checkmysensors.controller;

import android.app.Activity;
import android.view.View;

import com.mksingenieria.checkmysensors.model.CommandModel;
import com.mksingenieria.checkmysensors.model.Device;
import com.mksingenieria.checkmysensors.task.Task;
import com.mksingenieria.checkmysensors.task.TaskFactory;

/**
 * Created by diego.wald on 12/11/13.
 */
public class CommandModelController {
    private CommandModel commandModel;
    Device device;

    public CommandModelController(Device device, CommandModel commandModel) {
        this.commandModel = commandModel;
        this.device = device;
    }

    public String getCommandText() {
        return commandModel.name();
    }

    public String getCommandDescription() {
        return commandModel.description();
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
}