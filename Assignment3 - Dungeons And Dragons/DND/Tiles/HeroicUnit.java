package DND.Tiles;

import DND.Enemies.Enemy;
import DND.Players.Player;

import java.util.List;

public interface HeroicUnit {
     void castAbility(Tile[][] board, List<Enemy> enemies , Unit player);
}
