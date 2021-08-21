package Tests;

import DND.Players.Player;
import DND.Players.Warrior;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class WarriorTest {
    Player p;
    @Before
    public void setUp() throws Exception {
        p = new Warrior(5, 4, "Jon Snow", 300, 300, 30, 4, 3);
    }

    @After
    public void tearDown() throws Exception {
        p = null;
    }

    @Test
    public void tryLevelUp1() {
        p.setExperience(51);
        int expected = 2;
        p.tryLevelUp();
        Assert.assertEquals(expected,p.getLevel());
    }
    @Test
    public void tryLevelUp2() {
        p.setExperience(151);
        int expected = 3;
        p.tryLevelUp();
        Assert.assertEquals(expected,p.getLevel());
    }
}