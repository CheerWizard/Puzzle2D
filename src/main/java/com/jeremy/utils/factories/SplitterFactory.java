package com.jeremy.utils.factories;

import com.jeremy.constants.StringResources;
import com.jeremy.dao.ImageDAO;
import com.jeremy.dao.PuzzleDAO;
import com.jeremy.utils.loggers.PuzzleLogger;
import com.jeremy.utils.splitters.ImageSplitter;
import com.jeremy.utils.splitters.PuzzleSplitter;

public final class SplitterFactory {

    private static String class_name = "Splitter Factory";
    private static ImageSplitter imageSplitter;
    private static PuzzleSplitter puzzleSplitter;

    public synchronized static ImageSplitter getImageSplitter(ImageDAO imageDAO) {
        if (imageSplitter == null) imageSplitter = new ImageSplitter(imageDAO);
        return imageSplitter;
    }

    public static synchronized PuzzleSplitter getPuzzleSplitter(PuzzleDAO puzzleDAO) {
        if (puzzleSplitter == null) puzzleSplitter = new PuzzleSplitter(puzzleDAO);
        return puzzleSplitter;
    }

    public static void close() {
        PuzzleLogger.info(class_name , StringResources.closing);
        imageSplitter = null;
        puzzleSplitter = null;
    }
}
