package DND.Enemies;

import DND.Players.Player;
import DND.Tiles.Tile;
import DND.Tiles.Unit;

import java.awt.*;
import java.util.List;
import java.util.Random;


public abstract class Enemy extends Unit {

    private int experienceValue;

    public Enemy(char tile, int x, int y, String unitName, int healthPool, int healthAmount, int attackPoints, int defencePoints, int experienceValue) {
        super(tile, x, y, unitName, healthPool, healthAmount, attackPoints, defencePoints);
        this.experienceValue = experienceValue;
    }


    protected void moveDown(Tile[][] board) {
        Tile downTile = board[getPosition().x + 1][getPosition().y];
        interact(downTile, board);
    }

    protected void moveUp(Tile[][] board) {
        Tile upTile = board[getPosition().x - 1][getPosition().y];
        interact(upTile, board);
    }

    protected void moveRight(Tile[][] board) {
        Tile rightTile = board[getPosition().x][getPosition().y + 1];
        interact(rightTile, board);
    }

    protected void moveLeft(Tile[][] board) {
        Tile leftTile = board[getPosition().x][getPosition().y-1];
        interact(leftTile, board);
    }

    protected void randomMovement(Tile[][] board) {
        Random r = new Random();
        int i = r.nextInt(5);
        switch (i) {
            case 1:
                moveUp(board);
                break;
            case 2:
                moveDown(board);
                break;
            case 3:
                moveLeft(board);
                break;
            case 4:
                moveRight(board);
                break;
            case 5:
                break;
        }
    }
    //VISITOR PATTERN
    @Override
    public void interact(Tile tile,Tile[][]board) {
        tile.accept(this,board);
    }
    @Override
    public void visit(Player player,Tile[][]board) { // //get in combat ---> if player wins then gets experience else endgame
        m.sendMessage(getName() + " engaged in combat with " + player.getName());
        Random attackRoll = new Random();
        Random defendRoll = new Random();
        int amountAttack = attackRoll.nextInt(getAttackPoints());
        m.sendMessage(getName() + " rolled " + amountAttack + " attack points");
        int amountDefend = defendRoll.nextInt(player.getDefencePoints());
        m.sendMessage(player.getName() + " rolled " + amountDefend + " defense points");
        int damage = amountAttack - amountDefend;
        if (damage > 0) {
            player.setHealthAmount(player.getHealthAmount() - damage);
            m.sendMessage(getName() + " dealt " + damage + " damage to " + player.getName());
            if (player.getHealthAmount() <= 0){
                player.setTile('X');
                player.setHealthAmount(0);
                m.sendMessage(player.getName() + " was killed by " + getName() + "."+"\nYou lost.");
            }
        }
    }
    @Override
    public void visit(Enemy enemy,Tile[][]board) { //nothing happens
    }
    @Override
    public void accept(Unit unit,Tile[][]board) {
        unit.visit(this,board);
    }

    public int getExperienceValue() {
        return experienceValue;
    }


    public abstract void gameEnemyTick(Player player, Tile[][] board, List<Enemy> enemies);
}
