package com.jeremy.dao;

import com.vaadin.server.FileResource;

import java.io.IOException;

public class PuzzleDAO extends PuzzleLevelDAO implements IDao {

    private String class_name = getClass().getName();

    public PuzzleDAO(String fileName , String levelName) throws IOException {
        super(fileName , levelName);
    }

    @Override
    public String getClass_name() {
        return class_name;
    }

    @Override
    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    @Override
    protected void createImagePieceFile(double x, double y, double step_width, double step_height, int pieceId) throws IOException {
        super.createImagePieceFile(x, y, step_width, step_height, pieceId);
        fileResource = new FileResource(file);
    }
}
