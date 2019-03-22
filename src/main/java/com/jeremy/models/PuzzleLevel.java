package com.jeremy.models;

import com.jeremy.constants.StringResources;
import com.jeremy.utils.loggers.PuzzleLogger;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.Image;

public class PuzzleLevel extends Image implements Component {

    private String class_name = getClass().getName();
    private int puzzle_level_id;
    private String name;
    private int countOfPiecesByWidth;
    private int countOfPiecesByHeight;

    public PuzzleLevel(int puzzle_level_id, String name, FileResource fileResource, float width, float height , int countOfPiecesByWidth , int countOfPiecesByHeight) {
        PuzzleLogger.info(class_name , StringResources.initializing);
        this.puzzle_level_id = puzzle_level_id;
        this.name = name;
        setWidth(String.valueOf(width));
        setHeight(String.valueOf(height));
        setSource(fileResource);
        this.countOfPiecesByHeight = countOfPiecesByHeight;
        this.countOfPiecesByWidth = countOfPiecesByWidth;
    }

    public PuzzleLevel(int countOfPiecesByHeight , int countOfPiecesByWidth) {
        this.countOfPiecesByWidth = countOfPiecesByWidth;
        this.countOfPiecesByHeight = countOfPiecesByHeight;
    }

    public int getCountOfPiecesByWidth() {
        return countOfPiecesByWidth;
    }

    public void setCountOfPiecesByWidth(int countOfPiecesByWidth) {
        this.countOfPiecesByWidth = countOfPiecesByWidth;
    }

    public int getCountOfPiecesByHeight() {
        return countOfPiecesByHeight;
    }

    public void setCountOfPiecesByHeight(int countOfPiecesByHeight) {
        this.countOfPiecesByHeight = countOfPiecesByHeight;
    }

    public int getPuzzle_level_id() {
        return puzzle_level_id;
    }

    public void setPuzzle_level_id(int puzzle_level_id) {
        this.puzzle_level_id = puzzle_level_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return this.puzzle_level_id;
    }

    @Override
    public boolean equals(Object obj) {
        return this.puzzle_level_id == obj.hashCode();
    }

}
