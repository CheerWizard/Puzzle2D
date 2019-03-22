package com.jeremy.models;

import com.jeremy.constants.AnimationType;
import com.jeremy.constants.StringResources;
import com.jeremy.utils.loggers.PuzzleLogger;
import com.vaadin.ui.Component;

public class Animation {

    private String class_name = getClass().getName();
    private AnimationType animationType;
    private Component[] components;

    private Animation() {
        //lock default constructor
    }

    public Animation(AnimationType animationType, Component[] components) {
        PuzzleLogger.info(class_name , StringResources.initializing);
        this.animationType = animationType;
        this.components = components;
    }

    public Animation(AnimationType animationType) {
        this.animationType = animationType;
        components = new Component[]{};
    }

    public AnimationType getAnimationType() {
        return animationType;
    }

    public void setAnimationType(AnimationType animationType) {
        this.animationType = animationType;
    }

    public Component[] getComponents() {
        return components;
    }

    public void setComponents(Component[] components) {
        this.components = components;
    }
}
