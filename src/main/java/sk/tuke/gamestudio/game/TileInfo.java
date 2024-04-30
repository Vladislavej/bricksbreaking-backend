package sk.tuke.gamestudio.game;

public enum TileInfo {
    RED(1, "R", "\033[0;101m" ,"\u001B[30m"),
    GREEN(2, "G","\033[0;102m" ,"\u001B[30m"),
    BLUE(4, "B", "\033[0;104m", "\u001B[30m"),
    YELLOW(8, "Y", "\033[0;103m", "\u001B[30m"),
    PURPLE(16, "P", "\033[0;105m", "\u001B[30m"),
    ORANGE(32, "O", "\u001B[46m", "\u001B[30m"),
    WHITE(64, "W", "\u001B[47m", "\u001B[30m"),

    NONE(0,"X", "\u001B[40m", "\u001B[37m");

    private final int score;
    private final String sname;
    private final String bcolor;
    private final String tcolor;

    TileInfo(int score, String sname, String bcolor, String tcolor) {
        this.score = score;
        this.sname = sname;
        this.bcolor = bcolor;
        this.tcolor = tcolor;
    }
    public int getScore() {
        return score;
    }

    /**
     *
     * @return One character representing short name for example RED = R etc.
     */
    public String getSname() { return sname; }

    /**
     *
     * @return String of background color code
     */
    public String getBcolor() { return bcolor; }

    /**
     * @return String of text color code
     */
    public String getTcolor() { return tcolor; }
}