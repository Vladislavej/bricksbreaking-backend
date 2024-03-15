    package bricksbreaker.ui.console;

    import bricksbreaker.core.Field;
    import bricksbreaker.core.Tile;
    import bricksbreaker.core.TileInfo;
    import bricksbreaker.ui.GameUI;
    import sk.tuke.gamestudio.entity.Comment;
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
        public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN
        public static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
        public static final String BLACK_BRIGHT = "\033[0;90m";  // BLACK
        public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE
        public static final String BROWN = "\033[0;33m"; // Brown (approximation)
        public static final String	YELLOW = "\u001B[33m";
        public static final String RED_BRIGHT = "\033[0;91m";    // RED
        public static final String GREEN_BRIGHT = "\033[0;92m";  // GREEN
        private Field field;
        private String player;
        private int averageRating;
        private int lastRating;

        public ConsoleUI(Field field) {
            scanner = new Scanner(System.in);
            this.field = field;
            this.player = "Player";
        }
        @Override
        public void showField() {
            int rows = field.getRows();
            int cols = field.getCols();

            System.out.print(WHITE_BOLD_BRIGHT + "      " + ANSI_RESET);
            for (int j = 0; j < cols; j++) {
                System.out.printf(WHITE_BOLD_BRIGHT + "%2d " + ANSI_RESET, j);
            }
            System.out.println();

            System.out.print(WHITE_BOLD_BRIGHT + "      " + ANSI_RESET);
            for (int j = 0; j < cols; j++) {
                System.out.print(WHITE_BOLD_BRIGHT + " ─ " + ANSI_RESET);
            }
            System.out.println();

            for (int i = 0; i < rows; i++) {
                System.out.printf(WHITE_BOLD_BRIGHT + "%3d | " + ANSI_RESET, i);
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
                System.out.print(WHITE_BOLD_BRIGHT + " Enter x and y coordinates of your move: " + ANSI_RESET + " ");
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
            System.out.println(YELLOW_BOLD_BRIGHT + " 🌟 " + score + ANSI_RESET + " 💖 " + RED_BRIGHT + lives + " " + ANSI_RESET);
            System.out.println();
        }

        @Override
        public void setField(Field field) {
            this.field = field;
        }

        @Override
        public void showHighScores(List<Score> topScores) {
            System.out.println();
            System.out.println("🔥High scores🔥" + ANSI_RESET);
            System.out.println();
            for (int i = 0; i < 10; i++) {
                try {
                    Score topScore = topScores.get(i);
                    String rank = String.format("%-2d", i + 1);
                    String player = String.format("%-25s", topScore.getPlayer());
                    String points = String.format("%-10s", topScore.getPoints());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String playedOn = String.format("%-20s", dateFormat.format(topScore.getPlayedOn()));
                    if(i == 0) {
                        System.out.println(YELLOW_BOLD_BRIGHT + " 🥇  " + player + points + playedOn + ANSI_RESET);
                    } else if (i == 1) {
                        System.out.println(WHITE_BOLD_BRIGHT +  " 🥈  " + player + points + playedOn + ANSI_RESET);
                    } else if (i == 2) {
                        System.out.println(BROWN + " 🥉  " + player + points + playedOn + ANSI_RESET);
                    } else {
                        System.out.println(" " + rank + ". " + player + points + playedOn);
                    }
                } catch (IndexOutOfBoundsException e) { break; }
            }
            System.out.println("\n" + GREEN_UNDERLINED + "Enter to continue" + ANSI_RESET);
            scanner.nextLine();
            scanner.nextLine();
        }

        @Override
        public boolean playAgain() {
            System.out.println();
            System.out.println(WHITE_BOLD_BRIGHT + "Type 'yes' to play again or press enter to continue" + ANSI_RESET);
            while(true) {
                String input = scanner.nextLine();
                if (input.toLowerCase().equals("yes")) {
                    clearScreen();
                    return true;
                } else {
                    clearScreen();
                    return false;
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

            System.out.println("6. 💌Comments                   "+ ANSI_RESET);
            System.out.println("7. 🥇Ratings                    "+ ANSI_RESET);
            System.out.println();
            System.out.println(WHITE_BOLD_BRIGHT + "8. ❔Help                       "+ ANSI_RESET);
            System.out.println();
            try {
                int input = -1;
                input = scanner.nextInt();
                switch (input) {
                    case 1:
                        return input;
                    case 2:
                        clearScreen();
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
                    case 8:
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
                System.out.print(WHITE_BOLD_BRIGHT + "Enter field x dimension: " + ANSI_RESET);
                specs[0] = scanner.nextInt();
                while (specs[0] < 2) {
                    System.out.print(RED_UNDERLINED + "Enter field dimension bigger than 1: " + ANSI_RESET);
                    specs[0] = scanner.nextInt();
                }

                System.out.print(WHITE_BOLD_BRIGHT + "Enter field y dimension: " + ANSI_RESET);
                specs[1] = scanner.nextInt();
                while (specs[1] < 2) {
                    System.out.print(RED_UNDERLINED + "Enter field dimension bigger than 1: " + ANSI_RESET);
                    specs[1] = scanner.nextInt();
                }

                System.out.print(WHITE_BOLD_BRIGHT + "Enter how many colors you want (maximum " + (TileInfo.values().length - 1) + "): " + ANSI_RESET);
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
        public String changePlayerName() {
            clearScreen();
            System.out.println(YELLOW_BOLD_BRIGHT + "😎Enter new name😜" + ANSI_RESET);
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
        public String addComment(List<Comment> commentList) {
            clearScreen();
            System.out.println(YELLOW_BOLD_BRIGHT + "Most recent comments:" + ANSI_RESET);
            for (int i = 0; i < 10; i++) {
                try {
                    Comment comment = commentList.get(i);
                    String rank = String.format("%-2d", i + 1);
                    String player = String.format("%-20s", comment.getPlayer());
                    String text = String.format(comment.getComment());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String playedOn = String.format("%-20s", dateFormat.format(comment.getCommentedOn()));
                    System.out.println(player + playedOn + WHITE_BOLD_BRIGHT + text + ANSI_RESET);

                } catch (IndexOutOfBoundsException e) { break; }
            }
            System.out.println(YELLOW_BOLD_BRIGHT + "💬Add a new comment or press enter💬" + ANSI_RESET);
            String input = scanner.nextLine();
            input = scanner.nextLine();
            if(Objects.equals(input.toLowerCase(), "")) {
                System.out.println(RED_UNDERLINED + "\uD83D\uDED1Comment canceled\uD83D\uDED1" + ANSI_RESET);
                waitConsole(1000);
                return null;
            }
            System.out.println(GREEN_UNDERLINED + "💚Added a new comment💚" + ANSI_RESET);
            waitConsole(1000);
            return input;
        }

        @Override
        public int addRating() {
            System.out.println(YELLOW_BOLD_BRIGHT + "🥇Rate the game 1-5 or press enter🥇" + ANSI_RESET);
            if(lastRating != -1) {
                System.out.print(WHITE_BOLD_BRIGHT + "My last rating: " + ANSI_RESET);
                for (int i = 0; i < lastRating; i++) {
                    System.out.print("⭐");
                }
            }
            System.out.println();
            System.out.print(WHITE_BOLD_BRIGHT + "Average rating: " + ANSI_RESET);
            for (int i = 0; i < averageRating; i++) {
                System.out.print("⭐");
            }
            System.out.println();

            try {
                String input = scanner.nextLine();
                input = scanner.nextLine();

                if(Objects.equals(input.toLowerCase(), "")) {
                    return -1;
                }

                int rating = Integer.parseInt(input);

                if(rating > 5 || rating < 0) { throw new InputMismatchException("Rating out of bounds"); }
                System.out.println(GREEN_UNDERLINED + "💚Rated the game💚" + ANSI_RESET);
                waitConsole(1000);
                return rating;
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println(RED_UNDERLINED + "\uD83D\uDED1Rating out of bounds\uD83D\uDED1" + ANSI_RESET);
                waitConsole(1000);
                clearScreen();
                return addRating();
            }
        }

        @Override
        public void setPlayer(String player) {
            this.player = player;
        }


        private void printLogo() {
            System.out.println(WHITE_BOLD_BRIGHT +"▀█████████▄     ▄████████  ▄█   ▄████████    ▄█   ▄█▄    ▄████████                          \n" +
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
                    "               ███    ███                             ▀                                     \n" + ANSI_RESET);
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

        @Override
        public void passLastRating(int rating) { lastRating = rating; }

        @Override
        public void showWin() {
            System.out.println(GREEN_BRIGHT + "▄██   ▄    ▄██████▄  ███    █▄       ▄█     █▄   ▄██████▄  ███▄▄▄▄   \n" +
                    "███   ██▄ ███    ███ ███    ███     ███     ███ ███    ███ ███▀▀▀██▄ \n" +
                    "███▄▄▄███ ███    ███ ███    ███     ███     ███ ███    ███ ███   ███ \n" +
                    "▀▀▀▀▀▀███ ███    ███ ███    ███     ███     ███ ███    ███ ███   ███ \n" +
                    "▄██   ███ ███    ███ ███    ███     ███     ███ ███    ███ ███   ███ \n" +
                    "███   ███ ███    ███ ███    ███     ███     ███ ███    ███ ███   ███ \n" +
                    "███   ███ ███    ███ ███    ███     ███ ▄█▄ ███ ███    ███ ███   ███ \n" +
                    " ▀█████▀   ▀██████▀  ████████▀       ▀███▀███▀   ▀██████▀   ▀█   █▀  \n" +
                    "                                                                     " + ANSI_RESET);
        }

        @Override
        public void showFail() {
            System.out.println(RED_BRIGHT+ "▄██   ▄    ▄██████▄  ███    █▄       ▄█        ▄██████▄     ▄████████     ███     \n" +
                    "███   ██▄ ███    ███ ███    ███     ███       ███    ███   ███    ███ ▀█████████▄ \n" +
                    "███▄▄▄███ ███    ███ ███    ███     ███       ███    ███   ███    █▀     ▀███▀▀██ \n" +
                    "▀▀▀▀▀▀███ ███    ███ ███    ███     ███       ███    ███   ███            ███   ▀ \n" +
                    "▄██   ███ ███    ███ ███    ███     ███       ███    ███ ▀███████████     ███     \n" +
                    "███   ███ ███    ███ ███    ███     ███       ███    ███          ███     ███     \n" +
                    "███   ███ ███    ███ ███    ███     ███▌    ▄ ███    ███    ▄█    ███     ███     \n" +
                    " ▀█████▀   ▀██████▀  ████████▀      █████▄▄██  ▀██████▀   ▄████████▀     ▄████▀   \n" +
                    "                                    ▀                                             \n" +
                    "\n"+ ANSI_RESET);
        }

        @Override
        public void showHelp() {
            clearScreen();
            System.out.println(WHITE_BOLD_BRIGHT + "Destroy all the bricks by clicking them in groups of the same color. \nThe more you get at once, the higher the score.\n" +
                     RED_UNDERLINED + "If you try to remove a single brick you will lose a life!" + ANSI_RESET);
            System.out.println(WHITE_BOLD_BRIGHT + "🟥 - 1   point" + ANSI_RESET);
            System.out.println(WHITE_BOLD_BRIGHT + "🟩 - 8   points" + ANSI_RESET);
            System.out.println(WHITE_BOLD_BRIGHT + "🟦 - 32  points" + ANSI_RESET);
            System.out.println(WHITE_BOLD_BRIGHT + "🟨 - 128 points" + ANSI_RESET);
            System.out.println("\n" + GREEN_UNDERLINED + "Enter to continue" + ANSI_RESET);
            scanner.nextLine();
            scanner.nextLine();
        }
    }
