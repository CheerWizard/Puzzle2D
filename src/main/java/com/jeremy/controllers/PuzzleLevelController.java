package com.jeremy.controllers;

import com.jeremy.constants.AnimationType;
import com.jeremy.constants.StringResources;
import com.jeremy.models.PuzzleLevel;
import com.jeremy.services.IPuzzleLevelService;
import com.jeremy.ui.IView;
import com.jeremy.utils.factories.AnimationFactory;
import com.jeremy.utils.factories.PuzzleServiceFactory;
import com.jeremy.utils.loggers.PuzzleLogger;

public class PuzzleLevelController extends Controller<IPuzzleLevelService<PuzzleLevel>> {

    private final String class_name = getClass().getName();

    public PuzzleLevelController(IView view) {
        super(view);
        PuzzleLogger.info(class_name , StringResources.initializing);
        service = PuzzleServiceFactory.getPuzzleLevelService(this);
    }

    private PuzzleLevelController() {
        //lock default constructor temporary
    }

    public void onInitLevel(String levelName) {
        final PuzzleLevel[] puzzleLevel = new PuzzleLevel[]{service.get(levelName)};
        view.render(AnimationFactory.getAnimation(AnimationType.RENDER_PUZZLE_LEVEL , puzzleLevel));
    }

    public void onInitLevel(int id , String levelName) {
        final PuzzleLevel[] puzzleLevel = new PuzzleLevel[]{service.get(id , levelName)};
        view.render(AnimationFactory.getAnimation(AnimationType.RENDER_PUZZLE_LEVEL , puzzleLevel));
    }

    @Override
    public void onClose() {
        super.onClose();
        service.close();
    }
}
