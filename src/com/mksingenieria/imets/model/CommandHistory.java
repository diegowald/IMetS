package com.mksingenieria.imets.model;

/**
 * Created by diego.wald on 07/11/13.
 */
public class CommandHistory {
    private String phone_number;
    private String commandReceivedFromDevice;
    private String commandSentToDevice;
    private String dateSentToDevice;
    private String dateReceivedToDevice;
	private String process_Result;

    public CommandHistory(String phone, String commandReceived, String commandSent,
                          String dateSentToDevice, String dateReceivedToDevice, 
                          String processResult) {
        this.phone_number = phone;
        this.commandReceivedFromDevice = commandReceived;
        this.commandSentToDevice = commandSent;
        this.dateSentToDevice = dateSentToDevice;
        this.dateReceivedToDevice = dateReceivedToDevice;
        this.process_Result = processResult;
    }

    public String phone() {
        return phone_number;
    }

    public void setPhone(String phone) {
        phone_number = phone;
    }

    public String commamdReceived() {
        return commandReceivedFromDevice;
    }

    public void setCommandReceived(String newValue) {
        this.commandReceivedFromDevice = newValue;
    }

    public String commandSent() {
        return commandSentToDevice;
    }

    public void setCommandSent(String newValue) {
        this.commandSentToDevice = newValue;
    }

    public String dateSent() {
        return dateSentToDevice;
    }

    public void setDateSent(String newDate) {
        dateSentToDevice = newDate;
    }

    public String dateReceived() {
        return dateReceivedToDevice;
    }

    public void setDateReceived(String newDate) {
        dateReceivedToDevice = newDate;
    }
    
    public String processResult() {
    	return process_Result;
    }
    
    public void setProcessresult(String value) {
    	process_Result = value;
    }

}
