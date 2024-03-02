package bricksbreaker.consoleui;

import bricksbreaker.core.Field;
import bricksbreaker.core.GameState;
import bricksbreaker.core.Tile;
import bricksbreaker.core.TileInfo;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleUI {
    private Field field;
    private Scanner scanner;
    private int score;
    private static final String ANSI_RESET = "\u001B[0m";
    private int numColors;
    public ConsoleUI() {
        scanner = new Scanner(System.in);

        while (field == null) { field = initialiazeField(); }

        play(field);
    }
    private void showField() {
        System.out.println();

        int rows = field.getRows();
        int cols = field.getCols();

        System.out.print("     ");
        for (int j = 0; j < cols; j++) {
            System.out.printf("%3d", j);
        }
        System.out.println();

        System.out.print("      ");
        for (int j = 0; j < cols; j++) {
            System.out.print(" ─ ");
        }
        System.out.println();

        for (int i = 0; i < rows; i++) {
            System.out.printf("%3d | ", i);
            for (int j = 0; j < cols; j++) {
                Tile tile = field.getTiles()[i][j];
                if (tile != null) {
                    System.out.print(tile.getColor().getTcolor() + tile.getColor().getBcolor() + " " + tile.getColor().getSname() + " ");
                } else {
                    System.out.print(ANSI_RESET + " - ");
                }
            }
            System.out.println(ANSI_RESET);
        }
        System.out.println();
    }


    public void play(Field field) {
        this.field = field;
        prepareGame();
        showStats();
        do {
            showField();
            handleInput();
            field.unite();
            showStats();
            field.checkGameState();
        } while(field.getGameState() == GameState.PLAYING);

        showField();

        if(field.getGameState() == GameState.SOLVED) {
            System.out.println("Solved!");
        } else if(field.getGameState() == GameState.FAILED){
            System.out.println("Failed!");
        }

        System.out.println("Do you want to play again? (yes/no)");
        String input = scanner.nextLine(); //required even tho idea says no lol otherwise program just terminates
        boolean validAnswer = false;
        while(!validAnswer) {
            input = scanner.nextLine();
            if (input.equals("yes")) {
                field = null;
                while (field == null) { field = initialiazeField(); }
                play(field);
                validAnswer = true;
            } else if (input.equals("no")) {
                System.out.println("Thank you for playing!");
                validAnswer = true;
            } else {
                System.out.println("Didnt get a valid answer!");
            }
        }
    }

    private void handleInput() {
        try {
            if (scanner == null) {
                scanner = new Scanner(System.in);
            }
            System.out.print("Enter x and y coordinates of your move: ");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            field.breakTile(x, y);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }
    }

    private void showStats() {
        calculateStats();
        System.out.print("Score: " + score + " | Lives: " + field.getLives());
    }

    private void calculateStats() {
        score += field.getScoreThisMove() * field.getBrokenBricks();

        if(field.getBrokenBricks() == 1) { field.setLives(field.getLives() - 1); }

        field.setBrokenBricks(0);
        field.setScoreThisMove(0);
    }
    private void prepareGame() {
        field.generate(numColors);
        field.setLives(3);
        field.setBrokenBricks(0);
        field.setScoreThisMove(0);
        score = 0;
        field.setGameState(GameState.PLAYING);
    }

    private Field initialiazeField() {
        try {
            System.out.print("Enter field x dimension: ");
            int x = scanner.nextInt();
            while (x < 2) {
                System.out.print("Enter field dimension bigger than 1: ");
                x = scanner.nextInt();
            }

            System.out.print("Enter field y dimension: ");
            int y = scanner.nextInt();
            while (y < 2) {
                System.out.print("Enter field dimension bigger than 1: ");
                y = scanner.nextInt();
            }

            System.out.print("Enter how many colors you want (maximum " + (TileInfo.values().length - 1) + "): ");
            numColors = scanner.nextInt();
            while (numColors < 2 || numColors > TileInfo.values().length - 1) {
                System.out.print("Enter more than two colors and not more than " + (TileInfo.values().length - 1) + ": ");
                numColors = scanner.nextInt();
            }

            return new Field(x, y, numColors);
        } catch (InputMismatchException | NegativeArraySizeException e) {
            System.out.println("Invalid input. Please enter positive integers for dimensions and number of colors.");
            scanner.nextLine();
            return null;
        }
    }
}