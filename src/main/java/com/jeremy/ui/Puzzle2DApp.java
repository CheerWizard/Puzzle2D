package com.jeremy.ui;

import com.jeremy.constants.StringResources;
import com.jeremy.ui.windows.MainPuzzleWindow;
import com.jeremy.utils.factories.AnimationFactory;
import com.jeremy.utils.factories.PuzzleServiceFactory;
import com.jeremy.utils.factories.SplitterFactory;
import com.jeremy.utils.initializers.PuzzleLevelInitializer;
import com.jeremy.utils.loggers.PuzzleLogger;

/**The application class , that plays launcher's role*/
public class Puzzle2DApp {

    private String class_name = getClass().getName();
    private static Puzzle2DApp instance;
    private MainPuzzleWindow mainPuzzleWindow;

    private Puzzle2DApp() {
        PuzzleLogger.info(class_name , StringResources.initializing);
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public MainPuzzleWindow getMainPuzzleWindow() {
        return mainPuzzleWindow;
    }

    public void setMainPuzzleWindow(MainPuzzleWindow mainPuzzleWindow) {
        this.mainPuzzleWindow = mainPuzzleWindow;
    }

    public static synchronized Puzzle2DApp getInstance() {
        if (instance == null) instance = new Puzzle2DApp();
        return instance;
    }

    public void closeApp() {
        //close initializer
        PuzzleLevelInitializer.close();
        //close factories
        AnimationFactory.close();
        PuzzleServiceFactory.close();
        SplitterFactory.close();
//        mainPuzzleWindow = null;
    }

    public void launchApplication() {
        PuzzleLevelInitializer.initStartLevel();
        mainPuzzleWindow = new MainPuzzleWindow();
    }
}