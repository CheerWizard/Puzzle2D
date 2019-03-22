package com.jeremy.dao;

import com.jeremy.constants.FileConstants;
import com.jeremy.constants.StringResources;
import com.jeremy.utils.loggers.PuzzleLogger;
import com.vaadin.server.FileResource;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class PuzzleLevelDAO extends ImageDAO implements IDao {

    private String class_name = getClass().getName();

    protected FileResource fileResource;
    private String levelName;

    public PuzzleLevelDAO(String fileName , String levelName) throws IOException {
        super(fileName);
        this.levelName = levelName;
        PuzzleLogger.info(class_name , StringResources.initializing);
        fileResource = new FileResource(file);
    }

    public PuzzleLevelDAO() {
        PuzzleLogger.info(class_name , StringResources.initializing);
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    @Override
    public void writeImagePiece(double x, double y, double step_width, double step_height, int pieceId) throws IOException {
        super.writeImagePiece(x, y, step_width, step_height, pieceId);
    }

    @Override
    protected void createImagePieceFile(double x, double y, double step_width, double step_height, int pieceId) throws IOException {
        file = new File(FileConstants.LEVEL_DIR + levelName , pieceName + FileConstants.PIECE_OF_IMAGE + pieceId + FileConstants.PNG_FORMAT);
        pieceBufferedImage = fullBufferedImage.getSubimage((int) x,(int) y,(int) step_width,(int) step_height);
        ImageIO.write(pieceBufferedImage, "png", file);
    }

    @Override
    public String getClass_name() {
        return class_name;
    }

    @Override
    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public FileResource getFileResource() {
        return fileResource;
    }

    public void setFileResource(FileResource fileResource) {
        this.fileResource = fileResource;
    }

    @Override
    public void readImage(String dir, String fileName) throws IOException {
        super.readImage(dir, fileName);
        fileResource = new FileResource(file);
    }
}
