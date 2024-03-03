package bricksbreaker;

import bricksbreaker.core.GameManager;
import bricksbreaker.ui.console.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        ConsoleUI consoleUI = new ConsoleUI(null);
        new GameManager(consoleUI);
    }
}