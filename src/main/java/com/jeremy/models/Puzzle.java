package com.jeremy.models;

import com.jeremy.constants.FileConstants;
import com.jeremy.constants.RotationType;
import com.jeremy.constants.StringResources;
import com.jeremy.utils.loggers.PuzzleLogger;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.Image;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Puzzle extends Image implements Component , Comparable<Puzzle> {
    //class name
    private String class_name = getClass().getName();
    //unique number
    private int puzzle_id;
    //coordinates on panel
    private double defaultCoordinateX;
    private double defaultCoordinateY;
    private double rotatedCoordinateX;
    private double rotatedCoordinateY;
    //width and height
    private double puzzle_width;
    private double puzzle_height;
    //for rotation
    private double step_deg;
    private BufferedImage rotatedBufferedImage;
    //for rendering
    private FileResource fileResource;
    private BufferedImage defaultBufferedImage;

    public Puzzle() {
        //default constructor
        step_deg = 0;
    }
    //for test
    public Puzzle(int puzzle_id) {
        this.puzzle_id = puzzle_id;
    }

    public Puzzle(int puzzle_id , double coordinateX , double coordinateY , double puzzle_height , double puzzle_width , BufferedImage bufferedImage , FileResource fileResource) {
        PuzzleLogger.info(class_name , StringResources.initializing);
        this.defaultBufferedImage = bufferedImage;
        this.rotatedBufferedImage = bufferedImage;
        this.defaultCoordinateX = coordinateX;
        this.defaultCoordinateY = coordinateY;
        this.rotatedCoordinateX = coordinateX;
        this.rotatedCoordinateY = coordinateY;
        step_deg = 0;
        this.fileResource = fileResource;
        this.puzzle_width = puzzle_width;
        this.puzzle_height = puzzle_height;
        setWidth(String.valueOf(puzzle_width));
        setHeight(String.valueOf(puzzle_height));
        setSource(fileResource);
        this.puzzle_id = puzzle_id;
        setId(String.valueOf(puzzle_id));
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public FileResource getFileResource() {
        return fileResource;
    }

    public void setFileResource(FileResource fileResource) {
        this.fileResource = fileResource;
        setSource(fileResource);
    }

    public int getPuzzle_id() {
        return puzzle_id;
    }

    public void setPuzzle_id(int puzzle_id) {
        this.puzzle_id = puzzle_id;
        setId(String.valueOf(puzzle_id));
    }

    public double getDefaultCoordinateX() {
        return defaultCoordinateX;
    }

    public void setDefaultCoordinateX(double defaultCoordinateX) {
        this.defaultCoordinateX = defaultCoordinateX;
    }

    public double getDefaultCoordinateY() {
        return defaultCoordinateY;
    }

    public void setDefaultCoordinateY(double defaultCoordinateY) {
        this.defaultCoordinateY = defaultCoordinateY;
    }

    public double getRotatedCoordinateX() {
        return rotatedCoordinateX;
    }

    public void setRotatedCoordinateX(double rotatedCoordinateX) {
        this.rotatedCoordinateX = rotatedCoordinateX;
    }

    public double getRotatedCoordinateY() {
        return rotatedCoordinateY;
    }

    public void setRotatedCoordinateY(double rotatedCoordinateY) {
        this.rotatedCoordinateY = rotatedCoordinateY;
    }

    public double getPuzzle_width() {
        return puzzle_width;
    }

    public void setPuzzle_width(double puzzle_width) {
        this.puzzle_width = puzzle_width;
        setWidth(String.valueOf(puzzle_width));
    }

    public double getPuzzle_height() {
        return puzzle_height;
    }

    public void setPuzzle_height(double puzzle_height) {
        this.puzzle_height = puzzle_height;
        setHeight(String.valueOf(puzzle_height));
    }

    public BufferedImage getRotatedBufferedImage() {
        return rotatedBufferedImage;
    }

    public void setRotatedBufferedImage(BufferedImage rotatedBufferedImage) {
        this.rotatedBufferedImage = rotatedBufferedImage;
    }

    public BufferedImage getDefaultBufferedImage() {
        return defaultBufferedImage;
    }

    public void setDefaultBufferedImage(BufferedImage defaultBufferedImage) {
        this.defaultBufferedImage = defaultBufferedImage;
    }

    public void rotate(double deg , RotationType rotationType) throws IOException {
        switch (rotationType) {
            case LEFT:
                //rotate to left
                leftRotate(deg);
                break;
            case RIGHT:
                //rotate to right
                rightRotate(deg);
                break;
        }
    }

    private void leftRotate(double deg) throws IOException {
        //change coordinates
        step_deg = step_deg + deg;
        if (step_deg <= 90) this.rotatedCoordinateY = this.rotatedCoordinateY + (this.puzzle_width * deg) / 90;
        if (step_deg > 90 && step_deg <= 180) this.rotatedCoordinateX = this.rotatedCoordinateX + (this.puzzle_height * deg) / 90;
        if (step_deg > 180 && step_deg <= 270) this.rotatedCoordinateY = this.rotatedCoordinateY - (this.puzzle_width * deg) / 90;
        if (step_deg > 270 && step_deg < 360) {
            this.rotatedCoordinateX = this.rotatedCoordinateX - (this.puzzle_height * deg) / 90;
            //reset buffer image , as it will look blurry
            rotatedBufferedImage = defaultBufferedImage;
        }
        if (step_deg >= 360) resetState();
        //change in buffer image
        changeBufferedImage(-deg * (Math.PI / 180));
    }

    private void rightRotate(double deg) throws IOException {
        //change coordinates
        step_deg = step_deg + deg;
        if (step_deg <= 90) this.rotatedCoordinateX = this.rotatedCoordinateX + (this.puzzle_width * deg) / 90;
        if (step_deg > 90 && step_deg <= 180) this.rotatedCoordinateY = this.rotatedCoordinateY + (this.puzzle_height * deg) / 90;
        if (step_deg > 180 && step_deg <= 270) this.rotatedCoordinateX = this.rotatedCoordinateX - (this.puzzle_width * deg) / 90;
        if (step_deg > 270 && step_deg < 360) {
            this.rotatedCoordinateY = this.rotatedCoordinateY - (this.puzzle_height * deg) / 90;
            //reset buffer image , as it will look blurry
            rotatedBufferedImage = defaultBufferedImage;
        }
        if (step_deg >= 360) resetState();
        //change in buffer image and in file
        changeBufferedImage(deg * (Math.PI / 180));
    }

    private void changeBufferedImage(double theta) throws IOException {
        AffineTransform tx = new AffineTransform();
        tx.rotate(theta, puzzle_width / 2, puzzle_height / 2);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        this.rotatedBufferedImage = op.filter(rotatedBufferedImage, null);

        File renamed = new File(FileConstants.ROTATED_DIR , FileConstants.ROTATED + (int) step_deg + FileConstants.PIECE_OF_IMAGE + puzzle_id + FileConstants.PNG_FORMAT);
        ImageIO.write(rotatedBufferedImage , "png" , renamed);
        setSource(new FileResource(renamed));
    }

    private void resetState() {
        rotatedCoordinateX = defaultCoordinateX;
        rotatedCoordinateY = defaultCoordinateY;
        step_deg = 0;
    }

    @Override
    public int hashCode() {
        return this.puzzle_id;
    }

    @Override
    public boolean equals(Object obj) {
        return this.puzzle_id == obj.hashCode();
    }

    @Override
    public int compareTo(Puzzle o) {
        int similarity = 0;
        if (this.puzzle_id > o.getPuzzle_id()) similarity = 1;
        if (this.puzzle_id < o.getPuzzle_id()) similarity = -1;
        return similarity;
    }
}
