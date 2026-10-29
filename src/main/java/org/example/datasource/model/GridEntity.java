package org.example.datasource.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class GridEntity {
    //0 - empty
    //1 - X
    //2 - O
    String grid;

    public GridEntity() {
        this.grid = new String();
        this.initGrid();
    }

    private void initGrid() {
        this.grid = "000000000";
    }

    private int getIndexOfElem(int row, int col){
        return row*3+col;
    }

    public int getTypeCell(int row, int col){
        int index = getIndexOfElem(row, col);
        return Character.getNumericValue(this.grid.charAt(index));
    }

    public void setCell(int row, int col, int val) {
        char[] arrChar = this.grid.toCharArray();
        int index = getIndexOfElem(row, col);
        arrChar[index] = (char)('0'+val);
        this.grid = new String(arrChar);
    }

    public String getGrid() {return grid;}
    public void setGrid(String grid){this.grid=grid;}
}
