package org.example.domain.model;

public class Grid {
    //0 - empty
    //1 - X
    //2 - O
    int[][] grid;

    public Grid() {
        this.grid = new int[3][3];
        this.initGrid();
    }

    private void initGrid() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.grid[i][j] = 0;
            }
        }
    }

    private boolean isCorrectGridCoords(int row, int col) {
        return !(row > 2 || col > 2 || row < 0 || col < 0);
    }

    public int getTypeCell(int row, int col){
        return this.grid[row][col];
    }

    public boolean setCell(int row, int col, int val) {
        if (!isCorrectGridCoords(row, col)) return false;
        this.grid[row][col] = val;
        return true;
    }
}
