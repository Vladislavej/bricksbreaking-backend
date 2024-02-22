package bricksbreaker.consoleui;

import bricksbreaker.core.Field;
import bricksbreaker.core.GameState;

import java.util.Scanner;

public class ConsoleUI {
    private Field field;
    private int score;
    private Scanner scanner;
    public ConsoleUI() {
        this.score = 0;
    }
    public void show() {
        System.out.println();
        for (int i = 0; i < field.getRows(); i++) {
            for (int j = 0; j < field.getCols(); j++) {
                if(field.getTiles()[i][j] != null ) {
                    System.out.print(field.getTiles()[i][j].getColor());
                } else { System.out.print('-'); }
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
