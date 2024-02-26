package bricksbreaker.core;

public enum TileInfo {
    RED(255,0,0, 20, "R", "\u001B[41m"),
    GREEN(0,255,0 ,10, "G","\u001B[42m"),
    BLUE(0,0,255 ,5, "B", "\u001B[44m"),
    NONE(0,0,0,0," ", "\u001B[40m");

    private final int r;
    private final int g;
    private final int b;
    private final int score;
    private final String sname;
    private final String bcolor;
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    TileInfo(int r, int g, int b, int score, String sname, String bcolor) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.score = score;
        this.sname = sname;
        this.bcolor = bcolor;
    }

    public int getScore() {
        return score;
    }

    public String getSname() { return sname; }

    public String getBcolor() { return bcolor; }
}