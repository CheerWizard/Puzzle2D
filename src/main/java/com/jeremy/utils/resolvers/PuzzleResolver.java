package com.jeremy.utils.resolvers;

import com.jeremy.constants.StringResources;
import com.jeremy.models.Puzzle;
import com.jeremy.models.PuzzleLevel;
import com.jeremy.utils.loggers.PuzzleLogger;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**This class will only try to resolve puzzle logic*/
public final class PuzzleResolver {

    private static String class_name = "Puzzle Resolver";

    public static synchronized Puzzle[] resolve(Puzzle[] puzzles , PuzzleLevel puzzleLevel) {
        final long start = System.nanoTime();
        PuzzleLogger.info(class_name , StringResources.resolving);
//        //sort in width
//        for (int i = 0; i < puzzleLevel.getCountOfPiecesByWidth() * puzzleLevel.getCountOfPiecesByHeight(); i++) {
//            if (i + 1 != puzzleLevel.getCountOfPiecesByWidth() * puzzleLevel.getCountOfPiecesByHeight() && puzzles[i].getPuzzle_id() > puzzles[i + 1].getPuzzle_id()) {
//                Puzzle temp = puzzles[i];
//                puzzles[i] = puzzles[i + 1];
//                puzzles[i + 1] = temp;
//            }
//        }
//        for (Puzzle puzzle:puzzles) {
//            PuzzleLogger.info(class_name , "1 step : " + puzzle.getPuzzle_id());
//        }
//        //sort in height
//        for (int i = 0; i < puzzleLevel.getCountOfPiecesByHeight() * puzzleLevel.getCountOfPiecesByWidth();) {
//            if (i + 1 != puzzleLevel.getCountOfPiecesByHeight() && puzzles[i].getPuzzle_id() > puzzles[i + 1].getPuzzle_id()) {
//                Puzzle temp = puzzles[i];
//                puzzles[i] = puzzles[i + 1];
//                puzzles[i + 1] = temp;
//            }
//            i += puzzleLevel.getCountOfPiecesByWidth();
//        }
//        for (Puzzle puzzle:puzzles) {
//            PuzzleLogger.info(class_name , "2 step : " + puzzle.getPuzzle_id());
//        }
//        //sort first-last
//        for (int i = puzzleLevel.getCountOfPiecesByWidth() - 1; i < puzzleLevel.getCountOfPiecesByWidth() * puzzleLevel.getCountOfPiecesByHeight();) {
//            if (i + 1 != puzzleLevel.getCountOfPiecesByHeight() * puzzleLevel.getCountOfPiecesByWidth()) {
//                Puzzle temp = puzzles[i];
//                puzzles[i] = puzzles[i + 1];
//                puzzles[i + 1] = temp;
//            }
//            i += puzzleLevel.getCountOfPiecesByWidth();
//        }
//        for (Puzzle puzzle:puzzles) {
//            PuzzleLogger.info(class_name , "3 step : " + puzzle.getPuzzle_id());
//        }
        Arrays.sort(puzzles);
        PuzzleLogger.info(class_name , StringResources.has_finished_processes);
        PuzzleLogger.info(class_name , "Execution time : " + TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - start) + " seconds!");
        return puzzles;
    }

    public static String getClass_name() {
        return class_name;
    }

    public static void setClass_name(String class_name) {
        PuzzleResolver.class_name = class_name;
    }
}
