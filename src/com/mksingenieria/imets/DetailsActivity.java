package com.mksingenieria.imets;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.mksingenieria.imets.adapter.CommandModelAdapter;
import com.mksingenieria.imets.adapter.HistoryAdapter;
import com.mksingenieria.imets.exceptions.DuplicateRecordException;
import com.mksingenieria.imets.helper.DatabaseHelper;
import com.mksingenieria.imets.model.Device;
import com.mksingenieria.imets.model.Model;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends Activity {

    private boolean isNewRecord;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        isNewRecord = intent.getBooleanExtra("isNew", false);
        phone = intent.getStringExtra("phone");

        if (savedInstanceState == null) {
        	PlaceholderFragment fragment = new PlaceholderFragment();
        	fragment.setNewRecord(isNewRecord);
        	fragment.setPhone(phone);
        	
            getFragmentManager().beginTransaction()
                    .add(R.id.container, fragment, "Fragment")
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private boolean newRecord;
        private String phone;
        View rootView;
        
        public PlaceholderFragment() {
        	
        }
        
        public void setNewRecord(boolean value) {
        	this.newRecord = value;
        }
        
        public void setPhone(String phone) {
        	this.phone = phone;
        }
        
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_details, container, false);

            createTabs();

            List<String> models = getModels(rootView.getContext());

            Device device = getDevice(phone);
            ListView listView = (ListView) rootView.findViewById(R.id.listViewHistory);
            listView.setAdapter(new HistoryAdapter(device, getActivity()));

            listView = (ListView) rootView.findViewById(R.id.listViewCommands);
            listView.setAdapter(new CommandModelAdapter(device, getActivity()));

            fllSpinnerWithModels(models);

            rootView.findViewById(R.id.save_device).setOnClickListener(listener);

            return rootView;
        }

        Device getDevice(String phone) {
            if ((phone == null) || (phone.length() == 0))
                return null;

            DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
            Device device = databaseHelper.getDevice(phone);
            databaseHelper.close();
            return device;
        }

        private void fllSpinnerWithModels(List<String> models) {
            Spinner spinner = (Spinner) rootView.findViewById(R.id.device_model);

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(rootView.getContext(),
                    android.R.layout.simple_spinner_item, models);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);

            if (!newRecord) {
                DatabaseHelper databaseHelper = new DatabaseHelper(rootView.getContext());
                Device device = databaseHelper.getDevice(phone);
                assert device != null;

                ((TextView)rootView.findViewById(R.id.phone_number)).setText(device.phone());
                ((TextView)rootView.findViewById(R.id.device_description)).setText(device.name());
                spinner.setSelection(getItemId(spinner, device.model()));
            }
        }

        private void createTabs() {
            TabHost tabHost = (TabHost) rootView.findViewById(R.id.tabHost);
            tabHost.setup();

            TabHost.TabSpec spec1 = tabHost.newTabSpec("Tab 1");
            spec1.setContent(R.id.tabDetails);
            spec1.setIndicator("Details");

            TabHost.TabSpec spec2 = tabHost.newTabSpec("Tab 2");
            spec2.setContent(R.id.tabCommandLog);
            spec2.setIndicator("History");

            TabHost.TabSpec spec3 = tabHost.newTabSpec("Tab 3");
            spec3.setContent(R.id.tabCommands);
            spec3.setIndicator("Commands");

            tabHost.addTab(spec1);
            tabHost.addTab(spec2);
            tabHost.addTab(spec3);
        }

        private int getItemId(Spinner spinner, String model) {
            int index = 0;
            for (int i=0;i<spinner.getCount();i++){
                if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(model)){
                    index = i;
                    break;
                }
            }
            return index;
        }

        private List<String> getModels(Context context) {
            List<String> list = new ArrayList<String>();

            DatabaseHelper databaseHelper = new DatabaseHelper(context);

            List<Model> models = databaseHelper.getAllModels();

            for (Model model: models) {
                list.add(model.name());
            }

            return list;
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	DatabaseHelper databaseHelper = new DatabaseHelper(view.getContext());
            	try {
            		// Aca se hace el save de lo que se ve en la vista
            		String  newPhone = ((TextView) rootView.findViewById(R.id.phone_number)).getText().toString();
            		String newName = ((TextView)rootView.findViewById(R.id.device_description)).getText().toString();
            		Spinner spinner = (Spinner) rootView.findViewById(R.id.device_model);
            		String newModel = spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
            		if (newRecord) {
            			Device device = new Device(-1, newPhone, newName, newModel);
            			databaseHelper.createDevice(device);
            		} else {
            			Device device = databaseHelper.getDevice(phone);
            			device.setPhone(newPhone);
            			device.setName(newName);
            			device.setModel(newModel);
            			databaseHelper.updateDevice(device);
            		}
            		Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Saved", Toast.LENGTH_SHORT);
            		toast.show();
            	} catch (DuplicateRecordException ex) {
            		Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Invalid phone number", Toast.LENGTH_SHORT);
            		toast.show();
            	}
        		databaseHelper.close();
            }
        };
    }

}
