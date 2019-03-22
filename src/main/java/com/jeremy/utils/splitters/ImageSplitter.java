package com.jeremy.utils.splitters;

import com.jeremy.constants.StringResources;
import com.jeremy.dao.IDao;
import com.jeremy.dao.ImageDAO;
import com.jeremy.utils.loggers.PuzzleLogger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ImageSplitter {

    private final String class_name = getClass().getName();
    //property
    protected boolean isRunning;
    //global vars
    protected ImageDAO imageDAO;
    protected int absolute_width;
    protected int absolute_height;

    private ImageSplitter() {
        //lock default constructor
    }

    public ImageSplitter(IDao dao) {
        this.imageDAO = (ImageDAO) dao;
    }

    public ImageDAO getImageDAO() {
        return imageDAO;
    }

    public void setImageDAO(ImageDAO imageDAO) {
        this.imageDAO = imageDAO;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public int getAbsolute_width() {
        return absolute_width;
    }

    public void setAbsolute_width(int absolute_width) {
        this.absolute_width = absolute_width;
    }

    public int getAbsolute_height() {
        return absolute_height;
    }

    public void setAbsolute_height(int absolute_height) {
        this.absolute_height = absolute_height;
    }

    public synchronized void split(int countOfPiecesByWidth , int countOfPiecesByHeight) {
        final long start = System.nanoTime();
        isRunning = true;
        try {
            /**read res file to buffer image*/
            PuzzleLogger.info(class_name ,  StringResources.initializing);
            /**get width and height of image*/
            absolute_width = imageDAO.getFullBufferedImage().getWidth();
            absolute_height = imageDAO.getFullBufferedImage().getHeight();
            //check counts conditions
            if (countOfPiecesByHeight == 0 || countOfPiecesByWidth == 0) {
                PuzzleLogger.error(class_name, StringResources.didnt_input_count_of_pieces);
                throw new RuntimeException(StringResources.input_not_valid);
            }
            if (absolute_width == absolute_height) splitSquare(countOfPiecesByWidth * countOfPiecesByHeight);
            else splitRectangle(countOfPiecesByWidth , countOfPiecesByHeight);
            PuzzleLogger.info(class_name, StringResources.didnt_find_exceptions);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PuzzleLogger.info(class_name, StringResources.has_finished_processes);
            PuzzleLogger.info(class_name, "Total execution time : " + TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - start) + " seconds!");
            isRunning = false;
//            notify();
        }
    }

    private void splitSquare(int countOfPieces) throws IOException {
        PuzzleLogger.info(class_name , StringResources.splitting_square);
        int repeat = 0;
        /**Attention! As you see here , their can be probability that it will return not accurate value , as that is double.*/
        final double step = (absolute_width / Math.sqrt(countOfPieces));
        for (int pieceId = 0; pieceId < countOfPieces;) {
            pieceId = splitWidth(0 , step*repeat , step , step , pieceId);
            //change repeat
            repeat++;
        }
    }

    private void splitRectangle(int countOfPiecesByWidth , int countOfPiecesByHeight) throws IOException {
        PuzzleLogger.info(class_name , StringResources.splitting_rectangle);
        /**Attention! As you see here , their can be probability that it will return not accurate value , as that is double.*/
        final double step_width = (double) absolute_width / countOfPiecesByWidth;
        final double step_height = (double) absolute_height / countOfPiecesByHeight;
        //start algorithm
        start(countOfPiecesByHeight , countOfPiecesByWidth , step_width , step_height);
    }

    private void start(int countOfPiecesByHeight , int countOfPiecesByWidth , final double step_width , final double step_height) throws IOException {
        int repeat = 0;
        //splitting to width end
        for (int pieceId = 0; pieceId < countOfPiecesByHeight * countOfPiecesByWidth; ) {
            pieceId = splitWidth(0, step_height * repeat, step_width, step_height, pieceId);
            //change repeat
            repeat++;
        }
        //splitting to height end
        for (int pieceId = 0; pieceId < countOfPiecesByHeight * countOfPiecesByWidth; ) {
            pieceId = splitHeight(step_width*repeat , 0, step_width , step_height , pieceId);
            //change repeat
            repeat++;
        }
    }

    private int splitWidth(double x , double y , final double step_width , final double step_height , int pieceId) throws IOException {
        /**Attention! As we can have the probability that step will not be accurate , I have changed x to x + 1 here!*/
        while (x + 1 < absolute_width) {
            createPiece(x, y, step_width, step_height, pieceId);
            pieceId++;
            x = x + step_width;
            PuzzleLogger.info(class_name , "x : " + x);
        }
        return pieceId;
    }

    private int splitHeight(double x, double y , final double step_width , final double step_height , int pieceId) throws IOException {
        /**Attention! As we can have the probability that step will not be accurate , I have changed y to y + 1 here*/
        while (y + 1 < absolute_height) {
            createPiece(x, y, step_width, step_height, pieceId);
            pieceId++;
            y = y + step_height;
            PuzzleLogger.info(class_name , "x : " + x);
        }
        return pieceId;
    }

    protected void createPiece(double x , double y , final double step_width , final double step_height , int pieceId) throws IOException {
        PuzzleLogger.info(class_name , StringResources.creating_image);
        imageDAO.writeImagePiece(x, y, step_width, step_height, pieceId);
    }
}
