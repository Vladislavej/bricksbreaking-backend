package sk.tuke.gamestudio;

import sk.tuke.gamestudio.game.GameManager;
import sk.tuke.gamestudio.game.ui.console.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        //ConsoleUI consoleUI = new ConsoleUI(null);
        GameManager gameManager = new GameManager(null);
        gameManager.execute();
    }
}