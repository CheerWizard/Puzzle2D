package com.jeremy.utils.splitters;

import com.jeremy.dao.IDao;
import com.jeremy.dao.PuzzleDAO;
import com.jeremy.models.Puzzle;
import com.jeremy.utils.containers.PuzzleContainer;

import java.io.IOException;

public class PuzzleSplitter extends ImageSplitter {

    private PuzzleDAO puzzleDAO;
    private PuzzleContainer puzzleContainer;

    public PuzzleSplitter(IDao dao) {
        super(dao);
        puzzleDAO = (PuzzleDAO) dao;
        puzzleContainer = new PuzzleContainer();
    }

    public PuzzleContainer getPuzzleContainer() {
        return puzzleContainer;
    }

    public void setPuzzleContainer(PuzzleContainer puzzleContainer) {
        this.puzzleContainer = puzzleContainer;
    }

    public PuzzleDAO getPuzzleDAO() {
        return puzzleDAO;
    }

    public void setPuzzleDAO(PuzzleDAO puzzleDAO) {
        this.puzzleDAO = puzzleDAO;
    }

    @Override
    public synchronized void split(int countOfPiecesByWidth, int countOfPiecesByHeight) {
        super.split(countOfPiecesByWidth, countOfPiecesByHeight);
    }

    @Override
    protected void createPiece(double x, double y, double step_width, double step_height, int pieceId) throws IOException {
        super.createPiece(x, y, step_width, step_height, pieceId);
        puzzleContainer.add(new Puzzle(pieceId , x , y , step_height , step_width , puzzleDAO.getPieceBufferedImage() , puzzleDAO.getFileResource()));
    }
}
