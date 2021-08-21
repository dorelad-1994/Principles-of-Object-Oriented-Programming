package DND.Players;

import DND.Enemies.Enemy;
import DND.Tiles.Tile;
import DND.Tiles.Unit;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class Mage extends Player {
    private int manaPool;
    private int currentMana;
    private int manaCost;
    private int spellPower;
    private int hitCounts;
    private int abilityRange;
    private final String SPECIAL_ABILITY = "Blizzard";


    public Mage(int x, int y, String unitName, int healthPool, int healthAmount, int attackPoints, int defencePoints, int manaPool, int manaCost, int spellPower, int hitCounts, int abilityRange) {
        super(x, y, unitName, healthPool, healthAmount, attackPoints, defencePoints);
        this.manaPool = manaPool;
        currentMana = manaPool / 4;
        this.spellPower = spellPower;
        this.hitCounts = hitCounts;
        this.abilityRange = abilityRange;
        this.manaCost = manaCost;
    }

    @Override
    public void castAbility(Tile[][] board, List<Enemy> enemies, Unit player) {
        if (currentMana < manaCost)
            m.sendMessage("Not enough mana for using " + SPECIAL_ABILITY);
        else {
            m.sendMessage(getName() + "  cast " + SPECIAL_ABILITY);
            setCurrentMana(currentMana - manaCost);
            int hits = 0;
            List<Enemy> enemiesInRange = InRange(getAbilityRange(), board, getPosition(), enemies, false);
            while ((hits < getHitCounts()) & (enemiesInRange.size() > 0)) {
                Random i = new Random();
                Enemy enemyToAttack = enemiesInRange.get(i.nextInt(enemiesInRange.size()));
                int tryDefend = i.nextInt(enemyToAttack.getDefencePoints());
                m.sendMessage(enemyToAttack.getName() + " rolled " + tryDefend + " defense points");
                int damage = getSpellPower() - tryDefend;
                if (damage > 0) {
                    enemyToAttack.setHealthAmount(enemyToAttack.getHealthAmount() - damage); //enemy will reduce its health amount after trying to defend by within spell power
                    m.sendMessage(getName() + " hit " + enemyToAttack.getName() + " for " + damage + " ability damage");
                    if (enemyToAttack.getHealthAmount() <= 0) {
                        setExperience(getExperience() + enemyToAttack.getExperienceValue());
                        m.sendMessage(enemyToAttack.getName() + " died. " + getName() + " gained " + enemyToAttack.getExperienceValue() + " experience");
                        tryLevelUp();
                        enemiesInRange.remove(enemyToAttack);
                    }
                }
                hits++;
            }
        }
    }

    public void LevelUp() {
        super.LevelUp();
        setManaPool(getManaPool() + (25 * getLevel()));
        setCurrentMana(Math.min(getCurrentMana() + (getManaPool() / 4), getManaPool()));
        setSpellPower(getSpellPower() + (10 * getLevel()));
    }

    @Override
    public void tryLevelUp() {
        while (getExperience() >= (50 * getLevel())) {
            int amoutHealth = getHealthAmount();
            int amoutAttack = getAttackPoints();
            int amoutDefense = getDefencePoints();
            int amoutMaxMana = getManaPool();
            int amoutSpeelPower = getSpellPower();
            LevelUp();
            m.sendMessage(getName() + " reached level " + getLevel() + ": +" + (getHealthAmount() - amoutHealth) + " Health, +" + (getAttackPoints() - amoutAttack) + " Attack,\n +" + (getDefencePoints() - amoutDefense) + " Defense, +" + (getManaPool() - amoutMaxMana) + " Maximum mana, +" + (getSpellPower() - amoutSpeelPower) + " spell power");
        }
    }

    @Override
    public void gamePlayerTick() {
        setCurrentMana(Math.min(getManaPool(), (getCurrentMana() + 1) * getLevel()));
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
                "Mana: " + getCurrentMana() + "/" + getManaPool() +
                "        " +
                "Mana Cost: " + getManaCost() +
                "        " +
                "Spell Power: " + getSpellPower() +
                "        " +
                "Special Ability: " + SPECIAL_ABILITY;
    }

    public int getManaPool() {
        return manaPool;
    }

    public void setManaPool(int manaPool) {
        this.manaPool = manaPool;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public void setCurrentMana(int currentMana) {
        this.currentMana = currentMana;
    }

    public int getManaCost() {
        return manaCost;
    }

    public int getSpellPower() {
        return spellPower;
    }

    public void setSpellPower(int spellPower) {
        this.spellPower = spellPower;
    }

    public int getHitCounts() {
        return hitCounts;
    }

    public int getAbilityRange() {
        return abilityRange;
    }


}
