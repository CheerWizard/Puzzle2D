package com.jeremy.ui.panels;

import com.jeremy.constants.PuzzleLevelNames;
import com.jeremy.constants.RotationType;
import com.jeremy.constants.StringResources;
import com.jeremy.controllers.PuzzleLevelController;
import com.jeremy.models.Animation;
import com.jeremy.models.Puzzle;
import com.jeremy.models.PuzzleLevel;
import com.jeremy.ui.IView;
import com.jeremy.utils.factories.ControllerFactory;
import com.jeremy.utils.loggers.PuzzleLogger;
import com.vaadin.event.MouseEvents;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Panel;

import java.io.IOException;

public class PuzzleLevelPanel extends Panel implements IView , MouseEvents.ClickListener {
    //class name
    private String class_name = getClass().getName();
    //grid layout
    private GridLayout gridLayout;
    //controller
    private PuzzleLevelController controller;
    //level
    private PuzzleLevel puzzleLevel;
    //puzzles
    private Puzzle[] puzzles;
    //additional support vars
    private Puzzle firstPuzzle;
    private int clicked;
    private boolean closed;

    public PuzzleLevelPanel() {
        PuzzleLogger.info(class_name , StringResources.initializing);
        this.controller = ControllerFactory.getPuzzleLevelController(this);
        puzzles = new Puzzle[]{};
        gridLayout = new GridLayout();
        closed = false;
        clicked = 0;
        controller.onInitLevel(PuzzleLevelNames.Pikachu.LEVEL_1);
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public Puzzle[] getPuzzles() {
        return puzzles;
    }

    public void setPuzzles(Puzzle[] puzzles) {
        this.puzzles = puzzles;
    }

    public PuzzleLevelController getController() {
        return controller;
    }

    public void setController(PuzzleLevelController controller) {
        this.controller = controller;
    }

    public PuzzleLevel getPuzzleLevel() {
        return puzzleLevel;
    }

    public void setPuzzleLevel(PuzzleLevel puzzleLevel) {
        this.puzzleLevel = puzzleLevel;
    }

    public GridLayout getGridLayout() {
        return gridLayout;
    }

    public void setGridLayout(GridLayout gridLayout) {
        this.gridLayout = gridLayout;
    }

    public PuzzleLevelController getPuzzleLevelController() {
        return controller;
    }

    public void setPuzzleLevelController(PuzzleLevelController controller) {
        this.controller = controller;
    }

    public int getClicked() {
        return clicked;
    }

    public void setClicked(int clicked) {
        this.clicked = clicked;
    }

    private void initPuzzleLevel() {
        //clear old level
        gridLayout.removeAllComponents();
        //set new level
        setCaption(puzzleLevel.getName());
        setWidth(puzzleLevel.getWidth() + 16.0f, Unit.PIXELS);
        setHeight(puzzleLevel.getHeight() + 16.0f, Unit.PIXELS);
        gridLayout.setWidth(puzzleLevel.getWidth() , Unit.PIXELS);
        gridLayout.setHeight(puzzleLevel.getHeight() , Unit.PIXELS);
        gridLayout.setColumns(1);
        gridLayout.setRows(1);
        gridLayout.addComponent(puzzleLevel);
        setContent(gridLayout);
    }

    @Override
    public void click(MouseEvents.ClickEvent clickEvent) {
        if (clicked == 1) {
            int first_puzzle_pos = 0;
            int second_puzzle_pos = 0;
            final Puzzle second_puzzle = (Puzzle) clickEvent.getComponent();
            //find and save positions of clicked elements
            for (int i = 0; i < puzzles.length; i++) {
                if (puzzles[i].getPuzzle_id() == firstPuzzle.getPuzzle_id()) first_puzzle_pos = i;
                if (puzzles[i].getPuzzle_id() == second_puzzle.getPuzzle_id()) second_puzzle_pos = i;
            }
            //swap elements
            Puzzle temp = puzzles[first_puzzle_pos];
            puzzles[first_puzzle_pos] = puzzles[second_puzzle_pos];
            puzzles[second_puzzle_pos] = temp;
            //replace on UI
            gridLayout.replaceComponent(firstPuzzle , clickEvent.getComponent());
            clicked = 0;
        }
        else {
            firstPuzzle = (Puzzle) clickEvent.getComponent();
            clicked = 1;
        }
        if (clickEvent.isDoubleClick()) {
            final Puzzle clicked_puzzle = (Puzzle) clickEvent.getComponent();
            try {
                if (gridLayout.getWidth() == gridLayout.getHeight()) clicked_puzzle.rotate(90 , RotationType.RIGHT);
                else clicked_puzzle.rotate(180 , RotationType.RIGHT);
                gridLayout.replaceComponent(clickEvent.getComponent() , clicked_puzzle);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void render(Animation animation) {
        switch (animation.getAnimationType()) {
            case RENDER_PUZZLES_ON_PANEL:
                //clear old components
                gridLayout.removeAllComponents();
                //cast to puzzles
                puzzles = (Puzzle[]) animation.getComponents();
                PuzzleLogger.info(class_name , String.valueOf(puzzles.length));
                //set columns and rows
                gridLayout.setColumns(puzzleLevel.getCountOfPiecesByWidth());
                gridLayout.setRows(puzzleLevel.getCountOfPiecesByHeight());
                //set listeners and add to layout
                for (Puzzle puzzle:puzzles) {
                    puzzle.addClickListener(this);
                    gridLayout.addComponent(puzzle);
                }
                break;
            case RENDER_PUZZLE_LEVEL:
                //[0] inserted , as animation will always return only one level!
                puzzleLevel = (PuzzleLevel) animation.getComponents()[0];
                initPuzzleLevel();
                break;
        }
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    @Override
    public void closeView() {
        controller.onClose();
        closed = true;
    }
}
