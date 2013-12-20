package com.mksingenieria.imets.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mksingenieria.imets.R;
import com.mksingenieria.imets.controller.CommandModelController;
import com.mksingenieria.imets.helper.DatabaseHelper;
import com.mksingenieria.imets.model.CommandModel;
import com.mksingenieria.imets.model.Device;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diego.wald on 14/11/13.
 */
public class CommandModelAdapter extends BaseAdapter {

    List<CommandModelController> commands;
    Device device;
    Activity context;


    public CommandModelAdapter(Device device, Activity context) {
        super();
        this.context = context;
        this.device = device;
        commands = getCommands();
    }

    @Override
    public int getCount() {
        return (commands == null) ? 0 :  commands.size();
    }

    @Override
    public Object getItem(int i) {
        return commands.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        CommandModelController command = commands.get(position);
        View view = convertView;
        if (view == null) {
            // It is necessary to create a new one, since there is no view to re-use.
            view = context.getLayoutInflater().inflate(R.layout.commandview, null);
        }

        ((TextView)view.findViewById(R.id.TextCommandName)).setText(command.getCommandText());
        ((TextView)view.findViewById(R.id.TextCommandDescription)).setText(command.getCommandDescription());
        view.findViewById(R.id.ButtonExecuteCommand).setOnClickListener(command.listener);
        return view;
    }


    private List<CommandModelController> createFromCommandsList(List<CommandModel> commands) {
        List<CommandModelController> list = new ArrayList<CommandModelController>();
        for (CommandModel cmd: commands) {
            list.add(new CommandModelController(device, cmd));
        }
        return list;
    }

    private List<CommandModelController> getCommands() {
        if (device == null)
            return null;

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        String model = device.model();
        List<CommandModelController> result =
                createFromCommandsList(databaseHelper.getCommandsForModel(model));
        databaseHelper.close();
        return result;
    }

}
