package com.mksingenieria.checkmysensors.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mksingenieria.checkmysensors.R;
import com.mksingenieria.checkmysensors.controller.HistoryController;
import com.mksingenieria.checkmysensors.helper.DatabaseHelper;
import com.mksingenieria.checkmysensors.model.CommandHistory;
import com.mksingenieria.checkmysensors.model.Device;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diego.wald on 12/11/13.
 */
public class HistoryAdapter extends BaseAdapter {

    List<HistoryController> history;
    Activity context;
    Device device;

    public HistoryAdapter(Device device,  Activity context) {
        super();
        this.context = context;
        this.device = device;
        history = getHistory();
    }

    @Override
    public int getCount() {
        return (history == null) ? 0 :  history.size();
    }

    @Override
    public Object getItem(int i) {
        return history.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        HistoryController hist = history.get(position);
        View view = convertView;
        if (view == null) {
            // It is necessary to create a new one, since there is no view to re-use.
            view = context.getLayoutInflater().inflate(R.layout.logview, null);
        }

        ((TextView)view.findViewById(R.id.Text_MessageSent)).setText(hist.getCommandSent());
        ((TextView)view.findViewById(R.id.Text_DateSent)).setText(hist.getDateSent());
        ((TextView)view.findViewById(R.id.Text_MessageReceived)).setText(hist.getMessageReceived());
        ((TextView)view.findViewById(R.id.Text_DateReception)).setText(hist.getReceptionDate());
        //view.FindViewById<ImageButton>(Resource.Id.Image).SetImageResource(item.ImageResourceId);
        return view;
    }

    private List<HistoryController> createFromCommandHistory(List<CommandHistory> commandHistoryList) {
        List<HistoryController> list = new ArrayList<HistoryController>();

        for (CommandHistory commandHistory: commandHistoryList) {
            list.add(new HistoryController(commandHistory));
        }

        return  list;
    }

    private List<HistoryController> getHistory() {
        if (device == null)
            return null;

        DatabaseHelper databaseHelper = new DatabaseHelper(context);

        List<CommandHistory> commandHistoryList = databaseHelper.getHistoryForPhone(device.phone());

        databaseHelper.close();
        return createFromCommandHistory(commandHistoryList);
    }

}
