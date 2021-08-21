package DND.Tiles;

import DND.Enemies.Enemy;
import DND.Players.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public abstract class Unit extends Tile {
    private String name;
    private int healthPool;
    private int healthAmount;
    private int attackPoints;
    private int defencePoints;

    public Unit(char tile, int x, int y, String unitName, int healthPool, int healthAmount, int attackPoints, int defencePoints) {
        super(tile, x, y);
        name = unitName;
        this.healthPool = healthPool;
        this.healthAmount = healthAmount;
        this.attackPoints = attackPoints;
        this.defencePoints = defencePoints;
    }


        public List<Enemy> InRange(int range, Tile[][] board, Point playerPosition, List<Enemy> enemies, boolean equal) {
        List<Tile> listOFTiles = new ArrayList<>();
        List<Enemy> listOFEnemy = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Tile tmp = board[i][j];
                if (!equal) {
                    if (range(playerPosition, tmp.getPosition()) < range) {
                        listOFTiles.add(tmp);
                    }
                } else {
                    if (range(playerPosition, tmp.getPosition()) <= range) {
                        listOFTiles.add(tmp);
                    }
                }
            }
            for (Tile t : listOFTiles) {
                for (Enemy e : enemies) {
                    if (t == e)
                        listOFEnemy.add(e);
                }
            }
        }
        return listOFEnemy;
    }


    //visitor pattern
    public abstract void interact(Tile tile, Tile[][] board);

    public abstract void visit(Enemy enemy, Tile[][] board);

    public abstract void visit(Player player, Tile[][] board);


    public void visit(Wall wall, Tile[][] board) { //nothing happen
    }

    public void visit(Empty empty, Tile[][] board) { //swap positions
        swap(getPosition(), empty.getPosition(), board);
    }


    public String getName() {
        return name;
    }

    public int getAttackPoints() {
        return attackPoints;
    }

    public int getDefencePoints() {
        return defencePoints;
    }

    public int getHealthPool() {
        return healthPool;
    }

    public int getHealthAmount() {
        return healthAmount;
    }

    public void setAttackPoints(int attackPoints) {
        this.attackPoints = attackPoints;
    }

    public void setDefencePoints(int defencePoints) {
        this.defencePoints = defencePoints;
    }

    public void setHealthAmount(int healthAmount) {
        this.healthAmount = healthAmount;
    }

    public void setHealthPool(int healthPool) {
        this.healthPool = healthPool;
    }

}
