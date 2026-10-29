package org.example.web.mapper;

import org.example.datasource.model.GridEntity;
import org.example.domain.model.Grid;
import org.example.web.model.GridDTO;

import java.util.ArrayList;
import java.util.List;

public class GridWebMapper {
    public Grid toDomain(GridDTO gridDTO){
        Grid grid = new Grid();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid.setCell(i, j, gridDTO.getTypeCell(i, j));
            }
        }
        return grid;
    }

    public GridDTO toWeb(Grid grid){
        GridDTO gridDTO = new GridDTO();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gridDTO.setCell(i, j, grid.getTypeCell(i, j));
            }
        }
        return gridDTO;
    }

    public GridDTO toWeb(GridEntity gridEntity){
        GridDTO gridDTO = new GridDTO();
        List<Integer> gridList = new ArrayList<>();
        String gridStr = gridEntity.getGrid();
        for (int i = 0; i < 9; i++) {
            gridList.add(Character.getNumericValue(gridStr.charAt(i)));
        }
        gridDTO.setGrid(gridList);
        return gridDTO;
    }
}
