package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard(10, 10);

        Scanner scanner = new Scanner(System.in);

        new Thread(() -> {
            while (true) {
                String input = scanner.nextLine();
                switch (input.toLowerCase()) {
                    case "w":
                        gameBoard.getSnake().setDirection(Direction.UP);
                        break;
                    case "a":
                        gameBoard.getSnake().setDirection(Direction.LEFT);
                        break;
                    case "s":
                        gameBoard.getSnake().setDirection(Direction.DOWN);
                        break;
                    case "d":
                        gameBoard.getSnake().setDirection(Direction.RIGHT);
                        break;
                }
            }
        }).start();

        GameState state = GameState.RUNNING;
        while (state == GameState.RUNNING) {
            state = gameBoard.update();
            gameBoard.render();
            System.out.println();
            System.out.println();
            try {
                Thread.sleep(3000); // Control the game speed
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        if (state == GameState.WON) {
            System.out.println("Congratulations! You won!");
        } else if (state == GameState.GAME_OVER) {
            System.out.println("Oh! You lost!");
        }

    }
}