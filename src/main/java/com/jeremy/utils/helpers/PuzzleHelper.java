package com.jeremy.utils.helpers;

import com.jeremy.constants.StringResources;
import com.jeremy.dao.PuzzleDAO;
import com.jeremy.models.Puzzle;
import com.jeremy.models.PuzzleLevel;
import com.jeremy.utils.containers.PuzzleContainer;
import com.jeremy.utils.containers.PuzzleLevelContainer;
import com.jeremy.utils.factories.AnimationFactory;
import com.jeremy.utils.factories.PuzzleServiceFactory;
import com.jeremy.utils.factories.SplitterFactory;
import com.jeremy.utils.initializers.PuzzleLevelInitializer;
import com.jeremy.utils.loggers.PuzzleLogger;
import com.jeremy.utils.randomizers.PuzzleRandomizer;
import com.jeremy.utils.resolvers.PuzzleResolver;
import com.jeremy.utils.splitters.PuzzleSplitter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**The class that plays role like facade ,
 * which will use various classes and their functionality and execute them in one sequence*/
public final class PuzzleHelper {

    private static String class_name = "Puzzle Helper";

    private static List<Puzzle> primary_puzzles = new ArrayList<>();
    private static List<Puzzle> randomized_puzzles = new ArrayList<>();

    public static synchronized List<Puzzle> getPreparedPuzzles(String levelName) {
        final long start = System.nanoTime();
        PuzzleLogger.info(class_name, StringResources.initializing);
        try {
            /**Initialize puzzle data access object class*/
            final PuzzleDAO puzzleDAO = new PuzzleDAO(PuzzleLevelInitializer.getPuzzleLevelDAO().getFileName() , levelName);
            /**Initialize splitter class*/
            final PuzzleSplitter puzzleSplitter = SplitterFactory.getPuzzleSplitter(puzzleDAO);
            PuzzleLogger.info(class_name, StringResources.run_splitter_class);
            /**Start splitting*/
            puzzleSplitter.split(PuzzleLevelContainer.getPuzzleLevel(levelName).getCountOfPiecesByWidth(),
                    PuzzleLevelContainer.getPuzzleLevel(levelName).getCountOfPiecesByHeight());
            if (!puzzleSplitter.isRunning()) {
                /**Start getting buffered puzzles*/
                PuzzleLogger.info(class_name, StringResources.run_container_class);
                primary_puzzles = puzzleSplitter.getPuzzleContainer().getPuzzles();
                /**Start randomizing and getting again buffered puzzles*/
                PuzzleLogger.info(class_name, StringResources.run_randomizer_class);
                randomized_puzzles = PuzzleRandomizer.random(primary_puzzles);
            }
            PuzzleLogger.info(class_name , StringResources.didnt_find_exceptions);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PuzzleLogger.info(class_name, StringResources.has_finished_processes);
            PuzzleLogger.info(class_name, "Total execution time : " + TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - start) + " seconds!");
        }
        return randomized_puzzles;
    }

    public static synchronized Puzzle[] getResolvedPuzzles(PuzzleLevel puzzleLevel) {
        final long start = System.nanoTime();
        /**Start resolving process , which won't end
         * while it won't give the same picture (same id for each puzzle) ,
         * that was in primary state*/
        PuzzleLogger.info(class_name, StringResources.run_resolver_class);
        final Puzzle[] randomized_array = randomized_puzzles.toArray(new Puzzle[randomized_puzzles.size()]);
        Puzzle[] resolved_puzzles = PuzzleResolver.resolve(randomized_array , puzzleLevel);
        while (!checkPuzzles(resolved_puzzles)) resolved_puzzles = PuzzleResolver.resolve(resolved_puzzles , puzzleLevel);
        PuzzleLogger.info(class_name, StringResources.has_finished_processes);
        PuzzleLogger.info(class_name, "Total execution time : " + TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - start) + " seconds!");
        return resolved_puzzles;
    }

    public static synchronized boolean checkPuzzles(Puzzle[] puzzles) {
        int resolved = 0;
        final Puzzle[] primary_array = primary_puzzles.toArray(new Puzzle[puzzles.length]);
        for (int i = 0; i < puzzles.length; i++) {
            if (primary_array[i].getPuzzle_id() == puzzles[i].getPuzzle_id()) resolved++;
        }
        return resolved == puzzles.length;
    }

    public static String getClass_name() {
        return class_name;
    }

    public static void setClass_name(String class_name) {
        PuzzleHelper.class_name = class_name;
    }

    public static List<Puzzle> getPrimary_puzzles() {
        return primary_puzzles;
    }

    public static void setPrimary_puzzles(List<Puzzle> primary_puzzles) {
        PuzzleHelper.primary_puzzles = primary_puzzles;
    }

    public static List<Puzzle> getRandomized_puzzles() {
        return randomized_puzzles;
    }

    public static void setRandomized_puzzles(List<Puzzle> randomized_puzzles) {
        PuzzleHelper.randomized_puzzles = randomized_puzzles;
    }
    //close factories
    public static void close() {
        PuzzleLogger.info(class_name , StringResources.closing);
        PuzzleServiceFactory.close();
        SplitterFactory.close();
        AnimationFactory.close();
    }
}
