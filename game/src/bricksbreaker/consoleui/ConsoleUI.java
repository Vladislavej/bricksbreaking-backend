package bricksbreaker.consoleui;

import bricksbreaker.core.Field;
import bricksbreaker.core.GameState;
import bricksbreaker.core.Tile;

import java.util.Scanner;

public class ConsoleUI {
    private Field field;
    private int score;
    private Scanner scanner;
    public ConsoleUI() {
        this.score = 0;
    }
    public void show() {
        int rows = field.getRows();
        int cols = field.getCols();

        System.out.print("    ");
        for (int j = 0; j < cols; j++) {
            System.out.print(j + " ");
        }
        System.out.println();

        System.out.print("  X ");
        for (int j = 0; j < cols; j++) {
            System.out.print("- ");
        }
        System.out.println();

        for (int i = 0; i < rows; i++) {
            System.out.print(i + " | ");
            for (int j = 0; j < cols; j++) {
                Tile tile = field.getTiles()[i][j];
                if (tile != null) {
                    System.out.print(tile.getColor());
                } else {
                    System.out.print('-');
                }
                System.out.print(' ');
            }
            System.out.println();
        }
        System.out.println();
    }

    public void play(Field field) {
        this.field = field;
        do {
            show();
            handleInput();
            field.unite();
        } while(field.getGameState() == GameState.PLAYING);

        show();

        if(field.getGameState() == GameState.SOLVED) {
            System.out.println("Solved!");
        } else if(field.getGameState() == GameState.FAILED){
            System.out.println("Failed!");
        }
    }

    public void handleInput() {
        if(scanner == null) { scanner = new Scanner(System.in); }
        System.out.print("Enter x and y coordinates of your move: ");
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        field.breakTile(x,y);
    }
}