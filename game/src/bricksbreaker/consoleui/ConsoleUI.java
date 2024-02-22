package bricksbreaker.consoleui;

import bricksbreaker.core.Field;
import bricksbreaker.core.GameState;

public class ConsoleUI {
    private Field field;
    private int score;
    public ConsoleUI(Field field) {
        this.field = field;
        this.score = 0;
    }
    public void show() {
        System.out.println();
        for (int i = 0; i < field.getRows(); i++) {
            for (int j = 0; j < field.getCols(); j++) {
                if(field.getBricks()[i][j] != null ) {
                    System.out.print(field.getBricks()[i][j].getColor());
                } else { System.out.print('-'); }
                System.out.print(' ');
            }
            System.out.println();
        }
        System.out.println();
    }

//    public void play(Field field) {
//        this.field = field;
//        do {
//            show();
//        } while(field.getGameState() == GameState.PLAYING);
//
//        show();
//
//        if(field.getGameState() == GameState.SOLVED) {
//            System.out.println("Solved!");
//        } else if(field.getGameState() == GameState.FAILED){
//            System.out.println("Failed!");
//        }
//    }
}
