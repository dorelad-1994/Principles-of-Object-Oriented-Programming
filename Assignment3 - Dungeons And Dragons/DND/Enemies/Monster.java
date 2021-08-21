package DND.Enemies;

import DND.Players.Player;
import DND.Tiles.Tile;

import java.util.List;
import java.util.Random;

public class Monster extends Enemy {
    private int visionRange;

    public Monster(char tile, int x, int y, String unitName, int healthPool, int healthAmount, int attackPoints, int defencePoints, int experienceValue, int visionRange) {
        super(tile, x, y, unitName, healthPool, healthAmount, attackPoints, defencePoints, experienceValue);
        this.visionRange = visionRange;
    }

    @Override
    public void gameEnemyTick(Player player, Tile[][] board, List<Enemy> enemies) {
        if (range(getPosition(), player.getPosition()) < visionRange) {
            int dy = getPosition().x - player.getPosition().x;
            int dx = getPosition().y - player.getPosition().y;
            if (Math.abs(dx) > Math.abs(dy)) {
                if (dx > 0)
                    moveLeft(board);
                else moveRight(board);
            } else {
                if (dy > 0)
                    moveUp(board);
                else moveDown(board);
            }
        } else randomMovement(board);
    }

}
