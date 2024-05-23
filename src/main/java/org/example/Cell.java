package org.example;

public class Cell {
    private int x;
    private int y;
    private CellType cellType;

    public Cell(int X, int Y, CellType cellType) {
        this.x = X;
        this.y = Y;
        this.cellType = cellType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public CellType getCellType() {
        return cellType;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }

}
