package bricksbreaker.core;

//sname = Short name
//bcolor = Background color
//tcolor = Text color

public enum TileInfo {
    RED(5, 'R', "\u001B[41m" ,"\u001B[30m"),
    GREEN(10, 'G',"\u001B[42m" ,"\u001B[30m"),
    BLUE(15, 'B', "\u001B[44m", "\u001B[30m"),
    YELLOW(20, 'Y', "\u001B[43m", "\u001B[30m"),
    PURPLE(25, 'P', "\u001B[45m", "\u001B[30m"),
    CYAN(30, 'C', "\u001B[46m", "\u001B[30m"),
    WHITE(35, 'W', "\u001B[47m", "\u001B[30m"),

    NONE(0,'X', "\u001B[40m", "\u001B[37m");

    private final int score;
    private final char sname;
    private final String bcolor;
    private final String tcolor;

    TileInfo(int score, char sname, String bcolor, String tcolor) {
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
    public char getSname() { return sname; }

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