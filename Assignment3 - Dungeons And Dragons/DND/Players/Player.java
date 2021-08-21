package DND.Players;

import DND.Enemies.Enemy;
import DND.Tiles.Empty;
import DND.Tiles.HeroicUnit;
import DND.Tiles.Tile;
import DND.Tiles.Unit;

import java.awt.*;
import java.util.List;
import java.util.Random;


public abstract class Player extends Unit implements HeroicUnit {
    private int experience;
    private int level;
    private static final char PLAYER_TILE = '@';

    public Player(int x, int y, String unitName, int healthPool, int healthAmount, int attackPoints, int defencePoints) {
        super(PLAYER_TILE, x, y, unitName, healthPool, healthAmount, attackPoints, defencePoints);
        experience = 0;
        level = 1;
    }


    public void LevelUp() {
        int currentLevel = getLevel();
        setExperience(getExperience() - (50 * currentLevel));
        setLevel(currentLevel + 1);
        currentLevel = getLevel();
        setHealthPool(getHealthPool() + (10 * currentLevel));
        setHealthAmount(getHealthPool());
        setAttackPoints(getAttackPoints() + (4 * currentLevel));
        setDefencePoints(getDefencePoints() + currentLevel);

    }

    //VISITOR PATTERN
    @Override
    public void accept(Unit unit, Tile[][] board) {
        unit.visit(this, board);
    }
    @Override
    public void interact(Tile tile, Tile[][] board) {
        tile.accept(this, board);
    }
    @Override
    public void visit(Player player, Tile[][] board) {
    }
    @Override
    public void visit(Enemy enemy, Tile[][] board) { //get in combat
        m.sendMessage(getName() + " engaged in combat with " + enemy.getName());
        Random attackRoll = new Random();
        Random defendRoll = new Random();
        int amountAttack = attackRoll.nextInt(getAttackPoints());
        m.sendMessage(getName() + " rolled " + amountAttack + " attack points");
        int amountDefend = defendRoll.nextInt(enemy.getDefencePoints());
        m.sendMessage(enemy.getName() + " rolled " + amountDefend + " defense points");
        int damage = amountAttack - amountDefend;
        if (damage > 0) {
            enemy.setHealthAmount(enemy.getHealthAmount() - damage);
            m.sendMessage(getName() + " dealt " + damage + " damage to " + enemy.getName());
            if (enemy.getHealthAmount() <= 0) {
                setExperience(getExperience() + enemy.getExperienceValue());
                m.sendMessage(enemy.getName() + " died. " + getName() + " gained " + enemy.getExperienceValue() + " experience");
                tryLevelUp();
                swap(this.getPosition(), enemy.getPosition(), board);
            }
        }
    }


    public abstract String describe();
    public abstract void castAbility(Tile[][] board, List<Enemy> enemies ,Unit player);
    public abstract void gamePlayerTick();
    public abstract void tryLevelUp();


    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }




}
