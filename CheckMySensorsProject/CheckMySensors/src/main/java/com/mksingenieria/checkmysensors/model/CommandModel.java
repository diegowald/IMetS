package com.mksingenieria.checkmysensors.model;

/**
 * Created by diego.wald on 07/11/13.
 */
public class CommandModel {
    private String modelName;
    private String commandName;
    private String commandDescription;
    private String classThatPerformsCommand;

    public CommandModel(String modelName, String commandName, String commandDescription, String classThatPerformsCommand) {
        this.modelName = modelName;
        this.commandName = commandName;
        this.commandDescription = commandDescription;
        this.classThatPerformsCommand = classThatPerformsCommand;
    }

    public String name() {
        return  modelName;
    }

    public void setName(String newName) {
        modelName = newName;
    }

    public String command() {
        return commandName;
    }

    public void setCommand(String newCommand) {
        commandName = newCommand;
    }

    public String description() {
        return commandDescription;
    }

    public void setDescription(String newDescription) {
        commandDescription = newDescription;
    }

    public String commandClass() {
        return classThatPerformsCommand;
    }

    public void setCommandClass(String newClass) {
        classThatPerformsCommand = newClass;
    }
}
