package DND;
import DND.Enemies.Boss;
import DND.Enemies.Monster;
import DND.Enemies.Trap;
import DND.Players.*;
import java.util.HashMap;


public class CharacterTypes{
    private HashMap<Character, EnemyInitializer> enemyTypes;
    private HashMap<Integer, Player> playerTypes;

    public CharacterTypes() {
        enemyTypes = new HashMap<>();
        playerTypes = new HashMap<>();
        addCharacters();
    }

    private void addCharacters() {
        //MONSTERS
        enemyTypes.put('s', (i,j) -> new Monster('s', i, j, "Lannister Solider", 80, 80, 8, 3, 25, 3));
        enemyTypes.put('k',(i,j) -> new Monster('k',  i, j, "Lannister Knight", 200, 200, 14, 8, 50, 4));
        enemyTypes.put('q',(i,j) -> new Monster('q',  i, j, "Queen’s Guard", 400, 400, 20, 15, 100, 5));
        enemyTypes.put('z',(i,j) -> new Monster('z',  i, j, "Wright", 600, 600, 30, 15, 100, 3));
        enemyTypes.put('b', (i,j) ->new Monster('b' ,i, j, "Bear-Wright", 1000, 1000, 75, 30, 250, 4));
        enemyTypes.put('g', (i,j) -> new Monster('g',  i, j ,"Giant-Wright", 1500, 1500, 100, 40, 500, 5));
        enemyTypes.put('w', (i,j) ->new Monster('w',  i, j, "White Walker", 2000, 2000, 150, 50, 1000, 6));
        enemyTypes.put('M', (i,j) ->new Boss('M',  i, j, "The Mountain", 1000, 1000, 60, 25, 500, 6,5));
        enemyTypes.put('C',(i,j) -> new Boss('C',  i, j, "Queen Cersei", 100, 100, 10, 10, 1000, 1,4));
       enemyTypes.put('K',(i,j) -> new Boss('K',  i, j, "Night’s King", 5000, 5000, 300, 150, 5000, 8,5));
//        TRAPS
        enemyTypes.put('B',(i,j) -> new Trap('B',  i, j, "Bonus Trap", 1, 1, 1, 1, 250, 1, 5));
        enemyTypes.put('Q',(i,j) -> new Trap('Q',  i, j, "Queen’s Trap", 250, 250, 50, 10, 100, 3, 7));
        enemyTypes.put('D', (i,j) ->new Trap('D',  i, j, "Death Trap", 500, 500, 100, 20, 250, 1, 10));
        //Players
        playerTypes.put(1, new Warrior(-1, -1, "Jon Snow", 300, 300, 30, 4, 3));
        playerTypes.put(2, new Warrior(-1, -1, "The Hound", 400, 400, 20, 6, 5));
        playerTypes.put(3, new Mage(-1, -1, "Melisandre", 100, 100, 5, 1, 300, 30, 15, 5, 6));
        playerTypes.put(4, new Mage(-1, -1, "Thoros of Myr", 250, 250, 25, 4, 150, 20, 20, 3, 4));
        playerTypes.put(5, new Rogue(-1, -1, "Arya Stark", 150, 150, 40, 2, 20));
        playerTypes.put(6, new Rogue(-1, -1, "Bronn", 250, 250, 35, 3, 50));
        playerTypes.put(7, new Hunter(-1, -1, "Ygritte", 220, 220, 30, 2, 6));
    }

    public HashMap<Character, EnemyInitializer> getEnemyTypes() {
        return enemyTypes;
    }

    public Player getPlayerTile(int i) {
        return playerTypes.get(i);
    }

    public void printAllPlayers() {
        MessageHandler m = new CmdPrinter();
        for (int i = 1; i < playerTypes.size()+1; i++) {
            m.sendMessage(i + ". " + playerTypes.get(i).describe());
        }
    }

}
