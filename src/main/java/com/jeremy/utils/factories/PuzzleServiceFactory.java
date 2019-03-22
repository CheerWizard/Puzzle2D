package com.jeremy.utils.factories;

import com.jeremy.constants.StringResources;
import com.jeremy.controllers.IController;
import com.jeremy.services.PuzzleLevelService;
import com.jeremy.services.PuzzleService;
import com.jeremy.utils.loggers.PuzzleLogger;

public final class PuzzleServiceFactory {

    private static String class_name = "Puzzle Service Factory";
    private static PuzzleService puzzleService;
    private static PuzzleLevelService puzzleLevelService;

    public static synchronized PuzzleService getPuzzleService(IController controller) {
        if (puzzleService == null) puzzleService = new PuzzleService(controller);
        return puzzleService;
    }

    public static synchronized PuzzleLevelService getPuzzleLevelService(IController controller) {
        if (puzzleLevelService == null) puzzleLevelService = new PuzzleLevelService(controller);
        return puzzleLevelService;
    }

    public static void close() {
        PuzzleLogger.info(class_name , StringResources.closing);
        puzzleLevelService = null;
        puzzleService = null;
    }
}
