package bricksbreaker;

import bricksbreaker.consoleui.ConsoleUI;
import bricksbreaker.core.Field;

public class Main {
    public static void main(String[] args) {
        Field field = new Field(5,10);
        ConsoleUI consoleUI = new ConsoleUI(field);

        consoleUI.show();
        field.generate();
        consoleUI.show();
        field.breakBrick(2,2);
        consoleUI.show();
        field.unite();
        consoleUI.show();
    }
}