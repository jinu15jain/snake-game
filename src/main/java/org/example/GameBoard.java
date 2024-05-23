package org.example;

import java.util.Random;

public class GameBoard {

    private final int maxX;
    private final int maxY;
    private final Snake snake;
    private Cell[][] cells;
    private Cell foodCell;

    public GameBoard(int width, int height) {
        this.maxX = width;
        this.maxY = height;
        cells = new Cell[width][height];
        for (int y = maxY - 1; y >= 0 ; y--) {
            for (int x = 0; x < maxX; x++) {
                cells[y][x] = new Cell(x, y, CellType.EMPTY);
            }
        }
        this.snake = new Snake(getCell(maxX / 2, maxY / 2), Direction.RIGHT, this);
        generateFoodCell();
    }

    private void generateFoodCell() {
        Cell cell;
        do {
            Random random = new Random();
            int x = random.nextInt(maxX);
            int y = random.nextInt(maxY);
            cell = getCell(x, y);
        } while (snake.containsCell(cell));
        cell.setCellType(CellType.FOOD);
        this.foodCell = cell;
    }

    public Cell getCell(int x, int y) throws IndexOutOfBoundsException {
        if (x < 0 || x >= maxX || y < 0 || y >= maxY) {
            throw new IndexOutOfBoundsException(String.format("Invalid Cell (%s, %s)", x, y));
        }
        return cells[y][x];
    }

    public Snake getSnake() {
        return snake;
    }

    public GameState update() {
        if (snake.getSize() == maxX * maxY) {
            return GameState.WON;
        }
        Cell nextCell;
        try {
            nextCell = snake.getNextCell();
            System.out.printf("Next Cell (%s, %s, %s) to the %s", nextCell.getX(), nextCell.getY(), nextCell.getCellType(), snake.getDirection());
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            return GameState.GAME_OVER;
        }
        if (nextCell == foodCell) {
            snake.eatFood();
            generateFoodCell();
            return GameState.RUNNING;
        } else if (snake.containsCell(nextCell)) {
            System.out.printf("Snake Collided Itself (%s, %s)\n", snake.getNextCell().getX(), snake.getNextCell().getY());
            return GameState.GAME_OVER;
        }
        snake.moveNext();
        return GameState.RUNNING;
    }

    public void render() {
        char[][] board = new char[maxY][maxX];
        System.out.println("Snake Direction: " + snake.getDirection());
        for (int y = maxY - 1; y >= 0 ; y--) {
            for (int x = 0; x < maxX; x++) {
                switch (cells[y][x].getCellType()) {
                    case FOOD:
                        board[y][x] = 'F';
                        break;
                    case SNAKE:
                        board[y][x] = 'S';
                        break;
                    case EMPTY:
                        board[y][x] = '.';
                        break;
                }
            }
        }
        for (int y = maxY - 1; y >= 0; y--) {
            System.out.println(board[y]);
        }
    }
}
