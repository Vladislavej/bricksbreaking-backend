    package bricksbreaker.ui.console;

    import bricksbreaker.core.Field;
    import bricksbreaker.core.GameManager;
    import bricksbreaker.core.Tile;
    import bricksbreaker.core.TileInfo;
    import bricksbreaker.ui.GameUI;
    import org.checkerframework.checker.units.qual.A;
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
        public static final String GREEN_UNDERLINED = "\033[4;32m";
        public static final String WHITE_BACKGROUND = "\033[47m";  // WHITE
        public static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
        public static final String BLACK_BRIGHT = "\033[0;90m";  // BLACK
        public static final String	YELLOW = "\u001B[33m";
        private Field field;
        private String player;
        private int averageRating;

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
                System.out.printf(BLACK_BACKGROUND + "%2d " + ANSI_RESET, j);
            }
            System.out.println();

            System.out.print(BLACK_BACKGROUND + "      " + ANSI_RESET);
            for (int j = 0; j < cols; j++) {
                System.out.print(BLACK_BACKGROUND +  " ─ " + ANSI_RESET);
            }
            System.out.println();

            for (int i = 0; i < rows; i++) {
                System.out.printf(BLACK_BACKGROUND + "%3d | " + ANSI_RESET, i);
                for (int j = 0; j < cols; j++) {
                    Tile tile = field.getTiles()[i][j];
                    if (tile != null) {
                        System.out.print(tile.getColor().getBcolor() + " " + tile.getColor().getTcolor() + tile.getColor().getSname() + " " + ANSI_RESET);
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
                System.out.print(BLACK_BACKGROUND + " Enter x and y coordinates of your move: " + ANSI_RESET + " ");
                coordinates[0] = scanner.nextInt();
                coordinates[1] = scanner.nextInt();
                return coordinates;
            } catch (InputMismatchException e) {
                System.out.println(RED_UNDERLINED + "\uD83D\uDED1Invalid input. Correct input example: '4 8'\uD83D\uDED1" + ANSI_RESET);
                scanner.nextLine();
                return handleMove();
            }
        }

        @Override
        public void showStats(int score, int lives) {
            clearScreen();
            System.out.println(BLACK_BACKGROUND + " 🌟 " + score + " | 💖 " + lives + " " + ANSI_RESET);
        }

        @Override
        public void setField(Field field) {
            this.field = field;
        }

        @Override
        public void showHighScores(List<Score> topScores) {
            System.out.println();
            System.out.println(BLACK_BACKGROUND + YELLOW_BOLD_BRIGHT +"🔥High scores🔥" + ANSI_RESET);
            for (int i = 0; i < 10; i++) {
                try {
                    Score topScore = topScores.get(i);
                    String rank = String.format("%-2d", i + 1);
                    String player = String.format("%-25s", topScore.getPlayer());
                    String points = String.format("%-10s", topScore.getPoints());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String playedOn = String.format("%-20s", dateFormat.format(topScore.getPlayedOn()));
                    System.out.println(BLACK_BACKGROUND + rank + ". " + ANSI_RESET + BLACK_BACKGROUND + player + ANSI_RESET + BLACK_BACKGROUND + YELLOW_BOLD_BRIGHT + points + ANSI_RESET + BLACK_BACKGROUND + playedOn + ANSI_RESET);
                } catch (IndexOutOfBoundsException e) { return; }
            }
            System.out.println("\n" + BLACK_BACKGROUND + "Enter to continue" + ANSI_RESET);
            scanner.nextLine();
            scanner.nextLine();
        }

        @Override
        public boolean playAgain() {
            System.out.println();
            System.out.println(BLACK_BACKGROUND + "Do you want to play again? (yes/no)" + ANSI_RESET);
            while(true) {
                String input = scanner.nextLine();
                if (input.toLowerCase().equals("yes")) {
                    clearScreen();
                    return true;
                } else if (input.toLowerCase().equals("no")) {
                    clearScreen();
                    return false;
                } else {
                    System.out.println(RED_UNDERLINED + "\uD83D\uDED1Didnt get a valid answer!\uD83D\uDED1" + ANSI_RESET);
                }
            }
        }

        @Override
        public int mainMenu() {
            clearScreen();

            printLogo();

            System.out.println(GREEN_UNDERLINED + "1. 🎮Classic Game               " + ANSI_RESET);
            System.out.println("2. 🔧Custom Game                "+ ANSI_RESET);
            System.out.println(YELLOW_UNDERLINED + "3. 🏆High-scores                " + ANSI_RESET);
            System.out.println("4. 📝Change Player Name (" + player + ")"+ ANSI_RESET);
            System.out.println(RED_UNDERLINED + "5. ❌Exit                       " + ANSI_RESET);

            System.out.println();

            System.out.println("6. 💌Comment                    "+ ANSI_RESET);
            System.out.println("7. 🥇Rating                     "+ ANSI_RESET);
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
                        clearScreen();
                        printThankYou();
                        return input;
                    case 6:
                        clearScreen();
                        return input;
                    case 7:
                        clearScreen();
                        return input;
                }
            } catch (InputMismatchException e) {
                clearScreen();
                System.out.println(RED_UNDERLINED + "\uD83D\uDED1Invalid input. Pick a valid menu option. Example: '1' for Classic Game\uD83D\uDED1" + ANSI_RESET);
                scanner.nextLine();
                return mainMenu();
            }
            clearScreen();
            System.out.println(RED_UNDERLINED + "\uD83D\uDED1Invalid input. Pick a valid menu option. Example: '1' for Classic Game\uD83D\uDED1" + ANSI_RESET);
            return 0;
        }

        @Override
        public int[] getFieldSpecs() {
            try {
                int[] specs = new int[3];
                System.out.print(BLACK_BACKGROUND + "Enter field x dimension: " + ANSI_RESET);
                specs[0] = scanner.nextInt();
                while (specs[0] < 2) {
                    System.out.print(RED_UNDERLINED + "Enter field dimension bigger than 1: " + ANSI_RESET);
                    specs[0] = scanner.nextInt();
                }

                System.out.print(BLACK_BACKGROUND + "Enter field y dimension: " + ANSI_RESET);
                specs[1] = scanner.nextInt();
                while (specs[1] < 2) {
                    System.out.print(RED_UNDERLINED + "Enter field dimension bigger than 1: " + ANSI_RESET);
                    specs[1] = scanner.nextInt();
                }

                System.out.print(BLACK_BACKGROUND + "Enter how many colors you want (maximum " + (TileInfo.values().length - 1) + "): " + ANSI_RESET);
                specs[2] = scanner.nextInt();
                while (specs[2] < 2 || specs[2] > TileInfo.values().length - 1) {
                    System.out.print(RED_UNDERLINED + "Enter more than two colors and not more than " + (TileInfo.values().length - 1) + ": " + ANSI_RESET);
                    specs[2] = scanner.nextInt();
                }

                return specs;
            } catch (InputMismatchException | NegativeArraySizeException e) {
                System.out.println(RED_UNDERLINED + "Invalid input. Please enter positive integers for dimensions and number of colors." + ANSI_RESET);
                scanner.nextLine();
                return getFieldSpecs();
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
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }

        @Override
        public String getComment() {
            clearScreen();
            System.out.println(YELLOW_BOLD_BRIGHT + "💬Add a new comment or type cancel💬" + ANSI_RESET);
            String input = scanner.nextLine();
            input = scanner.nextLine();
            if(Objects.equals(input.toLowerCase(), "cancel")) {
                System.out.println(RED_UNDERLINED + "\uD83D\uDED1Comment canceled\uD83D\uDED1" + ANSI_RESET);
                waitConsole(1000);
                return null;
            }
            System.out.println(GREEN_UNDERLINED + "💚Added a new comment💚" + ANSI_RESET);
            waitConsole(1000);
            return input;
        }

        @Override
        public int getRating() {
            try {
                String input = scanner.nextLine();

                if(Objects.equals(input.toLowerCase(), "cancel")) {
                    System.out.println(RED_UNDERLINED + "\uD83D\uDED1Rating canceled\uD83D\uDED1" + ANSI_RESET);
                    waitConsole(1000);
                    return -1;
                }

                int rating = Integer.parseInt(input);

                if(rating > 5 || rating < 0) { throw new InputMismatchException("Rating out of bounds"); }
                System.out.println(GREEN_UNDERLINED + "💚Rated the game💚" + ANSI_RESET);
                waitConsole(1000);
                return rating;
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println(YELLOW_BOLD_BRIGHT + "🥇Rate the game 1-5 or type cancel🥇" + ANSI_RESET);
                System.out.println(YELLOW_BOLD_BRIGHT + "Average rating: " + averageRating + ANSI_RESET);
                return getRating();
            }
        }

        @Override
        public void setPlayer(String player) {
            this.player = player;
        }


        private void printLogo() {
            System.out.println("▀█████████▄     ▄████████  ▄█   ▄████████    ▄█   ▄█▄    ▄████████                          \n" +
                    "  ███    ███   ███    ███ ███  ███    ███   ███ ▄███▀   ███    ███                          \n" +
                    "  ███    ███   ███    ███ ███▌ ███    █▀    ███▐██▀     ███    █▀                           \n" +
                    " ▄███▄▄▄██▀   ▄███▄▄▄▄██▀ ███▌ ███         ▄█████▀      ███                                 \n" +
                    "▀▀███▀▀▀██▄  ▀▀███▀▀▀▀▀   ███▌ ███        ▀▀█████▄    ▀███████████                          \n" +
                    "  ███    ██▄ ▀███████████ ███  ███    █▄    ███▐██▄            ███                          \n" +
                    "  ███    ███   ███    ███ ███  ███    ███   ███ ▀███▄    ▄█    ███                          \n" +
                    "▄█████████▀    ███    ███ █▀   ████████▀    ███   ▀█▀  ▄████████▀                           \n" +
                    "               ███    ███                   ▀                                               \n" +
                    "▀█████████▄     ▄████████    ▄████████    ▄████████    ▄█   ▄█▄  ▄█  ███▄▄▄▄      ▄██████▄  \n" +
                    "  ███    ███   ███    ███   ███    ███   ███    ███   ███ ▄███▀ ███  ███▀▀▀██▄   ███    ███ \n" +
                    "  ███    ███   ███    ███   ███    █▀    ███    ███   ███▐██▀   ███▌ ███   ███   ███    █▀  \n" +
                    " ▄███▄▄▄██▀   ▄███▄▄▄▄██▀  ▄███▄▄▄       ███    ███  ▄█████▀    ███▌ ███   ███  ▄███        \n" +
                    "▀▀███▀▀▀██▄  ▀▀███▀▀▀▀▀   ▀▀███▀▀▀     ▀███████████ ▀▀█████▄    ███▌ ███   ███ ▀▀███ ████▄  \n" +
                    "  ███    ██▄ ▀███████████   ███    █▄    ███    ███   ███▐██▄   ███  ███   ███   ███    ███ \n" +
                    "  ███    ███   ███    ███   ███    ███   ███    ███   ███ ▀███▄ ███  ███   ███   ███    ███ \n" +
                    "▄█████████▀    ███    ███   ██████████   ███    █▀    ███   ▀█▀ █▀    ▀█   █▀    ████████▀  \n" +
                    "               ███    ███                             ▀                                     \n");
        }

        private void printThankYou() {
            System.out.println("\n" +
                    "\n" +
                    "    ███        ▄█    █▄       ▄████████ ███▄▄▄▄      ▄█   ▄█▄      ▄██   ▄    ▄██████▄  ███    █▄                   \n" +
                    "▀█████████▄   ███    ███     ███    ███ ███▀▀▀██▄   ███ ▄███▀      ███   ██▄ ███    ███ ███    ███                  \n" +
                    "   ▀███▀▀██   ███    ███     ███    ███ ███   ███   ███▐██▀        ███▄▄▄███ ███    ███ ███    ███                  \n" +
                    "    ███   ▀  ▄███▄▄▄▄███▄▄   ███    ███ ███   ███  ▄█████▀         ▀▀▀▀▀▀███ ███    ███ ███    ███                  \n" +
                    "    ███     ▀▀███▀▀▀▀███▀  ▀███████████ ███   ███ ▀▀█████▄         ▄██   ███ ███    ███ ███    ███                  \n" +
                    "    ███       ███    ███     ███    ███ ███   ███   ███▐██▄        ███   ███ ███    ███ ███    ███                  \n" +
                    "    ███       ███    ███     ███    ███ ███   ███   ███ ▀███▄      ███   ███ ███    ███ ███    ███                  \n" +
                    "   ▄████▀     ███    █▀      ███    █▀   ▀█   █▀    ███   ▀█▀       ▀█████▀   ▀██████▀  ████████▀                   \n" +
                    "                                                    ▀                                                               \n" +
                    "   ▄████████  ▄██████▄     ▄████████         ▄███████▄  ▄█          ▄████████ ▄██   ▄    ▄█  ███▄▄▄▄      ▄██████▄  \n" +
                    "  ███    ███ ███    ███   ███    ███        ███    ███ ███         ███    ███ ███   ██▄ ███  ███▀▀▀██▄   ███    ███ \n" +
                    "  ███    █▀  ███    ███   ███    ███        ███    ███ ███         ███    ███ ███▄▄▄███ ███▌ ███   ███   ███    █▀  \n" +
                    " ▄███▄▄▄     ███    ███  ▄███▄▄▄▄██▀        ███    ███ ███         ███    ███ ▀▀▀▀▀▀███ ███▌ ███   ███  ▄███        \n" +
                    "▀▀███▀▀▀     ███    ███ ▀▀███▀▀▀▀▀        ▀█████████▀  ███       ▀███████████ ▄██   ███ ███▌ ███   ███ ▀▀███ ████▄  \n" +
                    "  ███        ███    ███ ▀███████████        ███        ███         ███    ███ ███   ███ ███  ███   ███   ███    ███ \n" +
                    "  ███        ███    ███   ███    ███        ███        ███▌    ▄   ███    ███ ███   ███ ███  ███   ███   ███    ███ \n" +
                    "  ███         ▀██████▀    ███    ███       ▄████▀      █████▄▄██   ███    █▀   ▀█████▀  █▀    ▀█   █▀    ████████▀  \n" +
                    "                          ███    ███                   ▀                                                            \n" +
                    "\n");
        }

        private void waitConsole(int millis) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        @Override
        public void passAverageRating(int rating) {
            averageRating = rating;
        }
    }
