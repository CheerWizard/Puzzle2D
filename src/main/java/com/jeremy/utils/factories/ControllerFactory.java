package com.jeremy.utils.factories;

import com.jeremy.controllers.PuzzleLevelController;
import com.jeremy.controllers.MainPuzzleController;
import com.jeremy.ui.IView;

public final class ControllerFactory {

    public static synchronized MainPuzzleController getMainPuzzleController(IView view) {
        return new MainPuzzleController(view);
    }

    public static synchronized PuzzleLevelController getPuzzleLevelController(IView view) {
        return new PuzzleLevelController(view);
    }
}
