package org.example.web.model;

import java.util.ArrayList;
import java.util.List;

public class GridDTO {
    //0 - empty
    //1 - X
    //2 - O
    private List<Integer> grid;

    public GridDTO() {
        this.grid = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            this.grid.add(0);
        }
    }

    private int getIndexOfElem(int row, int col){
        return row*3+col;
    }

    private boolean isCorrectGridCoords(int row, int col) {
        return !(row > 2 || col > 2 || row < 0 || col < 0);
    }

    public List<Integer> getGrid(){
        return this.grid;
    }

    public void setGrid(List<Integer> grid) {
        this.grid = grid;
    }

    public int getTypeCell(int row, int col){
        return this.grid.get(getIndexOfElem(row, col));
    }

    public boolean setCell(int row, int col, int val) {
        if (!isCorrectGridCoords(row, col)) return false;
        this.grid.set(getIndexOfElem(row, col), val);
        return true;
    }
}
