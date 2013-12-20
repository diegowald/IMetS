package com.mksingenieria.imets.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mksingenieria.imets.R;
import com.mksingenieria.imets.controller.DeviceController;
import com.mksingenieria.imets.helper.DatabaseHelper;
import com.mksingenieria.imets.model.Device;

/**
 * Created by diego.wald on 07/11/13.
 */

public class DeviceAdapter extends BaseAdapter {

    List<DeviceController> devices;
    Activity context;

    public DeviceAdapter(Activity context) {
        super();
        this.context = context;
        devices = getDevices();
    }

    @Override
    public int getCount() {
        return devices.size();
    }

    @Override
    public Object getItem(int i) {
        return devices.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        DeviceController device = devices.get(position);
        View view = convertView;
        if (view == null) {
            // It is necessary to create a new one, since there is no view to re-use.
            view = context.getLayoutInflater().inflate(R.layout.deviceview, null);
        }
        ((TextView)view.findViewById(R.id.Text1)).setText(device.getMainText());
        ((TextView)view.findViewById(R.id.Text2)).setText(device.getSecondaryText(view.getContext()));
        //view.FindViewById<ImageButton>(Resource.Id.Image).SetImageResource(item.ImageResourceId);
        view.findViewById(R.id.ImgButtonGotoDetails).setOnClickListener(device.listener);
        view.setOnLongClickListener(device.longClickListener);
        return view;
    }


    private List<DeviceController> createFromDevices(List<Device> devices) {
        List<DeviceController> list = new ArrayList<DeviceController>();

        for (Device device: devices) {
            list.add(new DeviceController(device));
        }

        return list;
    }

    private List<DeviceController> getDevices() {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        List<DeviceController> result = createFromDevices(databaseHelper.getAllDevices());
        databaseHelper.close();
        return result;
    }

}