package com.jeremy.controllers;

import com.jeremy.ui.IView;

public class Controller<T> implements IController {

    protected IView view;
    protected T service;

    private Controller(IView view, T service) {
        this.view = view;
        this.service = service;
    }

    protected Controller(IView view) {
        this.view = view;
    }

    protected Controller() {
    }

    public IView getView() {
        return view;
    }

    public void setView(IView view) {
        this.view = view;
    }

    public T getService() {
        return service;
    }

    public void setService(T service) {
        this.service = service;
    }

    @Override
    public void onClose() {
    }
}
