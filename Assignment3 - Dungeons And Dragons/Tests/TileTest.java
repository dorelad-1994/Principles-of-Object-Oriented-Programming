package Tests;

import DND.Players.Warrior;
import DND.Tiles.Tile;
import DND.Tiles.Wall;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

public class TileTest {
    Tile wall;
    Tile player;
    Tile[][] board;
    @Before
    public void setUp() {
        board = new Tile[10][10]; //creating a demo board of walls only and @ player to check swap
        for (int i = 0; i <board.length ; i++) {
            for (int j = 0; j <board[i].length ; j++) {
                board[i][j] = new Wall(i,j);
            }
        }
        board[5][4] = new Warrior(5, 4, "Jon Snow", 300, 300, 30, 4, 3);
        player = board[5][4];
        wall = board[5][5];
;    }

    @After
    public void tearDown() throws Exception {
        board = null;
    }

    @Test
    public void swap1() { //check swap between 2 tiles - this test tests the player position
        Point a = player.getPosition();
        Point b = wall.getPosition();
        player.swap(a,b,board);
        char ActualPlayer = board[5][5].getTile();
        Assert.assertEquals('@',ActualPlayer);
    }
    @Test
    public void swap2() {//this test tests the wall position
        Point a = player.getPosition();
        Point b = wall.getPosition();
        player.swap(a,b,board);
        char ActualWallC = board[5][4].getTile();
        Assert.assertEquals('#',ActualWallC);
    }
}