package DND.Enemies;

import DND.Players.Player;
import DND.Tiles.HeroicUnit;
import DND.Tiles.Tile;
import DND.Tiles.Unit;

import java.util.List;
import java.util.Random;

public class Boss extends Enemy implements HeroicUnit {

    private int visionRange;
    private int abilityFrequency;
    private int combatTicks;

    public Boss(char tile, int x, int y, String unitName, int healthPool, int healthAmount, int attackPoints, int defencePoints, int experienceValue, int visionRange, int abilityFrequency) {
        super(tile, x, y, unitName, healthPool, healthAmount, attackPoints, defencePoints, experienceValue);
        this.visionRange = visionRange;
        this.abilityFrequency = abilityFrequency;
        combatTicks = 0;
    }


    @Override
    public void castAbility(Tile[][] board, List<Enemy> enemies, Unit player) {
        m.sendMessage(getName() + " shot at " + player.getName());
        Random i = new Random();
        int tryDefend = i.nextInt(player.getDefencePoints());
        m.sendMessage(player.getName() + " rolled " + tryDefend + " defense points");
        int damage = getAttackPoints() - tryDefend;
        if (damage > 0) {
            int healthOfPlayer = player.getHealthAmount();
            player.setHealthAmount(healthOfPlayer - damage);
            m.sendMessage(getName() + " hit " + player.getName() + " for " + damage + " ability damage");
            if (player.getHealthAmount() <= 0) {
                player.setTile('X');
                player.setHealthAmount(0);
                m.sendMessage(player.getName() + " was killed by " + getName() + "." + "\nYou lost.");
            }
        }
    }

    @Override
    public void gameEnemyTick(Player player, Tile[][] board, List<Enemy> enemies) {
        if (range(getPosition(), player.getPosition()) < getVisionRange()) {
            if (getCombatTicks() == getAbilityFrequency()) {
                setCombatTicks(0);
                castAbility(board, enemies, player);
            } else {
                setCombatTicks(getCombatTicks() + 1);
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
            }
        } else {
            setCombatTicks(0);
            randomMovement(board);
        }
    }

    public int getVisionRange() {
        return visionRange;
    }

    public int getAbilityFrequency() {
        return abilityFrequency;
    }

    public int getCombatTicks() {
        return combatTicks;
    }

    public void setCombatTicks(int combatTicks) {
        this.combatTicks = combatTicks;
    }
}
