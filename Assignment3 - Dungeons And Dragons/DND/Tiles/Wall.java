package DND.Tiles;

public class Wall extends Tile{
    private static final char WALL_TILE ='#';
    public Wall(int x, int y) {
        super(WALL_TILE, x, y);
    }

    @Override
    public void accept(Unit unit,Tile[][]board) {
            unit.visit(this,board);
    }

    @Override
    public String toString() {
        return String.valueOf(WALL_TILE);
    }
}
