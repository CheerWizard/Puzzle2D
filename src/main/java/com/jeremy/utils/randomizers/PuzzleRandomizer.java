package com.jeremy.utils.randomizers;

import com.jeremy.constants.StringResources;
import com.jeremy.models.Puzzle;
import com.jeremy.utils.loggers.PuzzleLogger;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**The global class that can easy randomize your list of puzzles*/
public final class PuzzleRandomizer {

    private static String class_name = "Puzzle Randomizer";

    public static synchronized List<Puzzle> random(List<Puzzle> puzzles) {
        final long start = System.nanoTime();
        //check object for null state
        if (puzzles == null) {
            PuzzleLogger.error(class_name , StringResources.cant_randomize_null);
            throw new RuntimeException(StringResources.input_not_valid);
        }
        final Puzzle[] puzzles_array = puzzles.toArray(new Puzzle[puzzles.size()]);
        PuzzleLogger.info(class_name , StringResources.randomizing);
        /**Util method that can easy and fast random all elements*/
        // Creating a object for Random class
        Random r = new Random();
        // Start from the last element and swap one by one. We don't
        // need to run for the first element that's why i > 0
        for (int i = puzzles_array.length-1; i > 0; i--) {
            // Pick a random index from 0 to i
            int j = r.nextInt(i);
            // Swap arr[i] with the element at random index
            Puzzle temp = puzzles_array[i];
            puzzles_array[i] = puzzles_array[j];
            puzzles_array[j] = temp;
        }
        for (Puzzle puzzle:puzzles_array) {
            PuzzleLogger.info(class_name , "Randomed : " + puzzle.getPuzzle_id());
        }
        PuzzleLogger.info(class_name, StringResources.has_finished_processes);
        PuzzleLogger.info(class_name, "Total execution time : " + TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - start) + " seconds!");
        return Arrays.asList(puzzles_array);
    }

    public static String getClass_name() {
        return class_name;
    }

    public static void setClass_name(String class_name) {
        PuzzleRandomizer.class_name = class_name;
    }
}
