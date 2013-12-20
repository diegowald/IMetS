package com.mksingenieria.imets.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mksingenieria.imets.R;
import com.mksingenieria.imets.controller.HistoryController;
import com.mksingenieria.imets.helper.DatabaseHelper;
import com.mksingenieria.imets.model.CommandHistory;
import com.mksingenieria.imets.model.Device;


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

        if (hist.getCommandSent().length() != 0) {
        	((TextView)view.findViewById(R.id.Text_MessageSent)).setText("Sent: " + hist.getCommandSent());
        	((TextView)view.findViewById(R.id.Text_DateSent)).setText("Date sent: " + hist.getDateSent());
        } else {
        	((TextView)view.findViewById(R.id.Text_MessageSent)).setVisibility(View.GONE);
        	((TextView)view.findViewById(R.id.Text_DateSent)).setVisibility(View.GONE);
        }
        if (hist.getMessageReceived().length() != 0) {
	        ((TextView)view.findViewById(R.id.Text_MessageReceived)).setText("Received: " + hist.getMessageReceived());
	        ((TextView)view.findViewById(R.id.Text_DateReception)).setText("Date: " + hist.getReceptionDate());
	        ((TextView)view.findViewById(R.id.Text_ProcessResult)).setText("Result: " + hist.getProcessResult());
        } else {
        	((TextView)view.findViewById(R.id.Text_MessageReceived)).setVisibility(View.GONE);
        	((TextView)view.findViewById(R.id.Text_DateReception)).setVisibility(View.GONE);
        	((TextView)view.findViewById(R.id.Text_ProcessResult)).setVisibility(View.GONE);
        }

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
