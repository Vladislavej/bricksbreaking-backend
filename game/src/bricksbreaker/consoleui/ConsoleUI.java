package bricksbreaker.consoleui;

import bricksbreaker.core.Field;

public class ConsoleUI {
    private Field field;
    private int score;
    public ConsoleUI(Field field) {
        this.field = field;
        this.score = 0;
    }
    public void drawField() {
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
}
