package org.example;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

public class Snake {

    private final GameBoard board;
    private Direction direction;
    private Deque<Cell> body;
    ReentrantLock lock = new ReentrantLock();

    public Snake(Cell cell, Direction direction, GameBoard board) {
        body = new LinkedList<>();
        body.addFirst(cell);
        this.direction = direction;
        this.board = board;
    }

    public void setDirection(Direction newDirection) {
        // opposite direction not allowed
        lock.lock();
        if (this.direction == Direction.UP && newDirection == Direction.DOWN
                || this.direction == Direction.LEFT && newDirection == Direction.RIGHT
                || this.direction == Direction.DOWN && newDirection == Direction.UP
                || this.direction == Direction.RIGHT && newDirection == Direction.LEFT) {
          // do nothing
            System.out.println("Direction not changed " + newDirection);
            return;
        }

        System.out.println("Direction changed to " + newDirection);
        this.direction = newDirection;
        lock.unlock();
    }

    public void moveNext() {
        lock.lock();
        Cell newCell = this.getNextCellByDirection(body.peekFirst());
        Cell cell = body.removeLast();
        cell.setCellType(CellType.EMPTY);
        newCell.setCellType(CellType.SNAKE);
        body.addFirst(newCell);
        lock.unlock();
    }

    public void eatFood() {
        lock.lock();
        Cell newCell = this.getNextCellByDirection(body.peekFirst());
        newCell.setCellType(CellType.SNAKE);
        body.addFirst(newCell);
        lock.unlock();
    }

    public Cell getNextCell() {
        Cell cell = getNextCellByDirection(body.peekFirst());
        return cell;
    }

    private Cell getNextCellByDirection(Cell cell) {
        switch (this.direction) {
            case UP:
                return board.getCell(cell.getX(), cell.getY() + 1);
            case DOWN:
                return board.getCell(cell.getX(), cell.getY() - 1);
            case LEFT:
                return board.getCell(cell.getX() - 1, cell.getY());
            case RIGHT:
                return board.getCell(cell.getX() + 1, cell.getY());
        }
        throw new RuntimeException("Invalid direction");
    }

    public boolean containsCell(Cell cell) {
        return body.contains(cell);
    }

    public int getSize() {
        return body.size();
    }

    public Direction getDirection() {
        return direction;
    }
}
