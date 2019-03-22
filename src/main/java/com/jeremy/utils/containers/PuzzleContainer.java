package com.jeremy.utils.containers;

import com.jeremy.constants.StringResources;
import com.jeremy.models.Puzzle;
import com.jeremy.utils.loggers.PuzzleLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PuzzleContainer {

    private String class_name = getClass().getName();

    private List<Puzzle> puzzleList;

    public PuzzleContainer() {
        puzzleList = new ArrayList<>();
    }

    public List<Puzzle> getPuzzleList() {
        return puzzleList;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public void setPuzzleList(List<Puzzle> puzzleList) {
        this.puzzleList = puzzleList;
    }

    public void add(Puzzle puzzle) {
        PuzzleLogger.info(class_name, StringResources.adding_puzzle_to_container);
        puzzleList.add(puzzle);
    }

    public Puzzle get(int position) {
        PuzzleLogger.info(class_name, StringResources.getting_puzzle_from_container);
        return puzzleList.get(position);
    }

    public void delete(int position) {
        PuzzleLogger.info(class_name, StringResources.deleting_puzzle_from_container);
        puzzleList.remove(position);
    }
    /**
     * Checks the list for existing elements and , if their are no elements , then we can just stop executing the program and
     * message our consumer with reason , why it stops , as our algorithm is like transaction
     */
    public List<Puzzle> getPuzzles() {
        final long start = System.nanoTime();
        PuzzleLogger.info(class_name, StringResources.getting_splitted_image);
        if (puzzleList.size() == 0) {
            PuzzleLogger.error(class_name, StringResources.nothing_to_return);
            throw new RuntimeException(StringResources.buffered_image_does_not_exist);
        }
        PuzzleLogger.info(class_name, StringResources.has_finished_processes);
        PuzzleLogger.info(class_name, "Total execution time : " + TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - start) + " seconds!");
        return puzzleList;
    }
}