package DND.Tiles;
import DND.CmdPrinter;
import DND.MessageHandler;


import java.awt.*;


public abstract class Tile {
    protected MessageHandler m;
    private char tile;
    private Point position;

    public Tile(char tile, int x, int y){
        this.tile = tile;
        this.position = new Point(x, y);
        m = new CmdPrinter();
    }

    public double range(Point a, Point b) {
        return a.distance(b);
    }

    @Override
    public String toString() {
        return String.valueOf(tile);
    }

    public char getTile() {
        return tile;
    }

    public Point getPosition() {
        return position;
    }

    public void setTile(char tile) {
        this.tile = tile;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public abstract void accept(Unit unit,Tile[][]board);


    public void swap(Point tile1, Point tile2, Tile[][] board) {
        int tile1X = tile1.x;
        int tile1Y = tile1.y;
        int tile2X = tile2.x;
        int tile2Y = tile2.y;
        Tile tmp =  board[tile1X][tile1Y];
        board[tile1X][tile1Y] = board[tile2X][tile2Y];
        board[tile2X][tile2Y] = tmp;
        tile1.setLocation(tile2X,tile2Y);
        tile2.setLocation(tile1X,tile1Y);
    }




}
