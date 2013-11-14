package com.mksingenieria.checkmysensors.controller;

import com.mksingenieria.checkmysensors.model.CommandHistory;

/**
 * Created by diego.wald on 12/11/13.
 */
public class HistoryController {
    CommandHistory commandHistory;

    public HistoryController(CommandHistory commandHistory) {
        this.commandHistory  = commandHistory;
    }

    public String getCommandSent() {
        return commandHistory.commandSent();
    }

    public String getDateSent() {
        return commandHistory.dateSent();
    }

    public String getMessageReceived() {
        return commandHistory.commamdReceived();
    }

    public String getReceptionDate() {
        return commandHistory.dateReceived();
    }
}

