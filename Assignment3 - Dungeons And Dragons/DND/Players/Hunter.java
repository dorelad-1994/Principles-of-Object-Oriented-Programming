package DND.Players;

import DND.Enemies.Enemy;
import DND.Tiles.Tile;
import DND.Tiles.Unit;

import java.util.List;
import java.util.Random;

public class Hunter extends Player {

    private int range;
    private int arrowsCount;
    private int ticksCount;
    private final String SPECIAL_ABILITY = "Shoot";

    public Hunter(int x, int y, String unitName, int healthPool, int healthAmount, int attackPoints, int defencePoints, int range) {
        super(x, y, unitName, healthPool, healthAmount, attackPoints, defencePoints);
        this.range = range;
        arrowsCount = 10;
        ticksCount = 0;
    }

    @Override
    public void castAbility(Tile[][] board, List<Enemy> enemies , Unit player) {
        List<Enemy> enemyInRange = InRange(range, board, getPosition(), enemies, true);
        if (getArrowsCount() == 0) {
            m.sendMessage("Out of arrows to use " + SPECIAL_ABILITY);
        } else if (enemyInRange.size() == 0) {
            m.sendMessage(getName() + " tried to shoot an arrow but there were no enemies in range.");
        } else {
            setArrowsCount(getArrowsCount() - 1);
            Enemy enemyToAttack = ClosestEnemy(enemyInRange);
            m.sendMessage(getName() + " fired an arrow at " + enemyToAttack.getName());
            Random i = new Random();
            int tryDefend = i.nextInt(enemyToAttack.getDefencePoints());
            m.sendMessage(enemyToAttack.getName() + " rolled " + tryDefend + " defense points");
            int damage = getAttackPoints() - tryDefend;
            if (damage > 0) {
                int healthOfEnemy = enemyToAttack.getHealthAmount();
                enemyToAttack.setHealthAmount(healthOfEnemy - damage);
                m.sendMessage(getName() + " hit " + enemyToAttack.getName() + " for " + damage + " ability damage");
                if (enemyToAttack.getHealthAmount() <= 0) {
                    setExperience(getExperience() + enemyToAttack.getExperienceValue());
                    m.sendMessage(enemyToAttack.getName() + " died. " + getName() + " gained " + enemyToAttack.getExperienceValue() + " experience");
                    tryLevelUp();
                }
            }
        }

    }

    @Override
    public void gamePlayerTick() {
        if (getTicksCount() == 10) {
            setArrowsCount(getArrowsCount() + getLevel());
            setTicksCount(0);
        } else {
            setTicksCount(getTicksCount() + 1);
        }
    }

    @Override
    public void tryLevelUp() {
        while (getExperience() >= (50 * getLevel())) {
            int amoutHealth = getHealthAmount();
            int amoutAttack = getAttackPoints();
            int amoutDefense = getDefencePoints();
            LevelUp();
            m.sendMessage(getName() + " reached level " + getLevel() + ": +" + (getHealthAmount() - amoutHealth) + " Health, +" + (getAttackPoints() - amoutAttack) + " Attack, +" + (getDefencePoints() - amoutDefense) + " Defense");
        }
    }

    public void LevelUp() {
        super.LevelUp();
        setArrowsCount(getArrowsCount() + (10 * getLevel()));
        setAttackPoints(getAttackPoints() + (2 * getLevel()));
        setDefencePoints((getDefencePoints() + (getLevel())));
    }
    @Override
    public String describe() {
        return getName() + "        " +
                "Health: " + getHealthAmount() + "/" + getHealthPool() +
                "        " +
                "Attack: " + getAttackPoints() +
                "        " +
                "Defense: " + getDefencePoints() +
                "        " +
                "Level: " + getLevel() +
                "        " +
                "Experience: " + getExperience() +
                "        " +
                "Arrows: " + getArrowsCount() +
                "        " +
                "Range: " + getRange() +
                "        " +
                "Special Ability: " + SPECIAL_ABILITY;
    }
    private Enemy ClosestEnemy(List<Enemy> enemies) {
        Enemy closest = enemies.get(0);
        for (Enemy e : enemies) {
            if (range(this.getPosition(), e.getPosition()) < range(this.getPosition(), closest.getPosition()))
                closest = e;
        }
        return closest;
    }

    public int getTicksCount() {
        return ticksCount;
    }

    public int getArrowsCount() {
        return arrowsCount;
    }

    public int getRange() {
        return range;
    }

    public void setTicksCount(int ticksCount) {
        this.ticksCount = ticksCount;
    }

    public void setArrowsCount(int arrowsCount) {
        this.arrowsCount = arrowsCount;
    }

}