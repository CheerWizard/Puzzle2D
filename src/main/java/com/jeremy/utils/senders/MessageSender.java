package com.jeremy.utils.senders;

import com.jeremy.constants.MessageType;
import com.jeremy.constants.StringResources;
import com.jeremy.models.Message;
import com.jeremy.utils.loggers.PuzzleLogger;
import de.steinwedel.messagebox.MessageBox;

public final class MessageSender {
    //class name
    private static String class_name = "Message Sender";

    private static MessageBox messageBox;

    public static MessageBox createMessage(MessageType messageType , Message message) {
        PuzzleLogger.info(class_name , StringResources.creating_message);
        switch (messageType) {
            case INFO:
                messageBox = MessageBox.createInfo();
                break;
            case ERROR:
                messageBox = MessageBox.createError();
                break;
            case WARNING:
                messageBox = MessageBox.createWarning();
                break;
            case QUESTION:
                messageBox = MessageBox.createQuestion();
                break;
        }
        if (messageBox == null) {
            PuzzleLogger.error(class_name , StringResources.message_type_null);
            throw new RuntimeException(StringResources.input_not_valid);
        }
        messageBox.withCaption(message.getCaption()).withMessage(message.getDescription());
        if (message.isWithCloseButton()) messageBox.withCloseButton(message.getBodyRunnable());
        if (message.isWithYesButton()) messageBox.withYesButton(message.getBodyRunnable());
        if (message.isWithNoButton()) messageBox.withNoButton(message.getBodyRunnable());
        if (message.isWithCancelButton()) messageBox.withCancelButton(message.getBodyRunnable());
        if (message.isWithRetryButton()) messageBox.withRetryButton(message.getBodyRunnable());
        return messageBox;
    }
}

