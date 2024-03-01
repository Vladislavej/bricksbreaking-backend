package bricksbreaker.consoleui;

import bricksbreaker.core.Field;
import bricksbreaker.core.GameState;
import bricksbreaker.core.Tile;

import java.util.Scanner;

public class ConsoleUI {
    private Field field;
    private Scanner scanner;
    private int score;
    private static final String ANSI_RESET = "\u001B[0m";
    private int numColors;
    public ConsoleUI() {
        scanner = new Scanner(System.in);

        System.out.print("Enter field x dimension: ");
        int x = scanner.nextInt();
        System.out.print("Enter field y dimension: ");
        int y = scanner.nextInt();
        System.out.print("Enter how many colors you want: ");
        numColors = scanner.nextInt();

        field = new Field(x,y,3);
        play(field);
    }
    private void showField() {
        System.out.println();

        int rows = field.getRows();
        int cols = field.getCols();

        System.out.print("    ");
        for (int j = 0; j < cols; j++) {
            System.out.print(" " + j + " ");
        }
        System.out.println();

        System.out.print("    ");
        for (int j = 0; j < cols; j++) {
            System.out.print(" — ");
        }
        System.out.println();

        for (int i = 0; i < rows; i++) {
            System.out.print(i + " | ");
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
                play(field);
            } else if (input.equals("no")) {
                System.out.println("Thank you for playing!");
                validAnswer = true;
            } else {
                System.out.println("Didnt get a valid answer!");
            }
        }
    }

    private void handleInput() {
        if(scanner == null) { scanner = new Scanner(System.in); }
        System.out.print("Enter x and y coordinates of your move: ");
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        field.breakTile(x,y);
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
}