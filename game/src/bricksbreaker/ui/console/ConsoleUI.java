    package bricksbreaker.ui.console;

    import bricksbreaker.core.Field;
    import bricksbreaker.core.GameManager;
    import bricksbreaker.core.Tile;
    import bricksbreaker.core.TileInfo;
    import bricksbreaker.ui.GameUI;
    import sk.tuke.gamestudio.entity.Score;

    import java.text.SimpleDateFormat;
    import java.util.InputMismatchException;
    import java.util.List;
    import java.util.Objects;
    import java.util.Scanner;

    public class ConsoleUI implements GameUI {
        private Scanner scanner;
        private static final String ANSI_RESET = "\u001B[0m";
        public static final String BLACK_BACKGROUND = "\033[40m"; // BLACK
        public static final String CYAN_BOLD_BRIGHT = "\033[1;96m"; // CYAN
        public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
        public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
        public static final String RED_UNDERLINED = "\033[4;31m";    // RED
        public static final String	YELLOW = "\u001B[33m";
        private Field field;
        private String player;

        public ConsoleUI(Field field) {
            scanner = new Scanner(System.in);
            this.field = field;
            this.player = "Player";
        }
        @Override
        public void showField() {
            int rows = field.getRows();
            int cols = field.getCols();

            System.out.print(BLACK_BACKGROUND + "      " + ANSI_RESET);
            for (int j = 0; j < cols; j++) {
                System.out.printf(BLACK_BACKGROUND + YELLOW_BOLD_BRIGHT + "%2d " + ANSI_RESET, j);
            }
            System.out.println();

            System.out.print(BLACK_BACKGROUND + "      " + ANSI_RESET);
            for (int j = 0; j < cols; j++) {
                System.out.print(BLACK_BACKGROUND + YELLOW_BOLD_BRIGHT +  " ─ " + ANSI_RESET);
            }
            System.out.println();

            for (int i = 0; i < rows; i++) {
                System.out.printf(BLACK_BACKGROUND + YELLOW_BOLD_BRIGHT + "%3d | " + ANSI_RESET, i);
                for (int j = 0; j < cols; j++) {
                    Tile tile = field.getTiles()[i][j];
                    if (tile != null) {
                        System.out.print(tile.getColor().getTcolor() + tile.getColor().getBcolor() + " " + tile.getColor().getSname() + " " + ANSI_RESET);
                    } else {
                        System.out.print(BLACK_BACKGROUND + " × " + ANSI_RESET);
                    }
                }
                System.out.println(ANSI_RESET);
            }

            System.out.println();
        }

        @Override
        public int[] handleMove() {
            try {
                int[] coordinates = new int[2];
                System.out.print(BLACK_BACKGROUND + YELLOW_BOLD_BRIGHT + " Enter x and y coordinates of your move: " + ANSI_RESET + " ");
                coordinates[0] = scanner.nextInt();
                coordinates[1] = scanner.nextInt();
                return coordinates;
            } catch (InputMismatchException e) {
                System.out.println(RED_UNDERLINED + "Invalid input. Correct input example: '4 8'" + ANSI_RESET);
                scanner.nextLine();
                return handleMove();
            }
        }

        @Override
        public void showStats(int score, int lives) {
            clearScreen();
            System.out.println(BLACK_BACKGROUND+ YELLOW_BOLD_BRIGHT + " Score: " + score + " | Lives: " + lives + " " + ANSI_RESET);
        }

        @Override
        public void setField(Field field) {
            this.field = field;
        }

        @Override
        public void showHighScores(List<Score> topScores) {
            System.out.println();
            System.out.println(BLACK_BACKGROUND + YELLOW_BOLD_BRIGHT +"High scores:" + ANSI_RESET);
            for (int i = 0; i < 10; i++) {
                try {
                    Score topScore = topScores.get(i);
                    String rank = String.format("%-2d", i + 1);
                    String player = String.format("%-25s", topScore.getPlayer());
                    String points = String.format("%-10s", topScore.getPoints());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String playedOn = String.format("%-20s", dateFormat.format(topScore.getPlayedOn()));
                    System.out.println(BLACK_BACKGROUND + YELLOW_BOLD_BRIGHT + rank + ". " + ANSI_RESET + BLACK_BACKGROUND + player + ANSI_RESET + BLACK_BACKGROUND + YELLOW_BOLD_BRIGHT + points + ANSI_RESET + BLACK_BACKGROUND + playedOn + ANSI_RESET);
                } catch (IndexOutOfBoundsException e) { return; }
            }
        }

        @Override
        public boolean playAgain() {
            System.out.println("Do you want to play again? (yes/no)");
            String input = scanner.nextLine(); //required even tho idea says no lol otherwise program just terminates
            while(true) {
                input = scanner.nextLine();
                if (input.toLowerCase().equals("yes")) {
                    return true;
                } else if (input.toLowerCase().equals("no")) {
                    return false;
                } else {
                    System.out.println("Didnt get a valid answer!");
                }
            }
        }

        @Override
        public int mainMenu() {
            System.out.println();
            System.out.println("---------------");
            System.out.println("Bricks Breaking");
            System.out.println("---------------");
            System.out.println();
            System.out.println("1. Classic Game");
            System.out.println("2. Custom Game");
            System.out.println("3. Show HighScores");
            System.out.println("4. Change Player Name (" + player + ")");
            System.out.println("5. Exit");

            System.out.println();

            System.out.println("6. Comment");
            System.out.println("7. Rating");
            System.out.println();

            try {
                int input = -1;
                input = scanner.nextInt();
                switch (input) {
                    case 1:
                        return input;
                    case 2:
                        return input;
                    case 3:
                        return input;
                    case 4:
                        return input;
                    case 5:
                        System.out.println("Thank you for playing!");
                        return input;
                    case 6:
                        System.out.println("Enter comment (as "+ player + "): ");
                        return input;
                    case 7:
                        System.out.println("Enter rating (as "+ player + "): ");
                        return input;
                }
            } catch (InputMismatchException e) {
                clearScreen();
                System.out.println(RED_UNDERLINED + "Invalid input. Pick a valid menu option. Example: '1' for Classic Game" + ANSI_RESET);
                scanner.nextLine();
                return mainMenu();
            }
            clearScreen();
            System.out.println(RED_UNDERLINED + "Invalid input. Pick a valid menu option. Example: '1' for Classic Game" + ANSI_RESET);
            return 0;
        }

        @Override
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

        @Override
        public String playerName() {
            clearScreen();
            System.out.println("Enter new name: ");
            String input = scanner.nextLine();
            input = scanner.nextLine();
            player = input;
            return input;
        }

        private static void clearScreen() {
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }

        @Override
        public String getComment() {
            String input = scanner.nextLine();
            input = scanner.nextLine();
            if(Objects.equals(input, "cancel")) {
                System.out.println(RED_UNDERLINED + "Comment canceled" + ANSI_RESET);
                return " ";
            }
            return input;
        }

        @Override
        public int getRating() {
            try {
                int input = scanner.nextInt();
                return input;
            } catch (InputMismatchException e) {
                System.out.println(RED_UNDERLINED + "Enter valid rating" + ANSI_RESET);
                scanner.nextLine();
                return getRating();
            }
        }
    }
