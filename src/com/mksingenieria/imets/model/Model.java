package com.mksingenieria.imets.model;

/**
 * Created by diego.wald on 07/11/13.
 */
public class Model {
    private String modelName;
    private String modelDescription;
    private String defaultCommand;

    public Model(String modelName, String modelDescription, String defaultCommand) {
        this.modelName = modelName;
        this.modelDescription = modelDescription;
        this.defaultCommand = defaultCommand;
    }

    public String name() {
        return modelName;
    }

    public void setName(String newName) {
        modelName = newName;
    }

    public String description() {
        return modelDescription;
    }

    public void setDescription(String newDescription) {
        modelDescription = newDescription;
    }

    public String getDefaultCommand() {
        return defaultCommand;
    }

    public void setDefaultCommand(String defaultCommand) {
        this.defaultCommand = defaultCommand;
    }

}
