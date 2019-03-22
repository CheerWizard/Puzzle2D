package com.jeremy.services;

import com.jeremy.constants.StartLevelContent;
import com.jeremy.constants.StringResources;
import com.jeremy.controllers.IController;
import com.jeremy.models.PuzzleLevel;
import com.jeremy.utils.containers.PuzzleLevelContainer;
import com.jeremy.utils.initializers.PuzzleLevelInitializer;
import com.jeremy.utils.loggers.PuzzleLogger;

public class PuzzleLevelService extends Service implements IPuzzleLevelService<PuzzleLevel> {

    private String class_name = getClass().getName();
    private int level_id;
    private int count_by_width;
    private int count_by_height;
    private String fileName;

    public PuzzleLevelService(IController controller) {
        super(controller);
        PuzzleLogger.info(class_name , StringResources.initializing);
        level_id = 0;
        count_by_height = StartLevelContent.countOfPieceByHeight;
        count_by_width = StartLevelContent.countOfPieceByWidth;
        fileName = StartLevelContent.fileName;
    }

    @Override
    public PuzzleLevel update(PuzzleLevel puzzleLevel) {
        return PuzzleLevelContainer.update(puzzleLevel);
    }

    @Override
    public PuzzleLevel get(String levelName) {
        return PuzzleLevelContainer.getPuzzleLevel(levelName);
    }

    @Override
    public PuzzleLevel get(int id , String levelName) {
        if (PuzzleLevelContainer.hasElement(id)) return PuzzleLevelContainer.getPuzzleLevel(id);
        else {
            PuzzleLevelInitializer.initNextLevel(levelName, ++level_id , fileName, ++count_by_width , ++count_by_height);
            return PuzzleLevelContainer.getPuzzleLevel(id);
        }
    }

    @Override
    public void delete(int id) {
        PuzzleLevelContainer.remove(id);
    }

    @Override
    public void delete(String name) {
        PuzzleLevelContainer.remove(name);
    }

    @Override
    public void close() {
        super.close();
    }
}
