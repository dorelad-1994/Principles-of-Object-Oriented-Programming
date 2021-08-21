package DND;

import DND.Enemies.Enemy;
import DND.Players.Player;
import DND.Tiles.Empty;
import DND.Tiles.Tile;
import DND.Tiles.Wall;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Level {
    private List<String> level;
    private Tile[][] tiles;
    private List<Enemy> enemies;
    private CharacterTypes types;
    private Player p;
    private Point playerStartPosition;


    public Level(List<String> l, Player player, CharacterTypes types) {
        level = l;
        this.types = types;
        tiles = new Tile[l.size()][l.get(0).length()];
        p = player;
        enemies = new ArrayList<>();
        levelParse();
    }

    private void levelParse() {
        for (int i = 0; i < level.size(); i++) {
            String row = level.get(i);
            for (int j = 0; j < row.length(); j++) {
                char ch = row.charAt(j);
                switch (ch) {
                    case '.':
                        tiles[i][j] = new Empty(i, j);
                        break;
                    case '#':
                        tiles[i][j] = new Wall(i, j);

                        break;
                    case '@':
                        tiles[i][j] = p;
                        p.getPosition().setLocation(i,j);
                        playerStartPosition = new Point(i,j);
                        break;
                    default:
                        Enemy e= types.getEnemyTypes().get(ch).initEnemy(i,j);
                        enemies.add(e);
                        tiles[i][j] = e;
                }
            }
        }
    }

    public Player getP() {
        return p;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public Point getPlayerStartPosition() {
        return playerStartPosition;
    }

    public void setP(Player p) {
        this.p = p;
    }
}


