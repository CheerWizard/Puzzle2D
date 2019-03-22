package com.jeremy.services;

import com.jeremy.constants.StringResources;
import com.jeremy.controllers.IController;
import com.jeremy.models.Puzzle;
import com.jeremy.models.PuzzleLevel;
import com.jeremy.utils.helpers.PuzzleHelper;
import com.jeremy.utils.loggers.PuzzleLogger;

import java.util.Arrays;
import java.util.List;

public class PuzzleService extends Service implements IPuzzleService<Puzzle> {

    private String class_name = getClass().getName();

    public PuzzleService(IController controller) {
        super(controller);
        PuzzleLogger.info(class_name , StringResources.initializing);
    }

    public PuzzleService() {
        PuzzleLogger.info(class_name , StringResources.initializing);
    }

    @Override
    public IController getController() {
        return super.getController();
    }

    @Override
    public void setController(IController controller) {
        super.setController(controller);
    }

    @Override
    public Puzzle[] split(String levelName) {
        final List<Puzzle> puzzleList = PuzzleHelper.getPreparedPuzzles(levelName);
        final int size = PuzzleHelper.getPrimary_puzzles().size();
        return puzzleList.toArray(new Puzzle[size]);
    }

    @Override
    public boolean check(Puzzle[] puzzles) {
        return PuzzleHelper.checkPuzzles(puzzles);
    }

    @Override
    public Puzzle[] resolve(PuzzleLevel puzzleLevel) {
        return PuzzleHelper.getResolvedPuzzles(puzzleLevel);
    }

    @Override
    public Puzzle[] get() {
        final List<Puzzle> puzzles = PuzzleHelper.getPrimary_puzzles();
        return puzzles.toArray(new Puzzle[puzzles.size()]);
    }

    @Override
    public Puzzle[] update(Puzzle[] puzzles) {
        PuzzleHelper.setPrimary_puzzles(Arrays.asList(puzzles));
        return puzzles;
    }

    @Override
    public void close() {
        super.close();
    }
}
