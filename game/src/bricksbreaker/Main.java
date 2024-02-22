package bricksbreaker;

import bricksbreaker.consoleui.ConsoleUI;
import bricksbreaker.core.Field;

public class Main {
    public static void main(String[] args) {
        Field field = new Field(5,10);
        ConsoleUI consoleUI = new ConsoleUI(field);

        consoleUI.drawField();
        field.generateField();
        consoleUI.drawField();
        field.breakTile(2,2);
        consoleUI.drawField();
    }
}