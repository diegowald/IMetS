package com.mksingenieria.imets.model;

/**
 * Created by diego.wald on 07/11/13.
 */
public class Device {
    private String phone_number;
    private String device_name;
    private String device_model;
    private int idRecord;

    public Device(int idRecord, String phone, String name, String model) {
        this.idRecord = idRecord;
        this.phone_number = phone;
        this.device_name = name;
        this.device_model = model;
    }

    public int id() {
        return idRecord;
    }

    public String phone() {
        return phone_number;
    }
    public void setPhone(String newNumber) {
        phone_number = newNumber;
    }

    public String name() {
        return device_name;
    }

    public void setName(String newName) {
        device_name = newName;
    }

    public String model() {
        return device_model;
    }

    public void setModel(String newModel) {
        device_model = newModel;
    }
}
