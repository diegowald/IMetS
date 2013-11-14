package com.mksingenieria.checkmysensors.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mksingenieria.checkmysensors.model.CommandHistory;
import com.mksingenieria.checkmysensors.model.CommandModel;
import com.mksingenieria.checkmysensors.model.Device;
import com.mksingenieria.checkmysensors.model.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diego.wald on 07/11/13.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG_CAT = "DatabasaeHelper";

    // Database version
    private static final int DATABASE_VERSION = 1;

    // Database name
    private static final String DATABASE_NAME = "DevicesLogger";

    // Table Names
    private static final String TABLE_DEVICE = "devices";
    private static final String TABLE_MODEL = "models";
    private static final String TABLE_COMMAND_HISTORY = "history";
    private static final String TABLE_COMMAND_MODEL = "commands";

    // Common column name;
    private static final String FIELD_ID = "ID";

    // Devices table, column names
    private static final String FIELD_PHONE_NUMBER = "phone";
    private static final String FIELD_DEVICE_NAME = "name";
    private static final String FIELD_DEVICE_MODEL = "model";

    // Models table, column names
    private static final String FIELD_MODEL_NAME = "model";
    private static final String FIELD_MODEL_DESCRIPTION = "description";
    private static final String FIELD_DEFAULT_COMMAND = "defaultCommand";

    // CommandHistory table, column names
    private static final String FIELD_COMMAND_RECEIVED = "cmdReceived";
    private static final String FIELD_COMMAND_SENT = "cmdSent";
    private static final String FIELD_DATE_SENT = "dateSent";
    private static final String FIELD_DATE_RECEPTION = "dateReception";

    // Commands table, column names
    private static final String FIELD_COMMAND_NAME = "command";
    private static final String FIELD_COMMAND_DESCRIPTION = "description";
    private static final String FIELD_CLASS_COMMAND = "classToExecute";

    // Table creation statements

    // Table devices
    private static final String CREATE_TABLE_DEVICE =
            "CREATE TABLE " + TABLE_DEVICE + "(" +
                    FIELD_ID + " INTEGER PRIMARY KEY, " +
                    FIELD_PHONE_NUMBER + " TEXT, " +
                    FIELD_DEVICE_NAME + " TEXT, " +
                    FIELD_DEVICE_MODEL + " TEXT)";

    // Table models
    private static final String CREATE_TABLE_MODELS =
            "CREATE TABLE " + TABLE_MODEL + "(" +
                    FIELD_ID + " INTEGER PRIMARY KEY, " +
                    FIELD_MODEL_NAME + " TEXT, " +
                    FIELD_MODEL_DESCRIPTION + " TEXT, " +
                    FIELD_DEFAULT_COMMAND + " TEXT)";

    // Table History
    private static final String CREATE_TABLE_HISTORY =
            "CREATE TABLE " + TABLE_COMMAND_HISTORY + " (" +
                    FIELD_ID + " INTEGER PRIMARY KEY, " +
                    FIELD_PHONE_NUMBER + " TEXT, " +
                    FIELD_COMMAND_RECEIVED + " TEXT, " +
                    FIELD_COMMAND_SENT + " TEXT, " +
                    FIELD_DATE_SENT + " DATETIME, " +
                    FIELD_DATE_RECEPTION + " DATETIME)";

    // Table Commands
    private static final String CREATE_TABLE_COMMANDS =
            "CREATE TABLE " + TABLE_COMMAND_MODEL + " (" +
                    FIELD_ID + " INTEGER PRIMARY KEY, " +
                    FIELD_MODEL_NAME + " TEXT, " +
                    FIELD_COMMAND_NAME + " TEXT, " +
                    FIELD_COMMAND_DESCRIPTION  + " TEXT, " +
                    FIELD_CLASS_COMMAND + " TEXT)";

    // Data
    private static final String model1 = "INSERT INTO " + TABLE_MODEL +
            "(" + FIELD_MODEL_NAME + ", " + FIELD_MODEL_DESCRIPTION +
            ", " + FIELD_DEFAULT_COMMAND + ") VALUES ("
            + "'m1', 'model 1', 'Sends S*0')";

    private static final String commandModel1 = "INSERT INTO " + TABLE_COMMAND_MODEL +
            "(" + FIELD_MODEL_NAME + ", " +
            FIELD_COMMAND_NAME + ", " +
            FIELD_COMMAND_DESCRIPTION  + ", " +
            FIELD_CLASS_COMMAND + " ) VALUES (" +
            "'m1', 'Sends S*0', 'Sends S*0 to the sensor', 'S*0')";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_MODELS);
        sqLiteDatabase.execSQL(CREATE_TABLE_DEVICE);
        sqLiteDatabase.execSQL(CREATE_TABLE_COMMANDS);
        sqLiteDatabase.execSQL(CREATE_TABLE_HISTORY);

        sqLiteDatabase.execSQL(model1);
        sqLiteDatabase.execSQL(commandModel1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }

    public long createDevice(Device device) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_PHONE_NUMBER, device.phone());
        values.put(FIELD_DEVICE_NAME, device.name());
        values.put(FIELD_DEVICE_MODEL, device.model());

        return db.insert(TABLE_DEVICE, null, values);
    }

    public int updateDevice(Device device) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_PHONE_NUMBER, device.phone());
        values.put(FIELD_DEVICE_NAME, device.name());
        values.put(FIELD_DEVICE_MODEL, device.model());

        String filter = FIELD_ID + " = " + device.id();

        return db.update(TABLE_DEVICE, values, filter, null);
    }

    public Device getDevice(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_DEVICE
                + " WHERE " + FIELD_ID + " = " + id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
            Device d = new Device(c.getInt(c.getColumnIndex(FIELD_ID)),
                    c.getString(c.getColumnIndex(FIELD_PHONE_NUMBER)),
                    c.getString(c.getColumnIndex(FIELD_DEVICE_NAME)),
                    c.getString(c.getColumnIndex(FIELD_DEVICE_MODEL)));
            return d;
        }
        return null;
    }

    public Device getDevice(String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_DEVICE
                + " WHERE " + FIELD_PHONE_NUMBER + " = '" + phone + "'";

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
            Device d = new Device(c.getInt(c.getColumnIndex(FIELD_ID)),
                    c.getString(c.getColumnIndex(FIELD_PHONE_NUMBER)),
                    c.getString(c.getColumnIndex(FIELD_DEVICE_NAME)),
                    c.getString(c.getColumnIndex(FIELD_DEVICE_MODEL)));
            return d;
        }
        return null;
    }

    public List<Device> getAllDevices() {
        List<Device> devices = new ArrayList<Device>();

        String selectQuery = "SELECT * FROM " + TABLE_DEVICE;

        Log.e(LOG_CAT, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Device d = new Device(c.getInt(c.getColumnIndex(FIELD_ID)),
                        c.getString(c.getColumnIndex(FIELD_PHONE_NUMBER)),
                        c.getString(c.getColumnIndex(FIELD_DEVICE_NAME)),
                        c.getString(c.getColumnIndex(FIELD_DEVICE_MODEL)));
                devices.add(d);
            } while (c.moveToNext());
        }
        return devices;
    }

    public long createModel(Model m) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_MODEL_NAME, m.name());
        values.put(FIELD_MODEL_DESCRIPTION, m.description());
        values.put(FIELD_DEFAULT_COMMAND, m.getDefaultCommand());

        return db.insert(TABLE_MODEL, null, values);
    }

    public Model getModel(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_MODEL
                + " WHERE " + FIELD_ID + " = " + id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
            Model m = new Model(c.getString(c.getColumnIndex(FIELD_MODEL_NAME)),
                    c.getString(c.getColumnIndex(FIELD_MODEL_DESCRIPTION)),
                    c.getString(c.getColumnIndex(FIELD_DEFAULT_COMMAND)));
            return m;
        }
        return null;
    }

    public Model getModel(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_MODEL
                + " WHERE " + FIELD_MODEL_NAME + " = '" + name + "'";

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
            Model m = new Model(c.getString(c.getColumnIndex(FIELD_MODEL_NAME)),
                    c.getString(c.getColumnIndex(FIELD_MODEL_DESCRIPTION)),
                    c.getString(c.getColumnIndex(FIELD_DEFAULT_COMMAND)));
            return m;
        }
        return null;
    }

    public List<Model> getAllModels() {
        List<Model> models = new ArrayList<Model>();

        String selectQuery = "SELECT * FROM " + TABLE_MODEL;

        Log.e(LOG_CAT, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Model m = new Model(c.getString(c.getColumnIndex(FIELD_MODEL_NAME)),
                        c.getString(c.getColumnIndex(FIELD_MODEL_DESCRIPTION)),
                        c.getString(c.getColumnIndex(FIELD_DEFAULT_COMMAND)));
                models.add(m);
            } while (c.moveToNext());
        }
        return models;
    }

    public long createCommandHistory(CommandHistory ch) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_PHONE_NUMBER, ch.phone());
        values.put(FIELD_COMMAND_RECEIVED, ch.commamdReceived());
        values.put(FIELD_COMMAND_SENT, ch.commandSent());
        values.put(FIELD_DATE_RECEPTION, ch.dateReceived());
        values.put(FIELD_DATE_SENT, ch.dateSent());

        return db.insert(TABLE_COMMAND_HISTORY, null, values);
    }

    public CommandHistory getCommandHistory(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_COMMAND_HISTORY
                + " WHERE " + FIELD_ID + " = " + id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
            CommandHistory ch = new CommandHistory(c.getString(c.getColumnIndex(FIELD_PHONE_NUMBER)),
                    c.getString(c.getColumnIndex(FIELD_COMMAND_RECEIVED)),
                    c.getString(c.getColumnIndex(FIELD_COMMAND_SENT)),
                    c.getString(c.getColumnIndex(FIELD_DATE_SENT)),
                    c.getString(c.getColumnIndex(FIELD_COMMAND_RECEIVED)));
            return ch;
        }
        return null;
    }

    public List<CommandHistory> getHistoryForPhone(String phoneNumber) {
        List<CommandHistory> history = new ArrayList<CommandHistory>();

        String selectQuery = "SELECT * FROM " + TABLE_COMMAND_HISTORY +
                " WHERE " + FIELD_PHONE_NUMBER + " = '" + phoneNumber + "'";

        Log.e(LOG_CAT, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                CommandHistory ch = new CommandHistory(c.getString(c.getColumnIndex(FIELD_PHONE_NUMBER)),
                        c.getString(c.getColumnIndex(FIELD_COMMAND_RECEIVED)),
                        c.getString(c.getColumnIndex(FIELD_COMMAND_SENT)),
                        c.getString(c.getColumnIndex(FIELD_DATE_SENT)),
                        c.getString(c.getColumnIndex(FIELD_COMMAND_RECEIVED)));
                history.add(ch);
            } while (c.moveToNext());
        }
        return history;
    }


    public long createComandModel(CommandModel cm) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_MODEL_NAME, cm.name());
        values.put(FIELD_COMMAND_NAME, cm.command());
        values.put(FIELD_COMMAND_DESCRIPTION, cm.description());
        values.put(FIELD_CLASS_COMMAND, cm.commandClass());

        return db.insert(TABLE_COMMAND_MODEL, null, values);
    }


    public CommandModel getCommandModel(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_COMMAND_MODEL
                + " WHERE " + FIELD_ID + " = " + id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
            CommandModel cm = new CommandModel(
                    c.getString(c.getColumnIndex(FIELD_MODEL_NAME)),
                    c.getString(c.getColumnIndex(FIELD_COMMAND_NAME)),
                    c.getString(c.getColumnIndex(FIELD_COMMAND_DESCRIPTION)),
                    c.getString(c.getColumnIndex(FIELD_CLASS_COMMAND)));

            return cm;
        }
        return null;
    }

    public CommandModel getCommandModel(String name) {
        SQLiteDatabase database = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_COMMAND_MODEL
                + " WHERE " + FIELD_COMMAND_NAME + " = '" + name + "'";

        Cursor c = database.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
            CommandModel cm = new CommandModel(
                    c.getString(c.getColumnIndex(FIELD_MODEL_NAME)),
                    c.getString(c.getColumnIndex(FIELD_COMMAND_NAME)),
                    c.getString(c.getColumnIndex(FIELD_COMMAND_DESCRIPTION)),
                    c.getString(c.getColumnIndex(FIELD_CLASS_COMMAND)));

            return cm;
        }
        return null;
    }

    public List<CommandModel> getCommandsForModel(String model) {
        List<CommandModel> models= new ArrayList<CommandModel>();

        String selectQuery = "SELECT * FROM " + TABLE_COMMAND_MODEL +
                " WHERE " + FIELD_MODEL_NAME + " = '" + model + "'";

        Log.e(LOG_CAT, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                CommandModel cm = new CommandModel(
                        c.getString(c.getColumnIndex(FIELD_MODEL_NAME)),
                        c.getString(c.getColumnIndex(FIELD_COMMAND_NAME)),
                        c.getString(c.getColumnIndex(FIELD_COMMAND_DESCRIPTION)),
                        c.getString(c.getColumnIndex(FIELD_CLASS_COMMAND)));
                models.add(cm);
            } while (c.moveToNext());
        }
        return models;
    }


}
