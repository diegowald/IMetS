package com.mksingenieria.imets.controller;

import com.mksingenieria.imets.model.Model;

/**
 * Created by diego.wald on 12/11/13.
 */
public class ModelController {
    Model model;

    public ModelController(Model m) {
        this.model = m;
    }

    public String getTitleText() {
        return model.name();
    }

    public String getDescription() {
        return model.description();
    }
}