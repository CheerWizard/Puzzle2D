package com.jeremy.controllers;

import com.jeremy.constants.AnimationType;
import com.jeremy.constants.StringResources;
import com.jeremy.models.Puzzle;
import com.jeremy.models.PuzzleLevel;
import com.jeremy.services.IPuzzleService;
import com.jeremy.ui.IView;
import com.jeremy.utils.factories.AnimationFactory;
import com.jeremy.utils.factories.PuzzleServiceFactory;
import com.jeremy.utils.helpers.PuzzleHelper;
import com.jeremy.utils.loggers.PuzzleLogger;
import com.vaadin.ui.Component;

public class MainPuzzleController extends Controller<IPuzzleService<Puzzle>> {

    private final String class_name = getClass().getName();

    private MainPuzzleController() {
        //lock
    }

    public MainPuzzleController(IView view) {
        super(view);
        PuzzleLogger.info(class_name , StringResources.initializing);
        service = PuzzleServiceFactory.getPuzzleService(this);
    }

    public String getClass_name() {
        return class_name;
    }

    public void onResolve(PuzzleLevel puzzleLevel) {
        view.render(AnimationFactory.getAnimation(AnimationType.RENDER_PUZZLES_ON_PANEL , service.resolve(puzzleLevel)));
    }

    public void onCheck(Puzzle[] puzzles) {
        if (PuzzleHelper.checkPuzzles(puzzles)) view.render(AnimationFactory.getAnimation(AnimationType.RENDER_VICTORY, null));
        else view.render(AnimationFactory.getAnimation(AnimationType.RENDER_TRY_AGAIN, null));
    }

    public void onSplit(String levelName) {
        view.render(AnimationFactory.getAnimation(AnimationType.RENDER_PUZZLES_ON_PANEL , service.split(levelName)));
    }

    @Override
    public void onClose() {
        super.onClose();
        service.close();
    }
}
