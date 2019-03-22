package com.jeremy.dao;

import com.jeremy.constants.FileConstants;
import com.jeremy.constants.StringResources;
import com.jeremy.utils.loggers.PuzzleLogger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageDAO implements IDao {

    private String class_name = getClass().getName();
    protected String fileName;
    //without .png or .jpg
    protected String pieceName;
    //full image
    protected BufferedImage fullBufferedImage;
    //piece of full image
    protected BufferedImage pieceBufferedImage;
    protected File file;

    public ImageDAO(String fileName) throws IOException {
        PuzzleLogger.info(class_name , StringResources.initializing);
        this.fileName = fileName;
        this.pieceName = fileName.substring(0 , fileName.length() - 4);
        PuzzleLogger.info(class_name , StringResources.reading_image);
        file = new File(FileConstants.IMAGES_DIR , fileName);
        readImage(file);
    }

    public ImageDAO() {
        //protect default constructor
        PuzzleLogger.info(class_name , StringResources.initializing);
        defaultInit();
    }

    private void defaultInit() {
        this.fileName = "";
        this.pieceName = "";
        this.file = new File("");
        this.fullBufferedImage = new BufferedImage(0 , 0 , BufferedImage.TYPE_BYTE_INDEXED);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPieceName() {
        return pieceName;
    }

    public void setPieceName(String pieceName) {
        this.pieceName = pieceName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void readImage(File file) throws IOException {
        fullBufferedImage = ImageIO.read(file);
    }

    public void readImage(String dir , String fileName) throws IOException {
        file = new File(dir , fileName);
        fullBufferedImage = ImageIO.read(file);
    }

    public void writeImagePiece(double x , double y , double step_width , double step_height , int pieceId) throws IOException {
        createImagePieceFile(x, y, step_width, step_height, pieceId);
    }

    protected void createImagePieceFile(double x , double y , double step_width , double step_height , int pieceId) throws IOException {
        file = new File(FileConstants.PIECES_DIR , pieceName + FileConstants.PIECE_OF_IMAGE + pieceId + FileConstants.PNG_FORMAT);
        pieceBufferedImage = fullBufferedImage.getSubimage((int) x,(int) y,(int) step_width,(int) step_height);
        ImageIO.write(pieceBufferedImage, "png", file);
    }

    public String getClass_name() {
        return class_name;
    }

    public BufferedImage getFullBufferedImage() {
        return fullBufferedImage;
    }

    public void setFullBufferedImage(BufferedImage fullBufferedImage) {
        this.fullBufferedImage = fullBufferedImage;
    }

    public BufferedImage getPieceBufferedImage() {
        return pieceBufferedImage;
    }

    public void setPieceBufferedImage(BufferedImage pieceBufferedImage) {
        this.pieceBufferedImage = pieceBufferedImage;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public boolean isHorizontalRectangle() {
        return fullBufferedImage.getWidth() > fullBufferedImage.getHeight();
    }

    public boolean isVerticalRectangle() {
        return fullBufferedImage.getWidth() < fullBufferedImage.getHeight();
    }

    public boolean isSquare() {
        return fullBufferedImage.getWidth() == fullBufferedImage.getHeight();
    }

    public boolean isRectangle() {
        return (isHorizontalRectangle() || isVerticalRectangle());
    }
    //TODO create method , that will check image_pieces directory for available files. It will be useful for faster executing and avoiding repeating of splitting!
}
