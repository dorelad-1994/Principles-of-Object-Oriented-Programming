package DND;

import DND.Enemies.Enemy;
import DND.Players.Player;
import DND.Tiles.Empty;
import DND.Tiles.Tile;

import java.awt.*;
import java.util.List;


public class GameManager {

    private Tile[][] board;
    private MessageHandler m;
    private List<Enemy> enemies;
    private Player player;
    private boolean deadPlayer;
    private boolean allEnemiesDead;


    public GameManager(Tile[][] board, List<Enemy> enemies, Player player, MessageHandler m) {
        this.board = board;
        this.m = m;
        this.enemies = enemies;
        this.player = player;
        deadPlayer = false;
        allEnemiesDead = false;

    }

    private String builtBoard() {

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                output.append(board[i][j]);
            }
            output.append("\n");
        }
        return output.toString();
    }

    public void printBoard() {
        m.sendMessage(builtBoard());
        m.sendMessage(player.describe());
    }

    public void tick(char approach) {
        Point positionPlayer = player.getPosition();
        switch (approach) {
            case 'w':
                Tile upTile = board[positionPlayer.x - 1][positionPlayer.y];
                player.interact(upTile, board);
                break;
            case 's':
                Tile downTile = board[positionPlayer.x + 1][positionPlayer.y];
                player.interact(downTile, board);
                break;
            case 'a':
                Tile leftTile = board[positionPlayer.x][positionPlayer.y - 1];
                player.interact(leftTile, board);
                break;
            case 'd':
                Tile rightTile = board[positionPlayer.x][positionPlayer.y + 1];
                player.interact(rightTile, board);
                break;
            case 'e':
                player.castAbility(board, enemies, player);
                break;
            case 'q':
                break;
        }
        m.sendMessage(player.describe());
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).getHealthAmount() <= 0) {
                Enemy enemy = enemies.remove(i);
                board[enemy.getPosition().x][enemy.getPosition().y] = new Empty(enemy.getPosition().x, enemy.getPosition().y);
            }
        }
        Tick(); //first player and then a enemy makes a random movement tick or chase a player
        if (enemies.size() == 0) {
            allEnemiesDead = true;
        }
        if (player.getTile() == 'X')
            deadPlayer = true;
    }

    private void Tick() {
        player.gamePlayerTick();
        for (Enemy e : enemies) {
            e.gameEnemyTick(player, board, enemies);

        }
    }

    public boolean isDeadPlayer() {
        return deadPlayer;
    }

    public boolean isAllEnemiesDead() {
        return allEnemiesDead;
    }

}

