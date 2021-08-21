package DND.Players;

import DND.Enemies.Enemy;
import DND.Tiles.Tile;
import DND.Tiles.Unit;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Warrior extends Player {
    private int AbilityCoolDown;
    private int RemainingCoolDown;
    private final String SPECIAL_ABILITY = "Avenger’s Shield";

    public Warrior(int x, int y, String unitName, int healthPool, int healthAmount, int attackPoints, int defencePoints, int AbilityCoolDown) {
        super(x, y, unitName, healthPool, healthAmount, attackPoints, defencePoints);
        this.AbilityCoolDown = AbilityCoolDown;
        RemainingCoolDown = 0;
    }


    @Override
    public void castAbility(Tile[][] board, List<Enemy> enemies ,Unit player) {
        if (RemainingCoolDown > 0) {
            m.sendMessage("Remaining Cooldown is still high to use " + SPECIAL_ABILITY);
        } else {
            int currentHealing = getHealthAmount();
            setRemainingCoolDown(AbilityCoolDown);
            setHealthAmount(Math.min((getHealthAmount() + (10 * getDefencePoints())), (getHealthPool())));
            m.sendMessage(getName() + " used " + SPECIAL_ABILITY + ", healing for " + (getHealthAmount() - currentHealing));
            List<Enemy> enemiesInRange = InRange(3, board, getPosition(), enemies,false);
            if (enemiesInRange.size() > 0) {
                Random i = new Random();
                Enemy enemyToAttack = enemiesInRange.get(i.nextInt(enemiesInRange.size()));
                int tenP = (int) (getHealthPool() * 0.1);
                int tryDefend = i.nextInt(enemyToAttack.getDefencePoints());
                m.sendMessage(enemyToAttack.getName() + " rolled " + tryDefend + " defense points");
                int damage = tenP - tryDefend;
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
    }

    @Override
    public void gamePlayerTick() {
        if (getRemainingCoolDown() != 0)
            setRemainingCoolDown(getRemainingCoolDown() - 1);
    }


    public void LevelUp() {
        super.LevelUp();
        setRemainingCoolDown(0);
        setHealthPool(getHealthPool() + (5 * getLevel()));
        setAttackPoints(getAttackPoints() + (2 * getLevel()));
        setDefencePoints(getDefencePoints() + (getLevel()));
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
                "CoolDown: " + getRemainingCoolDown() + "/" + getAbilityCoolDown() +
                "        " +
                "Special Ability: " + SPECIAL_ABILITY;

    }

    public int getAbilityCoolDown() {
        return AbilityCoolDown;
    }

    public int getRemainingCoolDown() {
        return RemainingCoolDown;
    }

    public void setRemainingCoolDown(int remainingCoolDown) {
        RemainingCoolDown = remainingCoolDown;
    }

}
