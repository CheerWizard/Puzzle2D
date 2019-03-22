package com.jeremy.utils.containers;

import com.jeremy.constants.StringResources;
import com.jeremy.models.PuzzleLevel;
import com.jeremy.utils.loggers.PuzzleLogger;

import java.util.LinkedList;
import java.util.List;

public final class PuzzleLevelContainer {

    private static String class_name = "Puzzle Level Container";
    private static List<PuzzleLevel> puzzleLevelList = new LinkedList<>();

    public static List<PuzzleLevel> getPuzzleLevelList() {
        return puzzleLevelList;
    }

    public static void setPuzzleLevelList(List<PuzzleLevel> puzzleLevelList) {
        PuzzleLevelContainer.puzzleLevelList = puzzleLevelList;
    }

    public static void add(PuzzleLevel puzzleLevel) {
        PuzzleLogger.info(class_name , StringResources.adding_puzzle_level);
        puzzleLevelList.add(puzzleLevel);
    }

    public static PuzzleLevel update(PuzzleLevel puzzleLevel) {
        PuzzleLogger.info(class_name , StringResources.updating_puzzle_level);
        puzzleLevelList.set(puzzleLevel.getPuzzle_level_id() , puzzleLevel);
        return puzzleLevel;
    }

    public static void remove(int id) {
        PuzzleLogger.info(class_name , StringResources.deleting_puzzle_level);
        puzzleLevelList.remove(id);
    }

    public static void remove(String name) {
        PuzzleLogger.info(class_name , StringResources.deleting_puzzle_level);
        final PuzzleLevel removed = getPuzzleLevel(name);
        puzzleLevelList.remove(removed);
    }

    public static void clear() {
        PuzzleLogger.info(class_name , StringResources.clearing);
        puzzleLevelList.clear();
    }

    public static PuzzleLevel getPuzzleLevel(String name) {
        PuzzleLogger.info(class_name , StringResources.getting_puzzle_level);
        PuzzleLevel result = null;
        for (PuzzleLevel puzzleLevel:puzzleLevelList) if (puzzleLevel.getName() == name) result = puzzleLevel;
        if (result == null) {
            PuzzleLogger.error(class_name , StringResources.level_does_not_exist);
            throw new RuntimeException(StringResources.input_not_valid);
        }
        return result;
    }

    public static PuzzleLevel getPuzzleLevel(int id) {
        PuzzleLogger.info(class_name , StringResources.getting_puzzle_level);
        return puzzleLevelList.get(id);
    }

    public static boolean hasElement(int id) {
        return false;
    }
}
