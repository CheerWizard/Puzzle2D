package com.jeremy.services;

import com.jeremy.controllers.IController;
import com.jeremy.utils.helpers.PuzzleHelper;

public class Service implements IService{

    protected IController controller;

    protected Service(IController controller) {
        this.controller = controller;
    }

    protected Service() {
    }

    public IController getController() {
        return controller;
    }

    public void setController(IController controller) {
        this.controller = controller;
    }

    @Override
    public void close() {
        PuzzleHelper.close();
    }
}
