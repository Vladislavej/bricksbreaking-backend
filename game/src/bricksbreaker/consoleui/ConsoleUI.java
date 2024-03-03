package bricksbreaker.consoleui;

import bricksbreaker.core.Field;
import bricksbreaker.core.GameState;
import bricksbreaker.core.Tile;
import bricksbreaker.core.TileInfo;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.ScoreServiceJDBC;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private Scanner scanner;
    private static final String ANSI_RESET = "\u001B[0m";
    private Field field;
    public ConsoleUI(Field field) {
        scanner = new Scanner(System.in);
        this.field = field;
    }
    public void showField() {
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

    public int[] handleInput() {
        try {
            int[] coordinates = new int[2];
            System.out.print("Enter x and y coordinates of your move: ");
            coordinates[0] = scanner.nextInt();
            coordinates[1] = scanner.nextInt();
            return coordinates;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }
        return null;
    }

    public void showStats(int score, int lives) {
        System.out.println("Score: " + score + " | Lives: " + lives);
    }

    public void setField(Field field) {
        this.field = field;
    }

    public void showHighScores(List<Score> topScores) {
        System.out.println("High scores:");
        for (int i = 0; i < 10; i++) {
            try {
                Score topScore = topScores.get(i);
                System.out.println((i+1) + ". " + topScore.getPlayer() + " " + topScore.getPoints() + " " + topScore.getPlayedOn());
            } catch (IndexOutOfBoundsException e) {
                return;
            }
        }
    }
    public boolean playAgain() {
        System.out.println("Do you want to play again? (yes/no)");
        String input = scanner.nextLine(); //required even tho idea says no lol otherwise program just terminates
        while(true) {
            input = scanner.nextLine();
            if (input.equals("yes")) {
                return true;
            } else if (input.equals("no")) {
                System.out.println("Thank you for playing!");
                return false;
            } else {
                System.out.println("Didnt get a valid answer!");
            }
        }
    }
    public int[] getFieldSpecs() {
        try {
            int[] specs = new int[3];
            System.out.print("Enter field x dimension: ");
            specs[0] = scanner.nextInt();
            while (specs[0] < 2) {
                System.out.print("Enter field dimension bigger than 1: ");
                specs[0] = scanner.nextInt();
            }

            System.out.print("Enter field y dimension: ");
            specs[1] = scanner.nextInt();
            while (specs[1] < 2) {
                System.out.print("Enter field dimension bigger than 1: ");
                specs[1] = scanner.nextInt();
            }

            System.out.print("Enter how many colors you want (maximum " + (TileInfo.values().length - 1) + "): ");
            specs[2] = scanner.nextInt();
            while (specs[2] < 2 || specs[2] > TileInfo.values().length - 1) {
                System.out.print("Enter more than two colors and not more than " + (TileInfo.values().length - 1) + ": ");
                specs[2] = scanner.nextInt();
            }

            return specs;
        } catch (InputMismatchException | NegativeArraySizeException e) {
            System.out.println("Invalid input. Please enter positive integers for dimensions and number of colors.");
            scanner.nextLine();
            return null;
        }
    }
}
