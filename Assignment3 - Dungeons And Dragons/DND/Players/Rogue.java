package DND.Players;

import DND.Enemies.Enemy;
import DND.Tiles.Tile;
import DND.Tiles.Unit;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class Rogue extends Player {
    private int cost;
    private int currentEnergy;
    private final String SPECIAL_ABILITY = "Fan of Knives";
    private final int MAX_ENERGY = 100;

    public Rogue(int x, int y, String unitName, int healthPool, int healthAmount, int attackPoints, int defencePoints, int cost) {
        super(x, y, unitName, healthPool, healthAmount, attackPoints, defencePoints);
        this.cost = cost;
        currentEnergy = MAX_ENERGY;
    }

    public void LevelUp() {
        super.LevelUp();
        setCurrentEnergy(100);
        setAttackPoints(getAttackPoints() + (3 * getLevel()));
    }


    @Override
    public void castAbility(Tile[][] board, List<Enemy> enemies ,Unit player) {
        if (getCurrentEnergy() < getCost())
            m.sendMessage("Not enough energy for using " + SPECIAL_ABILITY);
        else {
            m.sendMessage(getName() + " cast " + SPECIAL_ABILITY);
            setCurrentEnergy(getCurrentEnergy() - getCost());

            List<Enemy> enemiesInRange = InRange(2, board, getPosition(), enemies,false);
            for (Enemy e : enemiesInRange) {
                Random i = new Random();
                int tryDefend = i.nextInt(e.getDefencePoints());
                m.sendMessage(e.getName() + " rolled " + tryDefend + " defense points");
                int damage = getAttackPoints() - tryDefend;
                if (damage > 0) {
                    int healthOfEnemy = e.getHealthAmount();
                    e.setHealthAmount(healthOfEnemy - damage);
                    m.sendMessage(getName() + " hit " + e.getName() + " for " + damage + " ability damage");
                    if (e.getHealthAmount() <= 0) {
                        setExperience(getExperience() + e.getExperienceValue());
                        m.sendMessage(e.getName() + " died. " + getName() + " gained " + e.getExperienceValue() + " experience");
                        tryLevelUp();
                    }
                }
            }
        }
    }


    public int getCost() {
        return cost;
    }

    public int getCurrentEnergy() {
        return currentEnergy;
    }

    public void setCurrentEnergy(int currentEnergy) {
        this.currentEnergy = currentEnergy;
    }

    @Override
    public void gamePlayerTick() {
        setCurrentEnergy(Math.min(getCurrentEnergy() + 10, 100));
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
                "Energy: " + getCurrentEnergy() + "/" + MAX_ENERGY +
                "        " +
                "Special Ability: " + SPECIAL_ABILITY;
    }
}
