package com.jeremy.utils.initializers;

import com.jeremy.constants.StartLevelContent;
import com.jeremy.dao.PuzzleLevelDAO;
import com.jeremy.models.PuzzleLevel;
import com.jeremy.utils.containers.PuzzleLevelContainer;

import java.io.IOException;

/**Root point class , which main purpose is to initialize start level before all other classes
 * will use this level and also next levels of puzzle game(if it is needed for consumer).
 * That's so useful , as it provides 'lazy' loading*/
public final class PuzzleLevelInitializer {

    private static PuzzleLevelDAO puzzleLevelDAO;

    public static PuzzleLevelDAO getPuzzleLevelDAO() {
        if (puzzleLevelDAO == null) initStartLevel();
        return puzzleLevelDAO;
    }

    public static void setPuzzleLevelDAO(PuzzleLevelDAO puzzleLevelDAO) {
        PuzzleLevelInitializer.puzzleLevelDAO = puzzleLevelDAO;
    }

    public static synchronized void initStartLevel() {
        try {
            initLevel(StartLevelContent.levelName ,
                    StartLevelContent.id ,
                    StartLevelContent.fileName ,
                    StartLevelContent.countOfPieceByWidth ,
                    StartLevelContent.countOfPieceByHeight);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void initNextLevel(String levelName , int id , String fileName , int countOFPieceByWidth , int countOfPieceByHeight) {
        try {
            initLevel(levelName , id , fileName , countOFPieceByWidth , countOfPieceByHeight);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initLevel(String levelName , int id , String fileName , int countOfPieceByWidth , int countOfPieceByHeight) throws IOException {
        puzzleLevelDAO = new PuzzleLevelDAO(fileName , levelName);
        PuzzleLevelContainer.add(new PuzzleLevel(id,
                levelName,
                puzzleLevelDAO.getFileResource(),
                puzzleLevelDAO.getFullBufferedImage().getWidth(),
                puzzleLevelDAO.getFullBufferedImage().getHeight(),
                countOfPieceByWidth,
                countOfPieceByHeight));
    }

    public static void close() {
        puzzleLevelDAO = null;
    }
}
