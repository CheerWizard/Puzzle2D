package com.jeremy.ui.windows;

import com.jeremy.MyUI;
import com.jeremy.constants.MessageType;
import com.jeremy.constants.PuzzleLevelNames;
import com.jeremy.constants.StringResources;
import com.jeremy.controllers.IController;
import com.jeremy.controllers.MainPuzzleController;
import com.jeremy.models.Animation;
import com.jeremy.models.Message;
import com.jeremy.ui.IView;
import com.jeremy.ui.panels.PuzzleLevelPanel;
import com.jeremy.utils.factories.ControllerFactory;
import com.jeremy.utils.loggers.PuzzleLogger;
import com.jeremy.utils.senders.MessageSender;
import com.vaadin.ui.*;

import java.util.concurrent.TimeUnit;

/**Realize view implementation and also transfer window functionality*/
public class MainPuzzleWindow extends Window implements IView, Button.ClickListener {
    //class name
    private String class_name = getClass().getName();
    //appropriate controller
    private MainPuzzleController controller;
    //main layout
    private VerticalLayout verticalLayout;
    //game panel
    private PuzzleLevelPanel puzzleLevelPanel;
    //additional buttons
    private Button resolveButton, checkButton, startButton , nextButton , helpButton;
    //message label
    private Label messageLabel;
    //support var
    private boolean closed;
    private int level_id;
    //duration
    private long start_time;

    public MainPuzzleWindow() {
        PuzzleLogger.info(class_name, StringResources.initializing);
        closed = false;
        level_id = 0;
        this.controller = ControllerFactory.getMainPuzzleController(this);
        init();
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public IController getController() {
        return controller;
    }

    public void setController(IController controller) {
        this.controller = (MainPuzzleController) controller;
    }
    //main initialize
    private void init() {
        initLayouts();
        initComponents();
        initListeners();
        setContent(verticalLayout);
    }
    //Initialize main layouts
    private void initLayouts() {
        verticalLayout = new VerticalLayout();
    }
    //Initialize all components
    private void initComponents() {
        //labels
        messageLabel = new Label(StringResources.welcome_to_puzzle_2d);
        //panels
        puzzleLevelPanel = new PuzzleLevelPanel();
        //additional layouts
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        //buttons
        startButton = new Button(StringResources.start_level);
        resolveButton = new Button(StringResources.auto_resolve);
        checkButton = new Button(StringResources.done);
        nextButton = new Button(StringResources.next);
        helpButton = new Button(StringResources.help);
        //disable some buttons
        startLevelAnimate();
        //add all components to layouts
        horizontalLayout.addComponent(startButton);
        horizontalLayout.addComponent(resolveButton);
        horizontalLayout.addComponent(checkButton);
        horizontalLayout.addComponent(nextButton);
        horizontalLayout.addComponent(helpButton);
        verticalLayout.addComponent(puzzleLevelPanel);
        verticalLayout.addComponent(horizontalLayout);
        verticalLayout.addComponent(messageLabel);
    }
    //set listeners
    private void initListeners() {
        checkButton.addClickListener(this);
        startButton.addClickListener(this);
        resolveButton.addClickListener(this);
        nextButton.addClickListener(this);
        helpButton.addClickListener(this);
    }

    private void startLevelAnimate() {
        resolveButton.setEnabled(false);
        checkButton.setEnabled(false);
        startButton.setEnabled(true);
        nextButton.setEnabled(true);
        helpButton.setEnabled(true);
        startButton.setVisible(true);
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        if (clickEvent.getButton() == helpButton) {
            MessageSender
                    .createMessage(MessageType.INFO , new Message(StringResources.helpful_information ,
                            StringResources.helpful_description ,
                            true ,
                            null))
                    .open();
        }
        if (clickEvent.getButton() == resolveButton) {
            messageLabel.setValue(StringResources.resolving);
            controller.onResolve(puzzleLevelPanel.getPuzzleLevel());
            messageLabel.setValue(StringResources.resolved);
            resolveButton.setEnabled(false);
            checkButton.setEnabled(false);
            startButton.setEnabled(true);
            nextButton.setEnabled(true);
            //reset background logic
            new MyUI().close();
        }
        if (clickEvent.getButton() == checkButton) controller.onCheck(puzzleLevelPanel.getPuzzles());
        if (clickEvent.getButton() == startButton) {
            start_time = System.nanoTime();
            messageLabel.setValue(StringResources.rendering);
            controller.onSplit(puzzleLevelPanel.getPuzzleLevel().getName());
            messageLabel.setValue(StringResources.good_luck);
            checkButton.setEnabled(true);
            resolveButton.setEnabled(true);
            startButton.setEnabled(false);
            startButton.setVisible(false);
            nextButton.setEnabled(false);
        }
        if (clickEvent.getButton() == nextButton) {
            if (level_id < PuzzleLevelNames.Pikachu.levels.length - 1) {
                level_id++;
                puzzleLevelPanel.getController().onInitLevel(level_id, PuzzleLevelNames.Pikachu.levels[level_id]);
            }
            else level_id = -1;
            startLevelAnimate();
        }
    }

    @Override
    public void render(Animation animation) {
        switch (animation.getAnimationType()) {
            case RENDER_PUZZLES_ON_PANEL:
                puzzleLevelPanel.render(animation);
                break;
            case RENDER_VICTORY:
                messageLabel.setValue(StringResources.victory);
                nextButton.setEnabled(true);
                resolveButton.setEnabled(false);
                checkButton.setEnabled(false);
                MessageSender
                        .createMessage(MessageType.INFO , new Message(StringResources.game_stats ,
                        "You have spent : " + TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - start_time) + "seconds!" ,
                        true ,
                        (() -> new MyUI().close())))
                        .open();
                break;
            case RENDER_TRY_AGAIN:
                nextButton.setEnabled(true);
                messageLabel.setValue(StringResources.puzzle_not_resolved);
                MessageSender
                        .createMessage(MessageType.INFO , new Message(StringResources.game_stats ,
                                StringResources.puzzle_not_resolved ,
                                true ,
                                (() -> new MyUI().close())))
                        .open();
                break;
        }
    }

    @Override
    public void closeView() {
        controller.onClose();
        closed = true;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}
