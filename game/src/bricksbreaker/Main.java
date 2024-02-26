package bricksbreaker;

import bricksbreaker.consoleui.ConsoleUI;
import bricksbreaker.core.Field;

public class Main {
    public static void main(String[] args) {
        Field field = new Field(5,10, 3);
        ConsoleUI consoleUI = new ConsoleUI();

        consoleUI.play(field);
    }
}