package bricksbreaker.core;

//sname = Short name
//bcolor = Background color
//tcolor = Text color

public enum TileInfo {
    RED(255,0,0, 20, 'R', "\u001B[41m" ,"\u001B[30m"),
    GREEN(0,255,0 ,10, 'G',"\u001B[42m" ,"\u001B[30m"),
    BLUE(0,0,255 ,5, 'B', "\u001B[44m", "\u001B[30m"),
    NONE(0,0,0,0,' ', "\u001B[40m", "\u001B[37m");

    private final int r;
    private final int g;
    private final int b;
    private final int score;
    private final char sname;
    private final String bcolor;
    private final String tcolor;

    TileInfo(int r, int g, int b, int score, char sname, String bcolor, String tcolor) {
        this.r = r;
        this.g = g;
        this.b = b;
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