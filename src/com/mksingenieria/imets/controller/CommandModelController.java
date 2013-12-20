package com.mksingenieria.imets.controller;

import android.app.Activity;
import android.view.View;

import com.mksingenieria.imets.model.CommandModel;
import com.mksingenieria.imets.model.Device;
import com.mksingenieria.imets.task.Task;
import com.mksingenieria.imets.task.TaskFactory;

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
        return commandModel.command();
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