package com.jeremy.models;

import com.jeremy.constants.StringResources;
import com.jeremy.utils.loggers.PuzzleLogger;

public class Message {

    private String class_name = getClass().getName();
    private String caption;
    private String description;
    private Runnable bodyRunnable;
    private boolean withOkButton;
    private boolean withCancelButton;
    private boolean withRetryButton;
    private boolean withCloseButton;
    private boolean withNoButton;
    private boolean withYesButton;

    private Message() {
        //lock default constructor
    }

    public Message(String caption, String description, boolean withOkButton, boolean withCancelButton, boolean withRetryButton , Runnable bodyRunnable) {
        PuzzleLogger.info(class_name , StringResources.initializing);
        this.caption = caption;
        this.description = description;
        this.withOkButton = withOkButton;
        this.withCancelButton = withCancelButton;
        this.withRetryButton = withRetryButton;
        this.bodyRunnable = bodyRunnable;
    }

    public Message(String caption, String description, boolean withNoButton, boolean withYesButton , Runnable bodyRunnable) {
        this.caption = caption;
        this.description = description;
        this.withNoButton = withNoButton;
        this.withYesButton = withYesButton;
        this.bodyRunnable = bodyRunnable;
    }

    public Message(String caption, String description, boolean withCloseButton , Runnable bodyRunnable) {
        this.caption = caption;
        this.description = description;
        this.withCloseButton = withCloseButton;
        this.bodyRunnable = bodyRunnable;
    }

    public Runnable getBodyRunnable() {
        return bodyRunnable;
    }

    public void setBodyRunnable(Runnable bodyRunnable) {
        this.bodyRunnable = bodyRunnable;
    }

    public boolean isWithOkButton() {
        return withOkButton;
    }

    public void setWithOkButton(boolean withOkButton) {
        this.withOkButton = withOkButton;
    }

    public boolean isWithCancelButton() {
        return withCancelButton;
    }

    public void setWithCancelButton(boolean withCancelButton) {
        this.withCancelButton = withCancelButton;
    }

    public boolean isWithRetryButton() {
        return withRetryButton;
    }

    public void setWithRetryButton(boolean withRetryButton) {
        this.withRetryButton = withRetryButton;
    }

    public boolean isWithCloseButton() {
        return withCloseButton;
    }

    public void setWithCloseButton(boolean withCloseButton) {
        this.withCloseButton = withCloseButton;
    }

    public boolean isWithNoButton() {
        return withNoButton;
    }

    public void setWithNoButton(boolean withNoButton) {
        this.withNoButton = withNoButton;
    }

    public boolean isWithYesButton() {
        return withYesButton;
    }

    public void setWithYesButton(boolean withYesButton) {
        this.withYesButton = withYesButton;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
